package jysk_conv;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

import jysk_shared.Arrival;
import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemoteConveyer extends UnicastRemoteObject implements Conveyer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Pallet> pallets; 
	private Hashtable<String, PickStation> pickstations; 
	private Hashtable<String, Arrival> arrivalstations;
	private Hashtable<String, Crane> cranes; 


	public RemoteConveyer() throws RemoteException
	{
		pallets = new ArrayList<Pallet>(); 	
		pickstations = new Hashtable<String, PickStation>(); 
		arrivalstations = new Hashtable<String, Arrival>();
		cranes = new Hashtable<String, Crane>(); 
	}

	public RemoteConveyer(ArrayList<Pallet> pallets) throws RemoteException {
		this.pallets = pallets; 
	}

	@Override
	public void sendTo(Pallet pallet, String destinationID, String sendWhere) throws RemoteException {

		switch (sendWhere) {

		case "Pickstation":
			pickstations.get(destinationID).receivePallet(pallet);
			break;

		case "Crane":
			cranes.get(destinationID).pickPalletFromConveyer(pallet);
			break;

		default: 
			break; 
		}
	}

	@Override
	public void registerCrane(Crane crane) throws RemoteException {
		cranes.put(crane.getCraneID(), crane);	
	}

	@Override
	public void registerArrivalStation(Arrival station) throws RemoteException {
		arrivalstations.put(station.getID(), station); 
	}

	@Override
	public void registerPickupStation(PickStation station) throws RemoteException {
		pickstations.put(station.getId(), station); 
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
