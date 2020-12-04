package app;

/**
 *	InventoryItem represents an inventory item object.
 *
 *	@author	   
 *	@version   1.0
**/
	import java.util.* ;
import java.math.BigDecimal;  // for rounding floats
import java.math.RoundingMode;

import database.DatabasesDAO;
import app.ManageNotifications;

public class InventoryItem implements ManageNotifications{

	    private static int idGen = 0 ; // used to generate ID numbers	
	    private String id;

	    private String itemName; 
	    private String productCode;   //unique identifier for this type of product.

	    private int totalQty = 0;
	    // below is a dictionary item storing job reference numbers and quantities assigned
	    static ArrayList<String> jobs = new ArrayList<String>();
	    static int assignedQty = 0;
	    private double unitCost = 0;
	    static int pendingQty = 0;
	    static int criticalLevel = 0;
	    static String isCritical = "";
	    private String supplier = "";
	    private String manufacturer = "";
	    private Notification notify = new Notification("owner", this.getItemName(), this.getTotalQty());
	   
	    /**
	     *	Creates an InventoryItem object with the specified order, quantity
	     *      and pendingQty.
	     * 	@param item_name - the name of the item being transfered to inventory.
		 *	@param product_code - the product code of the item being transfered to inventory.
	     *	@param qty - the amount of the item being transfered.
	     *  @param cost - the cost of the item being transfered.
	     *	@param pendQty - the amount of items left pending.
	     *  @param producer - the manufacturer
	     *  @param suppl - the supplier
	    **/
	    public InventoryItem(String item_name, String product_code, int qty, double cost, 
	    		int pendQty, String producer, String suppl)
	    {
	        idGen++ ;
	        id = Integer.toString(idGen) ;
	        
	        itemName = item_name;
	        productCode = product_code;
	        totalQty = qty;

	        BigDecimal roundedNum = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
	        unitCost = roundedNum.doubleValue();

	        pendingQty = pendQty;
	        supplier = producer;
	        manufacturer = suppl;
	        isCritical = "Not_Critical";
	        
	        //DatabasesDAO data = new DatabasesDAO();

	    }

	    //constructor 2
	    public InventoryItem(String id_num, String item_name, String product_code, double cost, 
	    int pending_Qty, int total_Qty, String assigned_Data, int assigned_Qty, int critical, 
	    String is_critical, String producer, String suppl)
	    {
	        id = id_num;
	        itemName = item_name; 
	        productCode = product_code;   
	        totalQty = total_Qty;
	        
	        String[] arr = assigned_Data.split(",");
	        for (String str: arr){
	        	jobs.add(str);
	        }
	        
	        assignedQty = assigned_Qty;
	        unitCost = cost;
	        pendingQty = pending_Qty;
	        supplier = producer;
	        manufacturer = suppl;
	        criticalLevel = critical;
	        isCritical = is_critical;
	    }

	    public void evaluateCritical()
	    {
	        if ( (totalQty - assignedQty) > criticalLevel)
	        {
	            isCritical = "Not_Critical" ;
	        } else
	        {
	            isCritical = "Critical" ;
	        }
	    }

	    /**
	     *	Adds the specified quantity to the total quantity.
	     *	@param qty - the amount of the item being received.
	     *  @param cost - the cost of the item being received.
	    **/
	    public void receiveItem(int qty, double cost)
	    {
	        totalQty += qty ;
	        unitCost = (unitCost + cost)/ 2 ;
	        evaluateCritical() ;

	        BigDecimal roundedNum = new BigDecimal(unitCost).setScale(2, RoundingMode.HALF_UP);
	        unitCost = roundedNum.doubleValue();
	    }

	    /**
	     *	Assigns the specified quantity to the specified job.
	     *	@param jobRefNum - the String reference number for the job
	     *	@param qty - the amount of the item being assigned.
	    **/
	    public void assign(String jobRefNum, int qty)
	    {
	        //jobs.put(jobRefNum, qty) ;
	        assignedQty += qty ;
	        evaluateCritical() ;
	    }

	    /**
	     *	returnAssignedQuantity the specified quantity previously assigned to the specified job.
	     *	@param jobRefNum - the String reference number for the job
	     *	@param qty - the amount of the item being assigned.
	    **/
	    public void returnAssignedQuantity(String jobRefNum, int qty)
	    {
	        //int amount = jobs.get(jobRefNum) ;
	    	int amount = 2;
	        if (amount - qty == 0)
	        {
	            jobs.remove(jobRefNum);   // remove from assigned items list
	        }else
	        {
	            assignedQty -= amount ;    // reduce assigned qty.
	        }
	        evaluateCritical() ;

	    }

	    /**
	     *	Removes the specified quantity from the total quantity and from assigned items.
	     *	@param jobRefNum - the String reference number for the job
	     *	@param qty - the amount of the item being used.
	    **/
	    public void useAssignedItem(String jobRefNum, int qty)
	    {
	        totalQty -= qty ;  // reduce total quantity
	        // reduce assigned quantit
	        jobs.remove(jobRefNum);   // remove from assigned items

	        evaluateCritical() ;
	    }

	    /**
	     *	Removes the specified quantity from the assigned items.
	     *	@param jobRefNum - the String reference number for the job
	     *	@param qty - the amount of the item being removed.
	    **/
	    public void removeAssignedItem(String jobRefNum, int qty)
	    {
	        jobs.remove(jobRefNum);   // remove from assigned items
	        evaluateCritical() ;
	    }

