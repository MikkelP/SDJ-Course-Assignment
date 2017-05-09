package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import jysk_conv.RemoteConveyer;
import jysk_dao.RemoteCrane;
import jysk_dao.Tower;
import jysk_shared.Arrival;
import jysk_shared.Box;
import jysk_shared.Conveyer;
import jysk_shared.Crane;
import jysk_shared.Item;
import jysk_shared.Order;
import jysk_shared.Pallet;
import jysk_shared.PickStation;
import jysk_stations.RemoteArrival;
import jysk_stations.RemotePickStation;

public class Setup {

	public static void main(String[] args) throws NotBoundException 
	{
		Tower tower1 = new Tower("tower1", "sengetoej");
		Tower tower2 = new Tower("tower2", "havemobler"); 
		Tower tower3 = new Tower("tower3", "kontor");

		try {
			Conveyer conv = new RemoteConveyer();
			conv.startConveyer();

			Arrival as = new RemoteArrival("arrival1");

			Crane c1 = new RemoteCrane(tower1, "sengetoej");
			Crane c2 = new RemoteCrane(tower2, "havemobler");
			Crane c3 = new RemoteCrane(tower3, "kontor");

			as.openArrivalStation();

			Pallet p = new Pallet("sengetoej", 69);
			Box b = new Box("sengetoej", "dyne", 10);
			for (int i = 0; i < 5; i++) {
				p.addBox(b);
			}

			//as.sendToCrane(p);
			Order o = new Order(5); 
			Item i = new Item("sengetoej", "dyne", 12);
			o.addItem("dyne", i);
			PickStation ps = new RemotePickStation("pick1");
			ps.setUpConnection("sengetoej");
			boolean yesorder = ps.receiveOrder(o);
		} catch (RemoteException e) {
			e.printStackTrace();
		}





	}
}
