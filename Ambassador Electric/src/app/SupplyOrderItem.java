package supply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SupplyOrderItem {
	
	private double unitCost;
	private double totalCost; //Large cost???
	private int quantity;
	private String ItemName;
	private String productCode; //Do we need an Id if we have this ?
	private String manufacturer;
	private String supplier;
	private int id;
	private int orderNo;
	private Date orderedDate;/// Import
	private static int count;
	private String status;
	

	//Constructor 
	
	public SupplyOrderItem( String ItemName, String productCode, String manufacturer,  String supplier, int quantity, double unitCost, double totalCost) {
		this.unitCost=unitCost;
		this.totalCost=totalCost; //Large cost???
		this.quantity=quantity;
		this.ItemName=ItemName;
		this.productCode=productCode; //Do we need an Id if we have this ?
		this.manufacturer=manufacturer;// Why we need this ?
		this.supplier=supplier;// Why we need this ?
		this.id=count++;
		this.orderedDate=Calendar.getInstance().getTime();//DO I  NEED A NEW DATE ?////HELP
		this.status="pending";
		
	}
	
	public SupplyOrderItem(int id, String ItemName, int orderNo, String productCode, String manufacturer,  String supplier, int quantity, double unitCost, double totalCost, String date, String status) {
		this.id=id;
		this.unitCost=unitCost;
		this.totalCost=totalCost;
		this.quantity=quantity;
		this.ItemName=ItemName;
		this.productCode=productCode; //Do we need an Id if we have this ?
		this.manufacturer=manufacturer;// Why we need this ?
		this.supplier=supplier;// Why we need this ?
		this.id=count++;
		SimpleDateFormat formatter=new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");  
		try {
			this.orderedDate=formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		this.status=status;
		this.count=id++;
		this.orderNo=orderNo;
		
	}
	
	//GETTERS
	public int getId(){
        return this.id;
    }
	
    public String getName(){
        return this.ItemName;
    }

    public int getQuantity(){
        return this.quantity;
    }
    
    public double getUnitCost(){
        return this.unitCost;
    }

    public String getProductCode(){
        return this.productCode;
    }

    public String getManufacturer(){
        return this.manufacturer;
    }
    
    public String getSupplier(){
        return this.supplier;
    }
    
    
    public int getSupplyOrderNo() {
		// TODO Auto-generated method stub
		return this.orderNo;
	}

	public double getTotalCost() {
		// TODO Auto-generated method stub
		return this.totalCost;
	}

	public String getOrderedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");  
		String date = formatter.format(this.orderedDate);  
		return date ;
	}
	
	 public String getStatus(){
	        return this.status;
	    }
	 

    //SETTERS
    
    public void changeUnitCost(double cost) {
    	this.unitCost=cost;
    	this.totalCost=cost*this.quantity;
    }
    
    
    public void setQuantity(int qty) {
    	this.quantity=qty;
    	
    }	
    
    public void setStatus(String status) {
    	this.status=status;
    	
    }	
    //Why would we want to change the name, code,manufacturer of an item etc?/ Wouldn't we want to just delete an item if name is incorrect

	public int getOrderNo() {
		// TODO Auto-generated method stub
		return this.orderNo;
	}
	
	public void setOrderNo(int num) {
		// TODO Auto-generated method stub
		this.orderNo=num;
	}
    
//NEED TO FIND A WAY TO SAVE WHEN QYT OR COST IS CHANGED

}
