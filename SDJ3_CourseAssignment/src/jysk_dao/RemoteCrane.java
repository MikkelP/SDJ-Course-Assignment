package jysk_dao;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.CraneManager;
import jysk_shared.Pallet;

public class RemoteCrane extends UnicastRemoteObject implements Crane {

	private ITower towerData; 
	private Conveyer conveyer;
	private String craneID; 

	public RemoteCrane(Tower tower, String craneID) throws RemoteException {
		super(8080);
		towerData = tower;
		this.craneID = craneID; 
		doRegister(); 
		lookUpCraneManager();
		try {
			setUpCraneServer(tower);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
		
	private void lookUpCraneManager() 
	{
		try {
			Registry registry = LocateRegistry.getRegistry(1098);
			registry.rebind(this.getCraneID(), this);
			CraneManager cm = (CraneManager) registry.lookup("CraneManager");
			cm.registerCrane(this);
		} catch (RemoteException e1) {
			System.err.println("Crane did not register properly to the CraneManager." +"\n"
					+ "This is likely due to connection issues.");
			e1.printStackTrace();
			return;
		} catch (NotBoundException e2) {
			System.err.println("Crane did not register properly to the CraneManager."+"\n"
					+ "This is due to trying to look up a register that has no associated binding.");
			return;
		}
	}
	
	private void setUpCraneServer(Tower tower) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1098);
		if (registry == null) {
			registry = LocateRegistry.createRegistry(1098);
		}
		registry.rebind(this.craneID, this);
		System.out.println("Crane is running...");
	}

	private void doRegister() {
		Conveyer conv = null;
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.rebind(this.getCraneID(), this);
			conv = (Conveyer) registry.lookup("Conveyer");
			conv.registerCrane(this);
		} catch (RemoteException e1) {
			System.err.println("Crane did not register properly to the Conveyer belt." +"\n"
					+ "This is likely due to connection issues.");
			e1.printStackTrace();
			return;
		} catch (NotBoundException e2) {
			System.err.println("Crane did not register properly to the Conveyer belt."+"\n"
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
	public void storePallet(Pallet pallet) throws RemoteException {
		if (conveyer != null) {
			towerData.storePallet(pallet); 
		} else {
			doRegister(); 
		}
	}

	@Override
	public boolean retrievePallet(String pickstationID, int orderID, String type) throws RemoteException {
		if (conveyer != null) {
			Pallet p = towerData.retrievePallet();	
			if (p != null) {
				p.setOrderID(orderID);
				System.out.println("shit son");			
				conveyer.sendTo(p, pickstationID, "Pickstation");
				return true; 
			} else {
				return false; 
			}
		} else { 
			doRegister(); 
			return false;
		}
	}

	@Override
	public String getCraneID() {
		return craneID; 
	}

}
