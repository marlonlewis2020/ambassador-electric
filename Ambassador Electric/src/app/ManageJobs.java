import java.util.ArrayList;
import java.sql.*;
import java.util.Scanner;

public class ManageJobs {
	public ManageJobs() {
		// TODO Auto-generated constructor stub
		ArrayList<Job> newjob = data.getJob();
		for (Job item: newjob){
			//pass;
		}
		ArrayList<Job> jobs = data.getJobs();
		jobs.addAll(newjob);
		jobs.addAll(newjob);
		
		
		System.out.println("ArrayList contains : " + jobs);

	    //Calling remove(object)
	    jobs.remove(" ");	
	}

	
	private User user;
	 ManageJobs(User user){
		 this.user = user;
	 }
	 
	 public User getUser() {
	        return user;
	    }
	    public void setUser(User user) {
	        this.user = user;
	    }
	
	
	public ResultSet fetchRecords(Connection conn) throws SQLException, ClassNotFoundException
	{
		String query = "SELECT * FROM job";
		Statement st =conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	

	public int addRecords(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "Add INTO job ("+mng.getJob_Duration()+",'"+
	mng.getJob_Duration()+"','"+mng.getTeam_Leader()+"',"+mng.getJob_Description()+")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
    }
	
	public int useRecords(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "Use INTO job ("+mng.getJob_Duration()+",'"+
	mng.getJob_Duration()+"','"+mng.getTeam_Leader()+"',"+mng.getJob_Description()+")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
    }
	
	
	public int returnRecords(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "Add INTO job ("+mng.getJob_Duration()+",'"+
	mng.getJob_Duration()+"','"+mng.getTeam_Leader()+"',"+mng.getJob_Description()+")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
    }
		
	public int viewRecord(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "DELETE FROM ManageJobs WHERE job ('" + mng.getId() +")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
	}
	
	public int deleteRecord(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "DELETE FROM ManageJobs WHERE job ('" + mng.getId() +")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
	}
	
	public int completeRecord(Connection conn, Job mng) throws ClassNotFoundException, SQLException
	{
		String query = "DELETE FROM ManageJobs WHERE job ('" + mng.getId() +")";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);
		return rs;
	}
	
	
	public int viewRecord(Connection conn, ManageJobs maj) throws ClassNotFoundException, SQLException
	{
	    String query = "COMPLETE employee SET jobs ('"+maj.getnewJobs()+"','"+maj.getUser()+")";
	    Statement st = conn.createStatement();
	    int rs = st.executeUpdate(query);
	    return rs;
	}
	
	public int deleteRecord(Connection conn, ManageJobs maj) throws ClassNotFoundException, SQLException
	{
	    String query = "COMPLETE employee SET jobs ('"+maj.getnewJobs()+"','"+maj.getUser()+")";
	    Statement st = conn.createStatement();
	    int rs = st.executeUpdate(query);
	    return rs;
	}
	
	public int completeRecord(Connection conn, ManageJobs maj) throws ClassNotFoundException, SQLException
	{
	    String query = "COMPLETE employee SET jobs ('"+maj.getnewJobs()+"','"+maj.getUser()+")";
	    Statement st = conn.createStatement();
	    int rs = st.executeUpdate(query);
	    return rs;
	}


	private String getnewJobs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
