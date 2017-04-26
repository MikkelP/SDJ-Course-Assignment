package jysk_conv;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.Pallet;

public class RemoteConveyer extends UnicastRemoteObject implements Conveyer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Pallet> pallets; 
    
	
	public RemoteConveyer() throws RemoteException
	{
		pallets = new ArrayList<Pallet>(); 	
	}

	public RemoteConveyer(ArrayList<Pallet> pallets) throws RemoteException {
		this.pallets = pallets; 
	}

	@Override
	public void sendTo(Pallet pallet, int destination) throws RemoteException {
		

	}

	@Override
	public void addCrane(Crane crane) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startConveyer() throws RemoteException, NotBoundException {
		RemoteConveyer rconv = new RemoteConveyer();
		Conveyer conv = (Conveyer) UnicastRemoteObject.exportObject(rconv, 8080);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("Conveyer", conv);
		System.out.println("Conveyer belt running...");
	}
}
