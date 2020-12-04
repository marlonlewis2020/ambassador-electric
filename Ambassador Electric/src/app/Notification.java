package app;

import database.DatabasesDAO;
import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext; 

/**
 * @author Marlon Lewis
 * @version 1.0
 */

public class Notification {

	private String type;
	//type would either be inventory_level or job alert
	private String user;
	private String id;
	private String notificationInfo = "";
	private static int num;
	private String job;
	private String inv;
	private int inv_level = 0;
	
	public Notification(String type, String user, String id, String message, String job, String item, int item_level){
		this.type = type;
		this.user = user;
		this.id = id;
		notificationInfo = message;
		this.job = job;
		inv = item;
		inv_level = item_level;
	}
	
	public Notification(String username, String type, String job_number) {
		this.type = type;
		user = username;
		id = "AMB-J"+Notification.num++;
		this.job = job_number;
		this.inv = "?";
		this.inv_level = 0;
	}
	
	public Notification(String username, String inventory_item, int count) {
		this.type = "inventory_level";
		user = username;
		id = "AMB-I"+Notification.num++;
		this.inv = inventory_item;
		inv_level = count;
		this.job = "?";
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
	
	public String getJob() {
		return job;
	}

	public String getInv() {
		return inv;
	}

	public int getInv_level() {
		return inv_level;
	}
	
	private void jobNotify(){
		this.notificationInfo = String.format("New Job (Job #: %s) assigned", this.job);
	}
	
	private void inventoryNotify(){
		this.notificationInfo = String.format("%s levels are low (%s)", inv,inv_level);
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
		try{
			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			
			DatabasesDAO dao =(DatabasesDAO)ctx.getBean("dbdao"); 
			
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
			int status = dao.saveNotification(xxx);
			
			System.out.println(status);
		}
		catch(Exception e){
			e.printStackTrace();
			for (String s: e.getMessage().split(":")){
				System.out.println(s+":");
			}
			
		}
		

	}

}