	    /**
	     *	Removes the specified quantity from the total quantity.
	     *	@param qty - the amount of the item being removed.
	    **/
	    public void removeUnassignedItem(int qty)
	    {
	        // for removing defective item that was not assigned
	        totalQty -= qty ;  // reduce total quantity

	        evaluateCritical() ;
	    }

	    /**
	     *	Updates information on the inventory item using the specified info.
	     *	@param choice - a String representing the type of update to be done.
	     *	@param update - the information to be used as update.
	    **/
	    public void updateItem(String choice, String update)
	    {
	        if ( choice.equals("Change Name") )
	        {
	            itemName = update;

	        }else if ( choice.equals("Change Product Code") )
	        {
	            productCode = update;

	        }else if ( choice.equals("Change pending") )
	        {
	            pendingQty = Integer.valueOf(update);
	        }
	    }


	    /**
	     *	Updates critical level of the inventory item using the specified integer.
	     *	@param critLevel - an integer to be used to replace curent critical level.
	    **/
	    public void setCriticalLevel(int critLevel)
	    {
	        criticalLevel = critLevel;
	        checkCritical();
	    }
	    
	    private boolean checkCritical(){
	    	if (this.getCriticalLevel() >= this.getTotalQty()){
	    		isCritical = "Critical";
	        	this.addNotification("owner");
	        	return true;
	        }
	    	isCritical = "Not_Critical";
	    	return false;
	    }

	    /**
	     *	Updates critical level of the inventory item using the specified integer.
	     *	@return an integer that states whether the item is at or below critical level (1)
	     *    or not (0).
	    **/
	    public String isCritical()
	    {
	        return isCritical;
	    }

	    public String getSupplier()
	    {
	        return supplier ;
	    }

	    public String getManufacturer()
	    {
	        return manufacturer ;
	    }

	    /**
	     *	Returns the id for this inventory item.
	     *	@return id - a String representing the id.
	    **/
	    public String getId()
	    {
	        return id;
	    }

	    /**
	     *	Returns the name for this inventory item.
	     *	@return itemName - a String representing the item's name.
	    **/
	    public String getItemName()
	    {
	        return itemName;
	    }

	    /**
	     *	Returns the product code for this inventory item.
	     *	@return productCode - a String representing the product code.
	    **/
	    public String getProductCode()
	    {
	        return productCode;
	    }

	    /**
	     *	Returns the total quantity of this inventory item.
	     *	@return totalQty - an integer representing the total quantity.
	    **/
	    public int getTotalQty()
	    {
	        return totalQty;
	    }
	    
	    public String getJobs(){
	    	String myString = "";
	    	for (String str: jobs){
	    		myString.concat(str);
	    	}
	    	return myString;
	    }

	    /**
	     *	Returns the quantity assigned of this inventory item.
	     *	@return assignedQty - an integer representing the quantity assigned.
	    **/
	    public int getAssignedQty()
	    {
	        return assignedQty;
	    }

	    /**
	     *	Returns the total cost of this inventory item.
	     *	@return totalCost - a float representing the total cost.
	    **/
	    public double getUnitCost()
	    {
	        return unitCost;
	    }

	    /**
	     *	Returns the quantity pending of this inventory item.
	     *	@return pendingQty - an integer representing the pending quantity.
	    **/
	    public int getpendingQty()
	    {
	        return pendingQty;
	    }

	    /**
	     *	Returns the critical level of this inventory item.
	     *	@return criticalLevel - an integer representing the critical level.
	    **/
	    public int getCriticalLevel()
	    {
	        return criticalLevel;
	    }

		public double getTotalCost() {
			// TODO Auto-generated method stub
			return this.totalQty*this.unitCost;
		}

		@Override
		public boolean addNotification(String username) {
			// TODO Auto-generated method stub
			DatabasesDAO data = new DatabasesDAO();
			int saveNotif = data.saveNotification(notify);
			if (saveNotif==1){
				System.out.printf(" %s - true", saveNotif);
				return true;
			}
			System.out.printf(" %s - false", saveNotif);
			return false;
		}

		@Override
		public boolean deleteNotification(Notification alert) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean findNotification(Notification notification) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean assignNotification() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean updateNotifications() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ArrayList<Notification> pullNotifications(String type) {
			// TODO Auto-generated method stub
			ArrayList<Notification> arr = new ArrayList<Notification>();
			List<Notification> list = new DatabasesDAO().allNotifications();
			for (Notification n: list){
				arr.add(n);
			}
			return arr;
		}

//		public static void main(String[] args){
//			//checks access to database via DatabasesDAO
//			InventoryItem inv1 = new InventoryItem("test_item1", "Test_prod_code1", 5, 110.15, 
//		    		2, "Test_Producer1", "Test_Supplier1");
//			InventoryItem inv2 = new InventoryItem("test_item2", "Test_prod_code2", 12, 120.15, 
//		    		2, "Test_Producer2", "Test_Supplier1");
//			InventoryItem inv3 = new InventoryItem("test_item3", "Test_prod_code3", 19, 130.15, 
//		    		2, "Test_Producer1", "Test_Supplier2");
//			inv1.setCriticalLevel(5);
//			inv2.setCriticalLevel(10);
//			inv3.setCriticalLevel(15);
//		}
	
}

