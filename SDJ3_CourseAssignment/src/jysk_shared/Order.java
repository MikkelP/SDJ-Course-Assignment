package jysk_shared;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> types;
	
	public Order(ArrayList<String> types) {
		this.types = types;
	}	
	
	public ArrayList<String> getTypes() {
		return types; 
	}
}
