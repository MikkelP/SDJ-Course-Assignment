package jysk_stations;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import jysk_shared.Box;
import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.Order;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemotePickStation extends UnicastRemoteObject implements PickStation {

	private Queue<Order> orders; 
	private Hashtable<Integer, Pallet> pallets; 
	private Crane crane; 
	private String id; 

	protected RemotePickStation() throws RemoteException {
		super();
		orders = new LinkedList<Order>();
		pallets = new Hashtable<Integer, Pallet>(); 
	}

	void setUpConnection() {
		Crane c = null;
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(this.id, this);
			c = (Crane) registry.lookup("Crane");
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean receiveOrder(Order order) throws RemoteException {
		return orders.add(order); 
	}

	@Override
	public Order sendAway() throws RemoteException {
		return null;

	}

	private void sendPalletRequest(ArrayList<String> types, String myId, int orderID) {
		for (int i = 0; i < types.size(); i++) {
			try {
				boolean success = crane.retrievePallet(myId, orderID, types.get(i));
			    System.out.println("Type: "+types.get(i) + " was retrieved: "+success);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			} 
		}
	}
	
	private Box packOrder(Order o) 
	{
		while(pallets.get(o.getID()) == null) {
			try {
			wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized Box handleOrder() throws RemoteException {

		while (orders.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Order o = orders.poll();
		sendPalletRequest(o.getTypes(), id, o.getID());
		notifyAll();
	}
	
	@Override
	public synchronized void receivePallet(Pallet p) {
		pallets.put(p.getID(), p); 
		notifyAll(); 
	}

	@Override
	public String getId() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
