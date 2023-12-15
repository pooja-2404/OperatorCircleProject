package com.operator;
import java.util.Date;

public class Data{
	Date startdate,enddate;
	int Id;
	String operator, circle,status,processStatus;
	
	public Data( Date startdate, Date enddate, int id, String operator, String circle, String status, String processStatus) {
		this.startdate=startdate;
		this.enddate=enddate;
		this.Id=id;
		this.operator=operator;
		this.circle=circle;
		this.status=status;
		this.processStatus=processStatus;
	}

	public int getId() {
		return Id;
	}
	public String getOperator() {
		return operator;
	}
	public String getCircle() {
		return circle;
	}
	public String getStatus() {
		return status;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	
	public Date getstartDate() {
		return startdate;
	}
	
	public Date getendDate() {
		return enddate;
	}
}
