package jysk_shared;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PickStation extends Remote {

	public boolean receiveOrder(Order order) throws RemoteException;
	
	public Order sendAway() throws RemoteException;
	
	public OrderBox handleOrder() throws RemoteException;
	
	public String getId() throws RemoteException;
	
	public void receivePallet (Pallet pallet) throws RemoteException;

	void setUpConnection(String connectTo) throws RemoteException;

}
