package jysk_dao;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import jysk_shared.Crane;
import jysk_shared.CraneManager;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemoteCraneManager implements CraneManager {
	
	private Hashtable<String, Crane> cranes; 
	private Hashtable<String, PickStation> pcs;

	public RemoteCraneManager() throws RemoteException {
	    cranes = new Hashtable<String, Crane>(); 
	    try {
			setUpCraneManager();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	    pcs = new Hashtable<String, PickStation>();
	}
	
	@Override
	public void registerPickStation(PickStation pc) throws RemoteException {
		try {
			pcs.put(pc.getId(), pc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean retrievePallet(String pickstationID, int orderID, String type) throws RemoteException {
		System.out.println(type);
		return cranes.get(type).retrievePallet(pickstationID, orderID, type);
	}

	@Override
	public void storePallet(Pallet p) throws RemoteException {
		cranes.get(p.getType()).storePallet(p);
	}

	@Override
	public void registerCrane(Crane c) throws RemoteException {
		System.out.println("Registering crane type: "+c.getCraneID());
		try {
			cranes.put(c.getCraneID(), c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	
	private void setUpCraneManager() throws RemoteException, NotBoundException {
		CraneManager cm = (CraneManager) UnicastRemoteObject.exportObject(this, 8080);
		Registry registry = LocateRegistry.createRegistry(1098);
		registry.rebind("CraneManager", cm);
		System.out.println("CraneManager is running...");
	}

}
