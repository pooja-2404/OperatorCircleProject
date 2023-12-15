package com.operator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
@WebServlet("/ProcessMaster")
public class ProcessMaster extends HttpServlet{
	LocalDate localDatestart, localDateend;
	Connection conn;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			 conn=(Connection) getServletContext().getAttribute("dbConnection");
			String sql = "SELECT Id, Operator, Circle, StartDate, EndDate, Status, ProcessStatus from processedMaster";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<Data> data = new ArrayList<>();

			while (rs.next()) {
				Date startDate=rs.getDate(4);
				Date endDate=rs.getDate(5);
				String Status=rs.getString(6);
				String processStatus=rs.getString(7);
				String Operator=rs.getString(2);
				String Circle=rs.getString(3);
				int Id=rs.getInt(1);		

				data.add(new Data(startDate, endDate,Id, Operator, Circle,  Status, processStatus));
			}
			response.setContentType("application/json");
			response.getWriter().write("{\"dataList\":" + new Gson().toJson(data)+"}");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}
