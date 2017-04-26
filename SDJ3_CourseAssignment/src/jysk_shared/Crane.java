package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Crane extends Remote {

	public void PickPalletFromConveyer(Pallet pallet) throws RemoteException;
	
	public void PickPalletFromTower(PickStation station) throws RemoteException; 

}
