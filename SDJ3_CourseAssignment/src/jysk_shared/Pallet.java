package jysk_shared;
import java.util.ArrayList;

public class Pallet {

	private ArrayList<Box> boxes;
	
	public Pallet() 
	{
		boxes = new ArrayList<>();
	}
	
	public void addBox(Box box)
	{
		boxes.add(box);
	}
	
	public void removeBox(int type, int item, int amount)
	{
		for (int i = 0; i < boxes.size(); i++) 
		{
			if(boxes.get(i).getType() == type)
			{
				boxes.get(i).removeItem(item, amount);
			}
		}
	}

}
