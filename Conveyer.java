import java.util.ArrayList;

public class Conveyer {

	private ArrayList<Pallet> pallets;
	private ArrayList<Crane> cranes;
	
	public Conveyer() 
	{
		pallets = new ArrayList<>();
	}
	
	public void sendTo(Pallet pallet, int destination)
	{
		
	}
	
	public void addCrane(Crane crane)
	{
		cranes.add(crane);
	}

}
