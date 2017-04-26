package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Crane extends Remote {

	public void pickPalletFromConveyer(Pallet pallet) throws RemoteException;
	
	public void pickPalletFromTower(PickStation station) throws RemoteException; 
	
	public String getCraneID();

}
