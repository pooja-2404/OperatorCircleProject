package com.operator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String filename = "";
		int id = Integer.parseInt(request.getParameter("Id"));
		String status = request.getParameter("status");
		if (status.equals("Delivered")) {
			filename = "Delresult_" + id + ".zip";

		} else if (status.equals("Failed")) {
			filename = "Failresult_" + id + ".zip";
		}
		response.setContentType("application/zip");
		response.setHeader("Content-disposition", "attachment; filename=" + filename);

		File file = new File(
				"C:\\Users\\dell\\eclipse-workspace\\OperatorCircleProject\\src\\main\\webapp\\files\\" + filename);

		InputStream inn = new FileInputStream(file);
		try {
			byte bytearray[] = new byte[1024];
			while ((inn.read(bytearray)) != -1) {
				response.getOutputStream().write(bytearray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}
	}
}