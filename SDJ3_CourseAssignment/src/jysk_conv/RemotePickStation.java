package jysk_conv;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jysk_shared.Box;
import jysk_shared.Order;
import jysk_shared.Pallet;
import jysk_shared.PickStation;

public class RemotePickStation extends UnicastRemoteObject implements PickStation {

	protected RemotePickStation() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void receiveOrder(Order order) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAway() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Box handleOrder() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receivePallet(Pallet pallet) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
