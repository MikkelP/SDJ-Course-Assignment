package jysk_shared;

public class Item 
{
	private int amount;
	private String itemName;
	private String type;
	
	public Item(String name, String type, int amt) {
		itemName = name;
		this.type = type; 
		amount = amt;
	}
	
	public String getType() {
		return type; 
	}
	
	public String getItemName() {
		return itemName; 
	}
	
	public int getAmount() {
		return amount; 
	}
	
	public String toString() {
		return "Item: " + itemName + "\n"
				+ "Type: "+ type + "\n" 
				+ "Amount: "+amount; 
	}
}
