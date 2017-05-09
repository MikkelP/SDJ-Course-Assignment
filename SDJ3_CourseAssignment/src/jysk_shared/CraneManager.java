package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;


 //NOT IN USE - To be discussed
public interface CraneManager extends Remote 
{

	public void registerPickStation(PickStation pc) throws RemoteException; 
	
	public Pallet retrievePallet(String pickstationID, int orderID, String type) throws RemoteException; 
	
	public void storePallet(Pallet p) throws RemoteException; 
		
}
