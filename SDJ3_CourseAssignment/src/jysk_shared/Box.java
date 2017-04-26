package jysk_shared;
import java.io.Serializable;
import java.util.HashMap;

public class Box implements Serializable {

	private int type;
	private HashMap<Integer, Integer> items;
	
	public Box() 
	{
		items = new HashMap<>();
	}
	
	public Box(int type)
	{
		this();
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public void addItem (int item, int amount)
	{
		items.put(item, amount);
	}
	
	public boolean removeItem (int item, int amount)
	{
		int newAmount = items.get(item) - amount;
		if (newAmount < 0)
		{
			return false;
		}
		items.put(item,  newAmount);
		return true;	
	}

}
