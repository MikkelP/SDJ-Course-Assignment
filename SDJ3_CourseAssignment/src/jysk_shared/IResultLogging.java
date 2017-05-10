package jysk_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IResultLogging extends Remote {

	public void storeData(String sendFrom, String sendTo, long timeTaken) throws RemoteException;
	
	public ArrayList<DataCollection> retrieveData(String sendFrom) throws RemoteException; //Retrieves all data from 'sendFrom'.
	
}
