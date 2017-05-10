package jysk_dao;

import java.util.ArrayList;

import jysk_shared.DataCollection;
import jysk_shared.Pallet;

public interface ITower {

	Pallet retrievePallet();

	boolean storePallet(Pallet pallet); 
	
	ArrayList<DataCollection> retrieveData(String sendFrom);

	void storeData(String from, String to, long timeTaken); 
	
}
