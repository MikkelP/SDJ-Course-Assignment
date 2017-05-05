package jysk_shared;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> types;
	private Hashtable<String, Integer> items;
	private int orderID; 

	public Order(ArrayList<String> types, int id) {
		this.types = types;
		orderID = id; 
	}	

	public ArrayList<String> getTypes() {
		return types; 
	}

	public int getID() {
		return orderID; 
	}

	public void addItem(String item, int amount) {
		items.put(item, amount); 
	}

	public int getAmtOf(String whatItem) 
	{
		return items.get(whatItem); 	
	}
}
