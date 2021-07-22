package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class dummyServlet
 */
@WebServlet("/dummyServlet")
public class DummyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		static final String DB_URL = "jdbc: mysql://localhost:3306/mysql";
		// Database credentials
		static final String USER = "root";
		static final String PASS = "admin";
    public DummyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Connection conn = null;
		Statement stmt = null;
		List<Response>demoList=new ArrayList<>();
		try{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		//STEP 3: Open a connection
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=admin");
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT name_customer,cust_number,invoice_id,global_amount,due_in_date,Predicted_date,notes FROM mytable";
		ResultSet rs = stmt.executeQuery(sql);
		
		//STEP 5: Extract data from result set
		while(rs.next()){
		//Retrieve by column name
			Response demo=new Response();
		demo.setCustomer_name(rs.getString("name_customer"));
		demo.setCustomer_number(rs.getString("cust_number"));
		demo.setInvoice_id(rs.getInt("invoice_id"));
		demo.setInvoice_amount(rs.getInt("global_amount"));
		demo.setDue_in_date(rs.getString("due_in_date"));
		demo.setPredicted_Date(rs.getString("Predicted_date"));
		demo.setNotes(rs.getString("notes"));
		demoList.add(demo);
		}
		String jsonString=getJSONStringFromObject(demoList);
		response.setContentType("application/json");
		
		 Gson gson = new Gson();
		 String data = gson.toJson(demoList);
		 PrintWriter out = response.getWriter();
		 response.setContentType("application/json");
		 response.setHeader("Access-Control-Allow-Origin","*");
		 response.setCharacterEncoding("UTF-8");
		 out.print(data);
		 out.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	

	private String getJSONStringFromObject(List<Response> demoList) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}