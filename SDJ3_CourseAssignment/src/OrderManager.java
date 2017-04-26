import java.util.ArrayList;

public class OrderManager {

	private ArrayList<PickStation> pickStations;
	private ArrayList<Order> pendingOrders;
	
	public OrderManager() {
		pickStations = new ArrayList<>();
		pendingOrders = new ArrayList<>();
	}
	
	public PickStation delegateOrder(Order order)
	{
		
	}
	
	public void receiveOrder(Order order)
	{
		
	}

}
