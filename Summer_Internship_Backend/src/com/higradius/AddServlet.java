package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customer=request.getParameter("Customer");
		String name=request.getParameter("Name");
		int invoice=Integer.parseInt(request.getParameter("Invoice"));
		int amount=Integer.parseInt(request.getParameter("Amount"));
		String due=request.getParameter("due");
		String notes=request.getParameter("notes");
		java.util.Date date = Date.valueOf(due);
		java.sql.Date due_date = (Date) date;

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//STEP 3: Open a connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=admin");
			//STEP 4: Execute a query
			String sql;
			sql = "insert into mytable(cust_number,name_customer,due_in_date,invoice_id,global_amount,notes) values(?,?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,customer);
			st.setString(2,name);
			st.setDate(3, due_date);
			st.setInt(4, invoice);
			st.setInt(5, amount);
			st.setString(6, notes);
			int rs = st.executeUpdate();
			
			 response.setContentType("text/html");

			 PrintWriter out = response.getWriter();
			
			 response.setHeader("Access-Control-Allow-Origin","*");
			 response.setCharacterEncoding("UTF-8");
			 out.print(rs);
			 out.flush();
		
		
		}catch(Exception e) {
				e.printStackTrace();
			}
	}
		
		
		
	}