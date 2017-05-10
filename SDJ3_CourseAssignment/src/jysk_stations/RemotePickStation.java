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
import jysk_shared.CraneManager;
import jysk_shared.Item;
import jysk_shared.Order;
import jysk_shared.OrderBox;
import jysk_shared.Pallet;
import jysk_shared.PalletCollection;
import jysk_shared.PickStation;

public class RemotePickStation extends UnicastRemoteObject implements PickStation {

	private Queue<Order> orders; 
	private Hashtable<Integer, PalletCollection> pallets; 
	private CraneManager craneManager; 
	private String id; 
	private Conveyer conveyer;

	public RemotePickStation(String id) throws RemoteException {
		super();
		orders = new LinkedList<Order>();
		pallets = new Hashtable<Integer, PalletCollection>(); 
		this.id = id;
		doRegister(); //Conveyer belt
		setUpConnection(); //Crane manager
		Thread t = new Thread(new OrderHandler(this, true));
		t.start();
	}

	private void setUpConnection() {
		CraneManager c = null;
		try {
			Registry registry = LocateRegistry.getRegistry(1098);
			registry.rebind(this.id, this);
			c = (CraneManager) registry.lookup("CraneManager");
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
		craneManager = c; 
	}

	private void doRegister() {
		Conveyer conv = null;
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(this.id, this);
			conv = (Conveyer) registry.lookup("Conveyer");
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
		conveyer = conv; 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized boolean receiveOrder(Order order) throws RemoteException {
		boolean p = orders.add(order);
		notifyAll();
		return p;
	}

	@Override
	public Order sendAway() throws RemoteException {
		return null;

	}

	private void sendPalletRequest(ArrayList<String> types, String myId, int orderID) {
		for (int i = 0; i < types.size(); i++) {
			try {
				craneManager.retrievePallet(myId, orderID, types.get(i));		
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
			int initialRequest = itemsRequested.get(key).getAmount();
			System.out.println("Handling item(s) "+key);
			while (amtRemoved < initialRequest) {
				if (pallets.get(o.getID()).getBox(key) != null) {
					amtRemoved += pallets.get(o.getID()).getBox(key).removeItems(itemsRequested.get(key).getAmount()); 
					itemsRequested.get(key).setAmount(itemsRequested.get(key).getAmount() - amtRemoved);
					//System.out.println("amount removed "+ amtRemoved);
					if (amtRemoved == initialRequest) { 
						b.addItem(key, amtRemoved);
						break;
					} 
				}
			}
		}

		sendBackPallet(pallets.get(o.getID())); 
		pallets.remove(o.getID());
		notifyAll();
		return b;
	}

	@Override
	public synchronized OrderBox handleOrder() throws RemoteException {

		while (orders.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Order o = orders.poll();
		sendPalletRequest(o.getAllTypes(), id, o.getID());
		notifyAll(); 
		return packOrder(o);
	}

	@Override
	public void receivePallet(Pallet p) {
		if(pallets.get(p.getOrderID()) != null) {
			pallets.get(p.getOrderID()).addPallet(p.getType(), p);
			System.out.println("Putting in " +p.getType());
		} else {
			PalletCollection pc = new PalletCollection();
			pc.addPallet(p.getType(), p);
			pallets.put(p.getOrderID(), pc);
			System.out.println("Putting in " +p.getType());
		} 
	}

	@Override
	public String getId() throws RemoteException {
		return id;
	}

	private void sendBackPallet(PalletCollection p) {
		try {
			Set<String> keys = p.getCollection().keySet();
			for (String key : keys) {
				conveyer.sendTo(p.getPallet(key), p.getPallet(key).getType(), "Crane");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
