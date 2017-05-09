package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Crane extends Remote {

	public void storePallet(Pallet pallet) throws RemoteException;
	
	public String getCraneID() throws RemoteException;
	
	void registerPickStation(PickStation pc) throws RemoteException;

	boolean retrievePallet(String pickstationID, int orderID, String type) throws RemoteException;

}
