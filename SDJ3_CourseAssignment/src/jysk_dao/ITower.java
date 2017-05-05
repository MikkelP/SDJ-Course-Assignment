package jysk_dao;

import jysk_shared.Pallet;

public interface ITower {

	Pallet retrievePallet();

	boolean storePallet(Pallet pallet); 
	
}
