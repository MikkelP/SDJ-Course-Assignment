package jysk_conv;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import jysk_shared.Arrival;
import jysk_shared.Conveyer;
import jysk_shared.Pallet;

public class RemoteArrival extends UnicastRemoteObject implements Arrival {

	private String stationID; 
	private Conveyer conveyerBelt; 

	protected RemoteArrival(String stationID) throws RemoteException {
		super();
		this.stationID = stationID; 	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sendToCrane(Pallet pallet) throws RemoteException {
		conveyerBelt.sendTo(pallet, pallet.getType(), "Crane");
	}

	@Override
	public String getID() {
		return stationID; 
	}

	@Override
	public void openArrivalStation() throws RemoteException, NotBoundException {
		Registry registry;
		registry = LocateRegistry.getRegistry(1099);
		registry.rebind(this.getID(), this);
		Conveyer conv = (Conveyer) registry.lookup("Conveyer");
		conv.registerArrivalStation(this);
		conveyerBelt = conv; 
	}

}
