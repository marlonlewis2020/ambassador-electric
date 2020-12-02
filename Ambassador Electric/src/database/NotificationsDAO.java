package database;
/*
 * @author Marlon Lewis
 * @version 1.0
 */
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.jdbc.core.RowMapper;

import app.Notification;

public class NotificationsDAO {
	private JdbcTemplate jdbcTemplate;  
	  
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
	    this.jdbcTemplate = jdbcTemplate;  
	}
	
	public int saveNotification(Notification notification){
		String query="insert into NOTIFICATIONS values('"+notification.getType()+"','"+notification.getUser()+"','"+notification.getId()+"','"+notification.alert()+"','"+notification.getJob()+"','"+notification.getInv()+"','"+notification.getInv_level()+"')";
		return jdbcTemplate.update(query);
	}
	
	public int updateJobNotification(Notification notification){
		String query="update NOTIFICATIONS set TYPE='"+notification.getType()+"',USER='"+notification.getUser()+"' where id='"+notification.getId()+"' ";
		return jdbcTemplate.update(query);
	}
	
	public int deleteNotification(Notification notification){
		String query="delete from NOTIFICATIONS where id='"+notification.getId()+"' ";
		return jdbcTemplate.update(query);
	}
	  

}
