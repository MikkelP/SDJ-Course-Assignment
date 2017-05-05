package jysk_shared;
import java.io.Serializable;
import java.util.ArrayList;

public class Pallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Box> boxes;
	private String palletType; 
	private int palletID;
	private int orderID; 
	
	public Pallet(String palletType, int palletID) 
	{
		boxes = new ArrayList<>();
		this.palletType = palletType; 
		this.palletID = palletID; 
	}
	
	public void addBox(Box box)
	{
		boxes.add(box);
	}
	
	public ArrayList<Box> getBoxes() {
		return boxes; 
	}
	
	public boolean removeBox(String item, int amount)
	{
		for (int i = 0; i < boxes.size(); i++) 
		{
			if(boxes.get(i).getType() == item)
			{
				return boxes.get(i).removeItem(amount);
			}
		}
		return false; 
	}
	
	public int amountOfBoxes() {
		return boxes.size(); 
	}
	
	public String getType() {
		return palletType; 
	}
	
	public int getID() {
		return palletID; 
	}
	
	public int getOrderID() {
		return orderID; 
	}
	
	public void setOrderID(int id) {
		orderID = id; 
	}

}
