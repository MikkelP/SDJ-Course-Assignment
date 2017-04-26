package jysk_shared;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Arrival extends Remote {

	public void sendToCrane(Pallet pallet) throws RemoteException;
	
	public String getID() throws RemoteException;

	public void openArrivalStation() throws RemoteException, NotBoundException; 

}