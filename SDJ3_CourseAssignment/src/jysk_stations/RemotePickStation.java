package jysk_stations;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.Item;
import jysk_shared.Order;
import jysk_shared.OrderBox;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemotePickStation extends UnicastRemoteObject implements PickStation {

	private Queue<Order> orders; 
	private Hashtable<Integer, Pallet> pallets; 
	private Crane crane; 
	private String id; 

	public RemotePickStation(String id) throws RemoteException {
		super();
		orders = new LinkedList<Order>();
		pallets = new Hashtable<Integer, Pallet>(); 
		this.id = id;
		doRegister(); 
		Thread t = new Thread(new OrderHandler(this, true));
		t.start();
	}
	@Override
	public void setUpConnection(String connectTo) {
		Crane c = null;
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(this.id, this);
			c = (Crane) registry.lookup(connectTo);
			c.registerPickStation(this);
		} catch (RemoteException e1) {
			System.err.println("Crane did not register properly to the Conveyer belt." +"\n"
					+ "This is likely due to connection issues.");
			return;
		} catch (NotBoundException e2) {
			System.err.println("Crane did not register properly to the Conveyer belt."+"\n"
					+ "This is due to trying to look up a register that has no associated binding.");
			return;
		}
		crane = c; 
	}
	
	private void doRegister() {
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(this.id, this);
			Conveyer conv = (Conveyer) registry.lookup("Conveyer");
			conv.registerPickupStation(this);
		} catch (RemoteException e1) {
			System.err.println("Pick station did not register properly to the Conveyer belt." +"\n"
					+ "This is likely due to connection issues.");
			return;
		} catch (NotBoundException e2) {
			System.err.println("Pick station did not register properly to the Conveyer belt."+"\n"
					+ "This is due to trying to look up a register that has no associated binding.");
			return;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized boolean receiveOrder(Order order) throws RemoteException {
		notifyAll(); 
		return orders.add(order); 
	}

	@Override
	public Order sendAway() throws RemoteException {
		return null;

	}

	private void sendPalletRequest(ArrayList<String> types, String myId, int orderID) {
		for (int i = 0; i < types.size(); i++) {
			try {
				if(crane == null) {
					System.out.println("You done fucked up");
				}
				System.out.println("id is: "+myId +" orderid " + orderID + " type are " + types.get(i));
				boolean success = crane.retrievePallet(myId, orderID, types.get(i));
				System.out.println("Type: "+types.get(i) + " was retrieved: "+success);			
			} catch (RemoteException e) {
				e.printStackTrace();
			} 
		}
	}

	private synchronized OrderBox packOrder(Order o) 
	{
		while(pallets.get(o.getID()) == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Hashtable<String, Item> itemsRequested = o.getRequestedItems();
		Set<String> keys = itemsRequested.keySet();
		OrderBox b = new OrderBox(); 
		for (String key : keys) 
		{
			int amtRemoved = 0; 
			while (amtRemoved < itemsRequested.get(key).getAmount()) {
				if (pallets.get(o.getID()).getBox(key) != null) {
					amtRemoved += pallets.get(o.getID()).takeItems(key, itemsRequested.get(key).getAmount()); 
					if (amtRemoved == itemsRequested.get(key).getAmount()) { 
						b.addItem(key, itemsRequested.get(key).getAmount());
					} 
				}
			}
		}
		return b;
	}

	@Override
	public synchronized OrderBox handleOrder() throws RemoteException {

		System.out.println("Waiting to handle orders");
		while (orders.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Order o = orders.poll();
		sendPalletRequest(o.getAllTypes(), id, o.getID());
		System.out.println(Thread.currentThread() + " " + "Has handled an order.");
		return packOrder(o);
	}

	@Override
	public synchronized void receivePallet(Pallet p) {
		pallets.put(p.getID(), p); 
		notifyAll(); 
	}

	@Override
	public String getId() throws RemoteException {
		return id;
	}
}
