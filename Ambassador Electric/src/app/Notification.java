package app;

/**
 * @author Marlon Lewis
 * @version 1.0
 */

public class Notification {

	private String type;
	//type would either be inventory_level or job alert
	private String user;
	private String id;
	private String notificationInfo;
	private static int num;
	private String job = "";
	private String inv = "";
	private int inv_num;
	
	public Notification(String username, String type, String job_number) {
		this.type = type;
		user = username;
		id = "AMB-J"+Notification.num++;
		this.job = job_number;
	}
	
	public Notification(String username, String inventory_item, int count) {
		this.type = "inventory_level";
		user = username;
		id = "AMB-I"+Notification.num++;
		this.inv = inventory_item;
		inv_num = count;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getType(){
		return type;
	}
	
	public String getId(){
		return id;
	}
	
	public void jobNotify(){
		this.notificationInfo = String.format("New Job (Job #: %s) assigned", this.job);
	}
	
	public void inventoryNotify(){
		this.notificationInfo = String.format("%s levels are low (%s)", inv,inv_num);
	}
	
	public String alert(){
		if (this.type=="inventory_level"){
			inventoryNotify();
		}
		else {
			jobNotify();
		}
		return this.notificationInfo;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Notification xxx = new Notification("marlon", "laptop", 6);
		System.out.println(xxx.getUser());
		System.out.println(xxx.getId());
		System.out.println(xxx.getType());
		System.out.println(xxx.alert());
		
		Notification yyy = new Notification("lewis", "phone", 2);
		System.out.println(yyy.getUser());
		System.out.println(yyy.getId());
		System.out.println(yyy.getType());
		System.out.println(yyy.alert());
		
		Notification aaa = new Notification("mlewis", "job", "JB101");
		System.out.println(aaa.getUser());
		System.out.println(aaa.getId());
		System.out.println(aaa.getType());
		System.out.println(aaa.alert());	
		
		Notification bbb = new Notification("user", "job", "JB102");
		System.out.println(bbb.getUser());
		System.out.println(bbb.getId());
		System.out.println(bbb.getType());
		System.out.println(bbb.alert());	

	}

}
