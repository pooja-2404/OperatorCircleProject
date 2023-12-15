package com.operator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/ResultSetToExcel")
public class ResultSetToExcel extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
			String sql = "SELECT * FROM processedMaster";
			String excelFilePathDel = " ", excelFilePathFail = " ";

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Workbook workbook = new XSSFWorkbook();

				int id = resultSet.getInt(1);
				String operator = resultSet.getString(2);
				String circle = resultSet.getString(3);
				Date start = resultSet.getDate(4);
				Date end = resultSet.getDate(5);
				String status = resultSet.getString(6);
				String processstatus = resultSet.getString(7);
				if (processstatus.equals("Pending")) {
					if (status.equals("Delivered")) {
						String filename = "Delresult_" + id + ".xlsx";
						Sheet sheet = workbook.createSheet(filename);
						excelFilePathDel = "C:\\Users\\dell\\eclipse-workspace\\OperatorCircleProject\\src\\main\\webapp\\files\\"
								+ filename;

						String sql_Del = "Select Mobile from delivered_operator where operator='" + operator
								+ "' and circle='" + circle + "' and Delivered_date between '" + start + "' and '" + end
								+ "' ";

						Statement statement2 = conn.createStatement();
						ResultSet rs = statement2.executeQuery(sql_Del);
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						Row heading = sheet.createRow(0);
						heading.createCell(0).setCellValue(
								"(File whose Id:" + id + ", Operator:" + operator + ", Circle:" + circle + ")");
						Row headerRow = sheet.createRow(1);
						for (int i = 1; i <= columnCount; i++) {
							headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
						}
						int rowNum = 2;
						while (rs.next()) {
							Row row = sheet.createRow(rowNum++);
							for (int i = 1; i <= columnCount; i++) {
								row.createCell(i - 1).setCellValue(rs.getString(i));
							}
						}
						try {
							// this code is for creating excel files
//							FileOutputStream outputStream = new FileOutputStream(excelFilePathDel);
							// workbook.write(outputStream);
							System.out.println("Data exported to " + excelFilePathDel);
							String sqlupdate = "UPDATE processedMaster SET ProcessStatus='Done' where id=" + id;
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(sqlupdate);

						} catch (Exception e) {
							e.printStackTrace();
						}
						String zipFilepath = "C:\\Users\\dell\\eclipse-workspace\\OperatorCircleProject\\src\\main\\webapp\\files\\Delresult_"
								+ id + ".zip";
						FileOutputStream fos = new FileOutputStream(zipFilepath);
						ZipOutputStream zipOutputStream = new ZipOutputStream(fos);
						zipOutputStream.putNextEntry(new ZipEntry(filename));
						workbook.write(zipOutputStream);
						zipOutputStream.closeEntry();
						zipOutputStream.close();
						response.setContentType("text/plain");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(zipFilepath);

					} else {

						String filename = "Failresult_" + id + ".xlsx";
						Sheet sheet1 = workbook.createSheet(filename);
						excelFilePathFail = "C:\\Users\\dell\\eclipse-workspace\\OperatorCircleProject\\src\\main\\webapp\\files\\"
								+ filename;

						String sql_Fail = "Select Mobile from failed_operator where operator='" + operator
								+ "' and circle='" + circle + "' and Delivered_date between '" + start + "' and '" + end
								+ "' ";

						Statement statement2 = conn.createStatement();
						ResultSet rs = statement2.executeQuery(sql_Fail);
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						Row heading = sheet1.createRow(0);
						heading.createCell(0).setCellValue(
								"(File whose Id:" + id + ", Operator:" + operator + ", Circle:" + circle + ")");
						

						// Create the header row
						Row headerRow = sheet1.createRow(1);
						for (int i = 1; i <= columnCount; i++) {
							headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
						}
						int rowNum = 2;
						while (rs.next()) {
							Row row = sheet1.createRow(rowNum++);
							for (int i = 1; i <= columnCount; i++) {
								row.createCell(i - 1).setCellValue(rs.getString(i));
							}
						}
						try {
							// this is for creating excel files
//							FileOutputStream outputStream = new FileOutputStream(excelFilePathFail);
//							workbook.write(outputStream);
							System.out.println("Data exported to " + excelFilePathFail);
							String sqlupdate = "UPDATE  processedMaster SET ProcessStatus='Done' where id=" + id;
							Statement stmt = conn.createStatement();
							stmt.executeUpdate(sqlupdate);

						} catch (Exception e) {
							e.printStackTrace();
						}
						String zipFilepath = "C:\\Users\\dell\\eclipse-workspace\\OperatorCircleProject\\src\\main\\webapp\\files\\Failresult_"
								+ id + ".zip";
						FileOutputStream fos = new FileOutputStream(zipFilepath);
						ZipOutputStream zipOutputStream = new ZipOutputStream(fos);
						zipOutputStream.putNextEntry(new ZipEntry(filename));
						workbook.write(zipOutputStream);
						zipOutputStream.closeEntry();
						zipOutputStream.close();
						response.setContentType("text/plain");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(zipFilepath);
					}

					workbook.close();
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}

}
