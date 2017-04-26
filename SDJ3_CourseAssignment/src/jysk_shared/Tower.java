package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Tower extends Remote {

	public void storePallet(Pallet pallet) throws RemoteException;
	
	public void retrievePallet() throws RemoteException;

}
