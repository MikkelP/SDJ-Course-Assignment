package jysk_shared;
import java.io.Serializable;

public class Box implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String item; 
	private int amount;
	
	public Box(String type, String item, int amount)
	{
		this.item = item;
		this.amount = amount; 
		this.type = type;
	}

	public int getAmount() {
		return amount; 
	}

	public String getType() {
		return type;
	}

	public String getItem() {
		return item; 
	}

	public void addItem (int amount)
	{
		this.amount += amount; 
	}

	public int removeItems (int amount) //Should return the amount removed
	{
		if (amount > this.amount) {
			amount = this.amount;
		}
		
		int result = this.amount - amount;
		this.amount = result;
		return amount;
	}

	public String toString() {
		return "Type: "+ type + "\n" +
				"Item: "+ item +"\n"+
				"Amount: "+ amount; 
	}
}
