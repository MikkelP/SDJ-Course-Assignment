package jysk_shared;

import java.util.Hashtable;
import java.util.Set;

public class OrderBox
{
	private Hashtable<String, Integer> items; 
	
	public OrderBox() {
		items = new Hashtable<String, Integer>(); 
	}
	
	public void addItem(String itemName, int amount) {
		items.put(itemName, amount); 
	}
	
	public String toString() {
		String s = ""; 
		Set<String> keys = items.keySet(); 
		for (String key : keys) {
			s += items.get(key) + " "; 
		}
	return s;	
	}
}
