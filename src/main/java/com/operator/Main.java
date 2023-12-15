package com.operator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
@WebServlet("/Main")
public class Main extends HttpServlet {
	static HashMap<String,ArrayList<String>> test=new HashMap<>();
	 static ArrayList<String> values=new ArrayList<>();
	 Connection conn;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jdbcUrl = "jdbc:mysql://localhost:3306/db1";
			String dbUser = "root";
			String dbPassword = "Pooja@240494";
			Class.forName("com.mysql.cj.jdbc.Driver");
			 conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
			getServletContext().setAttribute("dbConnection", conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		createOperatorCircleHashmap(request,response);
		
	}
	public void createOperatorCircleHashmap(HttpServletRequest request, HttpServletResponse response) {
		try {
			test.clear();
			 //conn=(Connection) getServletContext().getAttribute("dbConnection");
		String query="Select operator, circle from operator_table";
		Statement stmt=conn.createStatement();
		ResultSet rs= stmt.executeQuery(query);
		while(rs.next()) {
			String operator=rs.getString(1);
			String circle=rs.getString(2);
			values = (ArrayList<String>) test.getOrDefault(operator, new ArrayList<>());		
			values.add(circle);
			test.put(operator, values);
		}
		values = (ArrayList<String>) test.getOrDefault("Other", new ArrayList<>());
		String circle="Other";
		values.add(circle);
		test.put("Other", values);
		request.getSession().setAttribute("hashmap", test);
		response.sendRedirect("FirstForm1.jsp");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
