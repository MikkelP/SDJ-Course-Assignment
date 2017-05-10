package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;



 
public interface CraneManager extends Remote 
{

	public void registerPickStation(PickStation pc) throws RemoteException; 
	
	public boolean retrievePallet(String pickstationID, int orderID, String type) throws RemoteException; 
	
	public void storePallet(Pallet p) throws RemoteException; 
	
	public void registerCrane(Crane c) throws RemoteException;

	void registerLog(IResultLogging rl) throws RemoteException;

	void storeData(String sendFrom, String sendTo, long timeTaken) throws RemoteException;

	ArrayList<DataCollection> retrieveData(String sendFrom) throws RemoteException; 
		
}
