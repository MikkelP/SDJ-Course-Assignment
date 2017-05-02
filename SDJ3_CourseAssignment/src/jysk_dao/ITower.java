package jysk_dao;

import jysk_shared.Pallet;

public interface ITower {

	public boolean storePallet(Pallet pallet, String whatTower); 
	
	Pallet retrievePallet(String whatTower); 
	
}
