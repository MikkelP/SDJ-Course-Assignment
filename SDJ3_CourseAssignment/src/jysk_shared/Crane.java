package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Crane extends Remote {

	public void storePallet(Pallet pallet) throws RemoteException;
	
	public String getCraneID();
	
	boolean retrievePallet(String pickstationID, String type) throws RemoteException;

}
