package supply;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SupplyServlet
 */
@WebServlet("/SupplyServlet")
public class SupplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		/*
		 * 
		 */
		PrintWriter out=response.getWriter();
		out.print("Hello");
		try{ 
			String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
			String dbName = "sql3379197";
			
			String driver = "com.mysql.jdbc.Driver";
			String userName = "sql3379197"; 
			String password = "CR82VSxwzw";  
			
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url+dbName,userName,password);
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from User");  
			while(rs.next()){  
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
					conn.close();  
			}
		}catch(Exception e){ 
			System.out.println(e);
		}  

		/*
		 * 
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
