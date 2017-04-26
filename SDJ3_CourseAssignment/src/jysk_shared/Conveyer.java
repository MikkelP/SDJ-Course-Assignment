package jysk_shared;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conveyer extends Remote {

		
	public void startConveyer() throws RemoteException, NotBoundException;

	void registerCrane(Crane crane) throws RemoteException;

	void registerPickupStation(PickStation station) throws RemoteException;

	void registerArrivalStation(Arrival station) throws RemoteException;

	void sendTo(Pallet pallet, String destinationID, String sendWhere) throws RemoteException;

}
