package database;
/*
 * @author Marlon Lewis
 * @version 1.0
 */

import org.springframework.jdbc.core.JdbcTemplate; 
import java.util.List;

import app.InventoryItem;
import app.Notification;

public class DatabasesDAO{
	private JdbcTemplate jdbcTemplate;  
	  
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
	    this.jdbcTemplate = jdbcTemplate;  
	}
	
	//Inventory Items Database
	//
	
	public int saveItem(InventoryItem item){
		String query="insert into Inventory values(DEFAULT,'"+item.getProductCode()+"','"
	+item.getItemName()+"','"+item.getTotalQty()+"','"+item.getJobs()
	+"','"+item.getAssignedQty()+"','"+item.getpendingQty()+"','"+item.getUnitCost()
	+"','"+item.getTotalCost()+"','"+item.getCriticalLevel()+"','"+item.isCritical()
	+"','"+item.getSupplier()+"','"+item.getManufacturer()+"')";
		return jdbcTemplate.update(query);
	}
	
	public int updateItem(InventoryItem item, String column, String value){
		
		String prod_code = item.getProductCode();
		try{
			String query="UPDATE `Inventory` SET `"+column+"`='"+Integer.valueOf(value)+"' WHERE `Product Code`='"+prod_code+"'";
			return jdbcTemplate.update(query);
		}
		catch(NumberFormatException n){
			String query="UPDATE `Inventory` SET `"+column+"`='"+value+"' WHERE `Product Code`='"+prod_code+"'";
			return jdbcTemplate.update(query);
		}
	}
	
	public List<InventoryItem> allInventory(String filter, String criteria){
		String sql = "select '"+filter+"' from 'Inventory' where 'Product Code'=?";
		return jdbcTemplate.query(sql, (rs, rowNum) ->
        new InventoryItem(rs.getString("id"), rs.getString("NAME"), rs.getString("PRODUCT CODE"), 
        		Double.valueOf(rs.getString("UNIT COST")), Integer.parseInt(rs.getString("PENDING QUANTITY")), 
        		Integer.parseInt(rs.getString("TOTAL QUANTITY")), rs.getString("Jobs"),
        		Integer.parseInt(rs.getString("ASSIGNED QUANTITY")), Integer.parseInt(rs.getString("CRITICAL LEVEL")), 
        		rs.getString("CRITICAL STATUS"), rs.getString("SUPPLIER"), rs.getString("MANUFACTURER")));
	}
	
	//Notifications Database
	
	public int saveNotification(Notification notification){
		String query="insert into NOTIFICATIONS values('"+notification.getType()+"','"
	+notification.getUser()+"','"+notification.getId()+"','"+notification.alert()+"','"
				+notification.getJob()+"','"+notification.getInv()+"','"+notification.getInv_level()+"')";
		return jdbcTemplate.update(query);
	}
	
	public int updateJobNotification(Notification notification){
		String query="update NOTIFICATIONS set TYPE='"+notification.getType()+"',USER='"
	+notification.getUser()+"' where id='"+notification.getId()+"' ";
		return jdbcTemplate.update(query);
	}
	
	public int deleteNotification(Notification notification){
		String query="delete from NOTIFICATIONS where id='"+notification.getId()+"' ";
		return jdbcTemplate.update(query);
	}
	
//	public ArrayList<Notification> getNotifications(String type){
//		String sql = String.format("SELECT * FROM NOTIFICATIONS WHERE TYPE = %s", type);
//				//.equalsIgnoreCase(type);
//	}

	
	public Notification findNotification(String column, String value) {
		// TODO Auto-generated method stub
	    String sql = String.format("SELECT * FROM 'NOTIFICATIONS' WHERE '%s' = ?",column);
	    return jdbcTemplate.queryForObject(sql, new Object[]{value}, (rs, rowNum) ->
        new Notification(rs.getString("TYPE"), rs.getString("USER"), rs.getInt("ID"), 
        		rs.getString("NOTIFICATION"), rs.getString("JOB"), rs.getString("ITEM"), 
        		rs.getInt("INV_LEVEL")));
	}
	
	public List<Notification> allNotifications(){
		String sql = "select * from 'Notifications'";
		return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Notification(rs.getString("TYPE"), rs.getString("USER"), rs.getInt("ID"), 
        		rs.getString("NOTIFICATION"), rs.getString("JOB"), rs.getString("ITEM"), 
        		rs.getInt("INV_LEVEL")));
	}

}
