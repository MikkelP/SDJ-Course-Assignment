package jysk_shared;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

	private ArrayList<Integer> types;
	
	public Order(ArrayList<Integer> types) {
		this.types = types;
	}	
}
