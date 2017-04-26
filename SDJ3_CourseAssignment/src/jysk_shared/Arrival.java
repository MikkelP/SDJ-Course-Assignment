package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Arrival extends Remote {

	public void putOnConveyer(Pallet pallet) throws RemoteException;

}