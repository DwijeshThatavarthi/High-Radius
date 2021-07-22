package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
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
		// TODO Auto-generated method stub
		Connection conn=null;
		int invoice_id = Integer.parseInt(request.getParameter("Invoice"));
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//STEP 3: Open a connection
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?user=root&password=admin");
			//STEP 4: Execute a query
			String sql;
			sql = "delete from mytable where invoice_id=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,invoice_id);
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