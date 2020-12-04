package app;

/**
 * Notification object created from this class
 * @author Marlon Lewis
 * @version 1.0
 */

public class Notification {

	private String type;
	//type would either be inventory_level or job alert
	private String user;
	private int id = 0;
	private String notificationInfo = "";
	private String job;
	private String inv;
	private int inv_level = 0;
	
	//Constructor 1
	/**
	 * CReates a notification object, used specifically when retrieving database notification to be presented to user/system
	 * @param type
	 * @param user
	 * @param id
	 * @param message
	 * @param job
	 * @param item
	 * @param item_level
	 */
	public Notification(String type, String user, int id, String message, String job, String item, int item_level){
		this.type = type;
		this.user = user;
		this.id = id;
		notificationInfo = message;
		this.job = job;
		inv = item;
		inv_level = item_level;
	}
	
	//Constructor 2
	/**
	 * Creates a job notification object
	 * @param username employee's username from the database
	 * @param job_number identification of specific job
	 */
	public Notification(String username, String job_number) {
		this.type = "job";
		user = username;
		//id = "AMB-J"+Notification.num++;
		this.job = job_number;
		this.inv = "?";
		this.inv_level = 0;
	}
	
	//Constructor 3
	/**
	 * Creates an inventory notification object when item is within critical
	 * @param username employee's username from the database
	 * @param inventory_item name of inventory item
	 * @param count current level of the inventory item specified
	 */
	public Notification(String username, String inventory_item, int count) {
		this.type = "inventory_level";
		user = username;
		//id = "AMB-I"+Notification.num++;
		this.inv = inventory_item;
		inv_level = count;
		this.job = "?";
	}
	
	/**
	 * Used to get the user attribute from the notification
	 * @return username: String
	 */
	public String getUser(){
		return user;
	}
	
	/**
	 * Used to get the type attribute from the notification
	 * @return type: String
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Used to get the id attribute for the notification
	 * @return id: String
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Used to get the job number attribute from the notification
	 * @return job: String
	 */
	public String getJob() {
		return job;
	}

	/**
	 * Used to get the inventory item attribute from the notification
	 * @return inv: String
	 */
	public String getInv() {
		return inv;
	}

	/**
	 * Used to get the count for the specific inventory item from the notification
	 * @return
	 */
	public int getInv_level() {
		return inv_level;
	}
	
	/**
	 * Used to create a String message for a job notification (alert)
	 */
	private void jobNotify(){
		this.notificationInfo = String.format("New Job (Job #: %s) assigned", this.job);
	}
	
	/**
	 * Used to create a String message for an inventory notification (alert)
	 */
	private void inventoryNotify(){
		this.notificationInfo = String.format("%s levels are low (%s)", inv,inv_level);
	}
	
	/**
	 * Used to print out the alert message for the notification object
	 * @return notificationInfo: String
	 */
	public String alert(){
		if (this.type=="inventory_level"){
			inventoryNotify();
		}
		else {
			jobNotify();
		}
		return this.notificationInfo;
	}

}
