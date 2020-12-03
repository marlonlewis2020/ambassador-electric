package supply;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SupplyOrder {
	private int id;
	private static int count=0;
	private ArrayList<SupplyOrderItem>Items;
	private String Updates;
	private String status;
	private Date date;
	private String supplier;
	
	
	//Constructors 

	public SupplyOrder( ArrayList ItemsList, String supplier){
		this.id=count++;
		this.Items=ItemsList;
		this.status="pending";
		this.date=Calendar.getInstance().getTime();
		this.supplier=supplier;
		this.Updates="";
	}
	
	public SupplyOrder(int id, ArrayList ItemsList, String date, String supplier, String status,String Updates){
		this.id=id;
		this.count=id;
		this.Items=ItemsList;
		this.status=status;
		this.Updates=Updates;
		this.supplier=supplier;
		SimpleDateFormat formatter=new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");  
		try {
			this.date=formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS
		
	public int getId(){
		return this.id;
	}
	
	public SupplyOrderItem getItem(int id){
		for (SupplyOrderItem item:Items){
            if (item.getId()==id) {
            	return item;
            }
		}
		return null;
	}
 
	public ArrayList<SupplyOrderItem> getItems() {	
		return this.Items;
	}
	  
	public String getStatus() {	
		return this.status;
	}
	
	public String getUpdates() {	
		return this.Updates;
	}
	
	public String getSupplier(){
        return this.supplier;
    }
	
	public String getdate(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");  
		String date = formatter.format(this.date);  
		return date ;
	}
	
	public String getItemIdList(){
		 String itemIds="";
			for (SupplyOrderItem item: Items ){
				  itemIds=itemIds+ "" +item.getId()+",";
			}
	        return itemIds;
	    }
	
	//SETTER & OTHER METHODS
	
	public void setStatus(String status) {	
		 this.status=status;
	}
	
	public void amendUpdates(String updates) {
		this.Updates=this.Updates+"\n "+ updates;
	}
	
	
  public void CancelItem(SupplyOrderItem item){
        this.Items.remove(item);
 }
  
  public void AddItem(SupplyOrderItem item) {
	  this.Items.add(item);  
  }

  public void UpdateItem(int id, String attribute, String newValue, String reason) { 
	  Date today = Calendar.getInstance().getTime();
	  for (SupplyOrderItem item :Items){
        if (item.getId()==id) {
			  if (attribute=="cost"){
		   		double oldValue= item.getUnitCost();
		   		item.changeUnitCost(Float.parseFloat(newValue));
		   		this.Updates+=Updates+" \n"+ today +": The unit cost was changed from "+ oldValue + " to " + newValue + " because "  + reason; //
		   	  }else if (attribute=="qty"){
		     	float oldValue= item.getQuantity();
		     	item.setQuantity(Integer.parseInt(newValue));
		     	this.Updates+=Updates+" \n"+ today +": The ordered quantity was changed from "+ oldValue + " to " + newValue + " because "  + reason;
		   	  }	
		  }
	  }	
  }
}


