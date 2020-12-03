package supply;

import java.util.*;
import supply.*;

public class ManageSupplyOrder {
	
	private ArrayList <SupplyOrder> orderList;
	//private String id;
	
	
	
	public ArrayList<SupplyOrder> getSupplyOrderItems(ArrayList orderItems) {
		return this.orderList=orderItems;
		
	}
	
	
	
	public void createOrder(float unitCost, double total, int quantity, String ItemName, String productCode, String manufactureer, String supplier, int id) {
		this.orderList.add(new SupplyOrder(unitCost,total,quantity,ItemName,productCode,manufactureer,supplier,id));
	}
	
	 
	public void cancelItem(int id){
		for(SupplyOrder order: orderList){
		  this.order.remove(getItem(id));
		}  
	  }
	public void updateOrder (ArrayList updatedOrder) {
		
		this.orderList=updatedOrder;
	}
	

	
	

}
