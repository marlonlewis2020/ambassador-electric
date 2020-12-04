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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import database.DatabasesDAO;
import app.ManageNotifications;

public class InventoryItem implements ManageNotifications{

	    private static int idGen = 0 ; // used to generate ID numbers	
	    private String id;

	    private String itemName; 
	    private String productCode;
	    private int totalQty = 0;
	    private ArrayList<String> jobs = new ArrayList<String>();
	    private int assignedQty = 0;
	    private double unitCost = 0;
	    static int pendingQty = 0;
	    static int criticalLevel = 0;
	    static String isCritical = "";
	    private String supplier = "";
	    private String manufacturer = "";
	    private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		private DatabasesDAO dao =(DatabasesDAO)ctx.getBean("dbdao");
	   
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
	        id = Integer.toString(idGen++) ;
	        itemName = item_name;
	        productCode = product_code;
	        totalQty = qty;
	        BigDecimal roundedNum = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
	        unitCost = roundedNum.doubleValue();
	        pendingQty = pendQty;
	        supplier = producer;
	        manufacturer = suppl;
	        criticalLevel = (int) (qty*0.5);
	        isCritical = "Not_Critical";
	        jobs.add("n/a");
//	        new DatabasesDAO().saveItem(this);
	    }

	    //constructor 2
	    /**
	     * method used when encapsulating an inventory item record from the database into the system for processing
	     * @param id_num id for inventory item
	     * @param item_name name of the inventory item
	     * @param product_code product code for inventory item
	     * @param cost the unit cost of item
	     * @param pending_Qty quantity of the specified item unfulfilled (or being awaited)
	     * @param total_Qty total quantity to be assigned
	     * @param assigned_jobs open job numbers associated with the use of this item
	     * @param assigned_Qty the quantity that is actively being used on open job
	     * @param critical critical level for this item
	     * @param is_critical critical status message for this item
	     * @param supplier Supplier
	     * @param manufacturer manufacturer
	     */
	    public InventoryItem(String id_num, String item_name, String product_code, double cost, 
	    int pending_Qty, int total_Qty, String assigned_jobs, int assigned_Qty, int critical, 
	    String is_critical, String supplier, String manufacturer)
	    {
	        id = id_num;
	        itemName = item_name; 
	        productCode = product_code;   
	        totalQty = total_Qty;
	        //adding job numbers to the jobs arraylist
	        String[] arr = assigned_jobs.split(",");
	        for (String str: arr){
	        	jobs.add(str);
	        }
	        assignedQty = assigned_Qty;
	        unitCost = cost;
	        pendingQty = pending_Qty;
	        this.supplier = supplier;
	        this.manufacturer = manufacturer;
	        setCriticalLevel(critical);
	        isCritical = is_critical;
	        evaluateCritical();   
	    }
	    
	    /**
	     * calculates the available quantity of this item available
	     * @return value: int
	     */
	    private int getAvailableQty(){
	    	return totalQty - assignedQty;
	    }

	    /**
	     * checks the available balance against the critical level, updates critical level status
	     * and creates critical level notification in database, if applicable
	     */
	    private void evaluateCritical()
	    {
	        if(checkCritical()){
	    		//update isCritical Status
	        	isCritical = "Critical";
	        	//add notification
	        	addNotification("owner");
	        }
	        else
	        {
	        	//update isCriticalStatus. i.e. 
	        	isCritical = "Not_Critical";
	        }
	        dao.updateItem(this, "Critical Status", isCritical);
	    }

	    /**
	     *	Adds the specified quantity to the total quantity.
	     *	@param qty - the amount of the item being received.
	     *  @param cost - the cost of the item being received.
	    **/
	    public void receiveQty(int qty, double cost)
	    {
	        totalQty += qty ;
	        unitCost = new BigDecimal(
	        		(unitCost + cost)/ 2).setScale(
	        				2, RoundingMode.HALF_UP).doubleValue();
	        evaluateCritical();
	        dao.updateItem(this, "Total Quantity", String.valueOf(totalQty));
	        dao.updateItem(this, "Critical Level", String.valueOf(unitCost));
	    }

	    /**
	     *	Assigns the specified quantity to the specified job.
	     *	@param jobRefNum - the String reference number for the job
	     *	@param qty - the amount of the item being assigned.
	    **/
	    public void assign(String jobRefNum, int qty)
	    {
	    	//when job is closed, the used assigned number for each item gets deducted from assignedQty
	    	//totalQty can never be less than assignedQty
	        this.jobs.add(jobRefNum);
	        this.assignedQty += qty;
	        evaluateCritical();
	        dao.updateItem(this, "Jobs", getJobs());
	        dao.updateItem(this, "Assigned Quantity", String.valueOf(assignedQty));
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
	    private void setCriticalLevel(int critLevel)
	    {
	        criticalLevel = critLevel;
	    }
	    
	    private boolean checkCritical(){
	    	if (getCriticalLevel() >= getAvailableQty()){
	        	return true;
	        }
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
	    
	    /**
	     * gets the string of jobs
	     * @return
	     */
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
		/**
		 * adds inventory level notification to database if none exists
		 */
		public boolean addNotification(String username) {
			// TODO Auto-generated method stub
			if(dao.saveNotification(new Notification
					("owner", this.getItemName(), this.getTotalQty())) == 1){
				return true;
			}
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
			List<Notification> list = dao.allNotifications();
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

