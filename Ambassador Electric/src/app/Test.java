package app;
/**
 * Used to unit test and system test
 * @author Marlon Lewis
 * @version 1.0
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import database.DatabasesDAO;

public class Test {
	
	/**
	 * unit test for the Notification class, accessing database
	 * @param d DatabasesDAO object 
	 */
	static void runNotificationTest(DatabasesDAO d){
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
		
		Notification aaa = new Notification("mlewis", "JB101");
		System.out.println(aaa.getUser());
		System.out.println(aaa.getId());
		System.out.println(aaa.getType());
		System.out.println(aaa.alert());	
		
		Notification bbb = new Notification("user", "JB102");
		System.out.println(bbb.getUser());
		System.out.println(bbb.getId());
		System.out.println(bbb.getType());
		System.out.println(bbb.alert());	
		int status = d.saveNotification(xxx);
		
		System.out.println(status);
	}
	
	/**
	 * Used to perform unit testing on the InventoryItem class
	 * @param d DatabasesDAO object
	 */
	static void runInvItemTest(DatabasesDAO d){
		//creating and adding item to database
		InventoryItem item1 = new InventoryItem("panadolMulti-Symptom", "pan103", 100, 50.50, 
	    		25, "York Medical", "Panadol");
		System.out.println(item1.getItemName());
		d.saveItem(item1);
		item1.assign("3", 4);
		InventoryItem item2 = new InventoryItem("antibiotics", "anti101", 145, 204.50, 
	    		20, "York Medical", "Immune Inc.");
		d.saveItem(item2);
		item2.assign("4", 100);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DatabasesDAO dao =(DatabasesDAO)ctx.getBean("dbdao"); 
		try{
			/*
			runNotificationTest(dao); //unit test for the Notification class, accessing database - successful
			*/
			runInvItemTest(dao); //unit test for InventoryItem class, accessing database - successful
		}
		catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			for (String s: e.getMessage().split(":")){
				System.out.println(s+":");
			}
		}
	}
	
}
