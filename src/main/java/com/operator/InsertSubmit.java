package com.operator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/InsertSubmit")
public class InsertSubmit extends HttpServlet {
	LocalDate localDatestart, localDateend;
	Connection conn;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operator = request.getParameter("operator");
		String circle = request.getParameter("circle");
		String status = request.getParameter("status");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		try {
			conn = (Connection) getServletContext().getAttribute("dbConnection");
			String sql_query = "INSERT INTO processedMaster(Operator, Circle, StartDate, EndDate,Status, ProcessStatus) "
					+ "VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql_query);
			preparedStatement.setString(1, operator);
			preparedStatement.setString(2, circle);
			preparedStatement.setString(3, startdate);
			preparedStatement.setString(4, enddate);
			preparedStatement.setString(5, status);
			preparedStatement.setString(6, "Pending");
			preparedStatement.executeUpdate();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}