package jysk_shared;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conveyer extends Remote {

	
	public void sendTo(Pallet pallet, int destination) throws RemoteException;
	
	public void addCrane(Crane crane) throws RemoteException;
	
	public void startConveyer() throws RemoteException, NotBoundException;

}
