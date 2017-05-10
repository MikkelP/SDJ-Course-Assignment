package jysk_stations;

import java.rmi.RemoteException;

import jysk_shared.OrderBox;
import jysk_shared.PickStation;

public class OrderHandler implements Runnable {

	private PickStation p;
	private boolean doRun;
	
	public OrderHandler(PickStation p, boolean doRun) {
		this.p = p;
		this.doRun = doRun;
	}
	
	public void setRun(boolean run) {
		doRun = run;
	}
	
	@Override
	public void run() {
		while (doRun) {
			OrderBox b = null;
			try {
				b = p.handleOrder();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("Box was taken care of: "+ b);
		}	
	}
}
