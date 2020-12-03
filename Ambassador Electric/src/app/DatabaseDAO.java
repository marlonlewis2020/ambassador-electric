package supply;

import java.sql.*;
import java.util.*;

import Inventory.*;


public class DatabaseDAO {
	private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
	private String dbName = "sql3379197";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "sql3379197"; 
	private String password = "CR82VSxwzw";
	private PreparedStatement preparedStmt;///TAKE OUT
	private Statement stmt; ///NEED THIS???
	private Connection connection;
	private ResultSet result;
	private String query;
	private ArrayList<SupplyOrder>SupplyOrderList= new ArrayList<SupplyOrder>();
	private ArrayList<InventoryItem>InventoryList= new ArrayList<InventoryItem>();
	
	//Constructors

	public DatabaseDAO() {
		try{
			Class.forName(driver);//Class.forName(driver).newInstance();
			this.connection = DriverManager.getConnection(this.url+this.dbName, this.userName, this.password);
		}catch (Exception e){
		      System.out.println("Got an exception!");
		      System.out.println(e.getMessage());
		 }
	}


	//		User Table
	public void addUser(int id, String name, String username, String password, String employeeType) {
		try {
			query ="insert into User (userid, name, username, password, `user type`) values (?,?,?,?,?)";
			preparedStmt= this.connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, username);
			preparedStmt.setString(4, password);
			preparedStmt.setString(5, employeeType);
			int affectedrows=preparedStmt.executeUpdate(); 
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	    }
    



