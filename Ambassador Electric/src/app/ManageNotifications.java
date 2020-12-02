package app;

/*
 * @author Marlon Lewis
 * @version 1.0
 */

import java.util.ArrayList;

public interface ManageNotifications {
	static ArrayList<Notification> alerts = new ArrayList<Notification>();

	abstract boolean addNotification();
	/*
	 * adds notification to database
	 */
	
	abstract boolean deleteNotification(Notification alert);
	/*
	 * deletes selected notification from database
	 */
	
	abstract boolean findNotification(Notification notification);
	/*
	 * checks is a notification exists in database, matching the parameter value
	 */
	
	abstract boolean assignNotification();
	/*
	 * after creating a notification, this method assigns is to the correct user
	 */
	
	abstract boolean updateNotifications();
	/*
	 * saves a copy of all notifications offline
	 */
	
	
//		private DatabaseDAO data = new DatabaseDAO();
	
//		ArrayList<InventoryItem> inv_items = data.getInventory();
//		for (InventoryItem item: inv_items){
//			if (item.getActual() <= item.getCritical()){
//				
//				Notification notif = new Notification("owner", item.getItemName(), item.getActual());
//				alerts.add(notif);
//			}
//		}
	
//		ArrayList<Job> jobs = data.getJobs();
//		for (Job job: jobs){
//			if (job.getStatus.equals("Complete")==false){
//				Notification notif = new Notification(job.getUser(), "job", job.getJobId());
//				alerts.add(notif);
//			}
//		}
	
	
	abstract ArrayList<Notification> pullNotifications(String type);
	/*
	 * returns an ArrayList of all notification objects in the database
	 */
	

}
