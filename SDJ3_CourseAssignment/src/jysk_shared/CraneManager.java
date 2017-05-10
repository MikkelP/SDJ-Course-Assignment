package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;


 
public interface CraneManager extends Remote 
{

	public void registerPickStation(PickStation pc) throws RemoteException; 
	
	public boolean retrievePallet(String pickstationID, int orderID, String type) throws RemoteException; 
	
	public void storePallet(Pallet p) throws RemoteException; 
	
	public void registerCrane(Crane c) throws RemoteException; 
		
}
