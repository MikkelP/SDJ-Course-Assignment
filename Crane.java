
public class Crane {

	private Conveyer conveyer;
	private int type;
	private Tower tower;
	private int destinationId;
	
	public Crane(Conveyer conveyer) {
		this.conveyer = conveyer;
		conveyer.addCrane(this);
	}
	
	public void PickPalletFromConveyer(Pallet pallet)
	{
		
	}
	
	public void PickPalletFromTower(PickStation station)
	{
		
	}

}
