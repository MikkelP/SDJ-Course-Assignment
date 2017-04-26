
public class Setup {

	public static void main(String[] args) 
	{
		Conveyer conveyer = new Conveyer();
		Arrival arrival = new Arrival(conveyer);
		
		Crane crane1 = new Crane(conveyer);
		Crane crane2 = new Crane(conveyer);
		Crane crane3 = new Crane(conveyer);
		
		OrderManager orderManager = new OrderManager();
	}

}
