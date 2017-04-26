package jysk_conv;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jysk_shared.Crane;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemoteCrane extends UnicastRemoteObject implements Crane {

	private ITower towerData; 
	
	protected RemoteCrane() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void pickPalletFromConveyer(Pallet pallet) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickPalletFromTower(PickStation station) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCraneID() {
		// TODO Auto-generated method stub
		return null;
	}

}
