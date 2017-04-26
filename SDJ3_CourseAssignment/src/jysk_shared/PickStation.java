package jysk_shared;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PickStation extends Remote {

	public void receiveOrder(Order order) throws RemoteException;
	
	public void sendAway() throws RemoteException;
	
	public Box handleOrder() throws RemoteException;
	
	public String getId() throws RemoteException;
	
	public void receivePallet (Pallet pallet) throws RemoteException;

}