	public boolean verifyUser(String username, String password ){
		try {
			query="select * from User where username=? and password=?";
			preparedStmt= this.connection.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			result=preparedStmt.executeQuery();
			if (result.next()) {
					return true;
				}	
		} catch (SQLException e) {
			System.out.println(e);
	    }
		return false;
	}
	
	
	
	
	public void deleteUser(String username) {
		try {
			query="delete from User where username=?";
			preparedStmt= connection.prepareStatement(query);
			preparedStmt.setString(1,username);
			System.out.println(preparedStmt);//??????????????????????
			int affectedrows=preparedStmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e);//println??
	    }
	}


	//	Supply Order Table
	 public void addSupplyItem(SupplyOrderItem item) {
			try {
				query ="insert into `Supply Items` (Id, `Supply Order Number`, `Product Code`, Name, Quantity, `Unit Cost`, `Total Cost`, Supplier, Manufacturer , `Ordered Date`, Status) values (?,?,?,?,?,?,?,?,?,?,?)";
				preparedStmt= this.connection.prepareStatement(query);
				preparedStmt.setInt(1, item.getId());
				preparedStmt.setInt(2, item.getSupplyOrderNo());
				preparedStmt.setString(3, item.getProductCode());
				preparedStmt.setString(4, item.getName());
				preparedStmt.setInt(5, item.getQuantity());
				preparedStmt.setDouble(6, item.getUnitCost());
				preparedStmt.setDouble(7, item.getTotalCost());
				preparedStmt.setString(8, item.getSupplier());
				preparedStmt.setString(9, item.getManufacturer());
				preparedStmt.setString(10, item.getOrderedDate());
				preparedStmt.setString(11, item.getStatus());
				int affectedrows=preparedStmt.executeUpdate(); 
		        } catch (SQLException e) {
		            System.out.println(e);
		        }
		}
 
 
 
 	

		public void deleteSupplyItem(int id, String name) {
		try {
			query="delete from `Supply Items` where Id=? and Name=?";
			preparedStmt= connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.setString(2,name);
			System.out.println(preparedStmt);//??????????????????????
			int affectedrows=preparedStmt.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e);//println??
	    }
	}
		public void UpdateSupplyItem(int id, String column, String newValue) {
			try {
				query="update SupplyItems`"+ column+ "` set= ? where Id=?";
				preparedStmt.setString(1, column);
				if (column=="Quantity" || column=="Supply Order Number") {
					preparedStmt.setInt(2,Integer.parseInt(newValue));
				}else if (column=="Unit Cost"  ||  column=="Total Cost") {
					preparedStmt.setDouble(2,Double.parseDouble(newValue) );
				}else {
					preparedStmt.setString(2, newValue);
				}
				preparedStmt.setInt(3, id); 
				int affectedrows=preparedStmt.executeUpdate();
			}catch (SQLException e) {
				System.out.println(e);
		    }
		}
	
		public ArrayList<SupplyOrderItem> RetrieveItems(){
			try {
				query="select * from `Supply Items`";
				stmt=connection.createStatement();  
				result=stmt.executeQuery(query);  
				while(result.next()){  
					//Need to retrieve assigned job data;NEEEEEED
					int id = result.getInt(1);
					int orderNo=result.getInt(2);
					String name=result.getString(4);
					String code=result.getString(3);
					double unitCost=result.getDouble(6);
					double totalCost=result.getDouble(7);
					int qty=result.getInt(5);
				    String supplier= result.getString(8);
				    String manufacturer= result.getString(9);
				    String date =result.getString(10);
				    String status=result.getString(11);
				    
					SupplyOrderItem item= new SupplyOrderItem (id, name,orderNo,code, manufacturer, supplier, qty, unitCost,totalCost, date, status);
					InventoryList.add(item);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		//SUPPLY ORDER TABLE 
		
		
		  public void addSupplyOrder(SupplyOrder order) {
		try {
			query ="insert into `Supply Order` (Id, `Supply Order Items`, `Ordered Date`, Status, Supplier, Updates) values (?,?,?,?,?,?)";
			preparedStmt= this.connection.prepareStatement(query);
			preparedStmt.setInt(1, order.getId());
			preparedStmt.setString(2, order.getItemIdList());
			preparedStmt.setString(3, order.getdate());
			preparedStmt.setString(4, order.getStatus());
			preparedStmt.setString(5, order.getSupplier());
			preparedStmt.setString(6, order.getUpdates());
			int affectedrows=preparedStmt.executeUpdate(); 
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
	}
	
		public void updateSupplyOrder(String task, SupplyOrder order) {
			try {
				if (task.equals("itemListUpdate")){
					query="update `Supply Order` set `Supply Order Items`= ?, `Updates=? where Id=?";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setString(1, order.getItemIdList());
					preparedStmt.setString(2, order.getUpdates());
					preparedStmt.setInt(3, order.getId());
				}
				
				else if(task.equals("status")){
					query="update `Supply Order` set `Status`= ?, `Updates=? where Id=?";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setString(1, order.getStatus());
					preparedStmt.setString(2, order.getUpdates());
					preparedStmt.setInt(3, order.getId());
					
				}
				int affectedrows=preparedStmt.executeUpdate();
				
			}catch (SQLException e) {
				System.out.println(e);
		    }
		}
		
		
		 public ArrayList<SupplyOrder>  retrieveOrderList(String criteria, ArrayList<SupplyOrderItem> ItemList) {
			 ArrayList<SupplyOrder> ListofOrders=new ArrayList<SupplyOrder>();
				try {
					if (criteria.equals("All")) {
						query="select * from `Supply Order`";
					}
					else if(criteria.equals("pending")) {
						query="select * from `Supply Order` where Status= 'pending'";
					}else if(criteria.equals("fulfilled")) {
						query="select * from `Supply Order` where Status= 'fulfilled'";
					}
					stmt=connection.createStatement();  
					result=stmt.executeQuery(query); 
					
					ArrayList<SupplyOrderItem> list=new ArrayList<SupplyOrderItem>();
					while(result.next()){  
						int id = result.getInt(1);
						String[] ItemsNo =(result.getString(2)).split(",");
				        for(String num: ItemsNo) {
				        	 for(SupplyOrderItem item: ItemList) {
				        		 if(item.getId()==Integer.parseInt(num)) {
				        			 list.add(item);
				        		 }
				        	 }
				        }
				        String date=result.getString(3);
						String supplier= result.getString(4);
						String status=result.getString(5);
						String updates=result.getString(6);
						SupplyOrder order= new SupplyOrder(id, list ,date,supplier,status,updates);
						ListofOrders.add(order);
				        
				      }
					
						
				} catch (SQLException e) {
					System.out.println(e);
				}
			return ListofOrders;
						
			}
		
			
	
	
	//////////Inventory Management //////////
	public void addInventoryItem(InventoryItem item) {
		try {
			query ="insert into Inventory (Id, `Product Code`, Name, `Total Quantity`, `Assigned Quantity`, `Pending Quantity`, `Unit Cost`, `Total Cost`, `Critical Level`, `Critical Status`, Supplier, Manufacturer) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStmt= this.connection.prepareStatement(query);
			preparedStmt.setString(1, item.getId());
			preparedStmt.setString(2, item.getProductCode());
			preparedStmt.setString(3, item.getItemName());
			preparedStmt.setInt(4, item.getTotalQty());
			preparedStmt.setInt(5, item.getpendingQty());
			preparedStmt.setInt(6, item.getAssignedQty());
			preparedStmt.setDouble(7, item.getUnitCost());//Need to encode
			preparedStmt.setDouble(8, item.getTotalCost());
			preparedStmt.setInt(9, item.getCriticalLevel());
			preparedStmt.setString(10, item.isCritical());
			preparedStmt.setString(11, item.getSupplier());
			preparedStmt.setString(12, item.getManufacturer());
			int affectedrows=preparedStmt.executeUpdate(); ///DO I need to save it???
	        } catch (SQLException e) {
	            System.out.println(e);
	        }
		
	}
	//Increase inventory - average cost
	//Supply Updates Table - Action(descrepancies,defective, use) , Qunatity , Reason, User, Date 
	//filter 
	
	
	
	
	//task - increase inventory, decrease, use, defective, assign , confirm/add (increase total, change cost decrease pending) possibly change status, 
		public void UpdateInventoryQty( String task, InventoryItem item) {
			try {
				if (task.equals("confirmArrival")){
					query="update Inventory set `Total Quantity`= ?, `Pending Quantity=?, `Unit Cost` =?, `Total Cost`=? where id=?";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setInt(1, item.getTotalQty());
					preparedStmt.setInt(2, item.getpendingQty());
					preparedStmt.setDouble(3, item.getUnitCost());//Need to encode
					preparedStmt.setDouble(4, item.getTotalCost());
					preparedStmt.setString(5, item.getId());
				}
				
				else if(task.equals("assign/deassign")){
					query="update Inventory set `Total Quantity`= ?, `Assigned Quantity`=? where id=?";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setInt(1, item.getTotalQty());
					preparedStmt.setInt(2, item.getAssignedQty());
					preparedStmt.setString(3, item.getId());
					
				}
				else {
					query="update Inventory set `Total Quantity`= ?, where id=?";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setInt(1, item.getTotalQty());
					preparedStmt.setString(2, item.getId());
				}
				int affectedrows=preparedStmt.executeUpdate();
				
			}catch (SQLException e) {
				System.out.println(e);
		    }
		}
		
		
		
		//Updates
		//change="+2" or"-2")
		//task-defective, decrepancies ,use, assign, dessign
		//reason 2 job for use, 
		//confirmed delivery 
		//SHOULD THIS BE A CLASS
		public void recordInventoryUpdates(int id, String productCode, String task, String change, String reason, String user, String date) {
			try {
					query ="insert into Updates (Id, Product Code, Action, Change, Reason, User, Date) values (?,?,?,?,?,?,?,?)";
					preparedStmt= this.connection.prepareStatement(query);
					preparedStmt.setInt(1, id);
					preparedStmt.setString(2, productCode);
					preparedStmt.setString(3, task);
					preparedStmt.setString(4, change);
					preparedStmt.setString(5, reason);
					preparedStmt.setString(6, user);//Need to encode
					preparedStmt.setString(7, date);
					int affectedrows=preparedStmt.executeUpdate(); ///DO I need to save it???
			        } catch (SQLException e) {
			            System.out.println(e);
			}
		}		
		//Retrieve whole inventory
	 public ArrayList<InventoryItem>  getInventoryList() {
			try {
				query="select * from Inventory";
				stmt=connection.createStatement();  
				result=stmt.executeQuery(query);  
				while(result.next()){  
					//Need to retrieve assigned job data;NEEEEEED
					String id = result.getString(1);
					String name=result.getString(3);
					String code=result.getString(2);
					double unitCost=result.getDouble(7);
					int pending_Qty=result.getInt(6); 
					int total_Qty=result.getInt(4);
					String assigned_Data="001=40";//Need to fix FIXXXXXX
					int assigned_Qty=result.getInt(5);
					int criticalLevel=result.getInt(9);
				    String is_critical=result.getString(10);
				    String supplier= result.getString(11);
				    String manufacturer= result.getString(12);
					InventoryItem item= new InventoryItem(id , name ,code, unitCost,pending_Qty, total_Qty ,assigned_Data, assigned_Qty , criticalLevel, is_critical, manufacturer, supplier );
					InventoryList.add(item);
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		return InventoryList;
			
			
		}
		
		//NEED A CLOSE WHEN LOGIN 
	}
	
