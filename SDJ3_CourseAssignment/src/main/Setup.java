package main;

import jysk_dao.Tower;
import jysk_shared.Box;
import jysk_shared.Pallet;

public class Setup {

	public static void main(String[] args) 
	{
		Tower tower = new Tower("tower1", "sex toys");
//		Pallet p = new Pallet("sex toys",3);
//		p.addBox(new Box("sex toys", "butt fucker 3000", 15));
//		p.addBox(new Box("sex toys", "tentacles", 10));
//		p.addBox(new Box("sex toys", "dildo", 4));
//		tower.storePallet(p);
		Pallet myPallet = tower.retrievePallet();
		System.out.println(myPallet.getBoxes());
		
	}
}
