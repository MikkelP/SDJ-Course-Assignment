package jysk_shared;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Hashtable<String, Item> items;
	private int orderID; 

	public Order(int id) {
		orderID = id; 
		items = new Hashtable<String, Item>();
	}	

	public int getID() {
		return orderID; 
	}
	
	public Hashtable<String, Item> getRequestedItems() {
		return items; 
	}

	public void addItem(String itemName, Item item) {
		items.put(itemName, item); 
	}

	public Item getItem(String whatItem) 
	{
		return items.get(whatItem); 	
	}
	
	public ArrayList<String> getAllTypes() {
		ArrayList<String> arr = new ArrayList<String>();
		Set<String> keys = items.keySet(); 
		for (String key : keys) 
		{
			arr.add(items.get(key).getType()); 
		}
		return arr; 
	}
	
}
