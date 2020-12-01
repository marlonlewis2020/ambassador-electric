package app;

import java.util.ArrayList;

public class ManageNotifications {
	private DatabaseDAO data = new DatabaseDAO();
	private ArrayList<Notification> alerts = new ArrayList<Notification>();

	public ManageNotifications() {
		// TODO Auto-generated constructor stub
		ArrayList<InventoryItem> inv_items = data.getInventory();
		for (InventoryItem item: inv_items){
			//pass;
		}
		ArrayList<Job> jobs = data.getJobs();
		alerts.addAll(inv_items);
		alerts.addAll(jobs);
	}
	
	public void updateNotifications(){
		//check for changes, adds notifs, removes notifs and saves notifs to database
		
	}
	
	private ArrayList scan(){
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
