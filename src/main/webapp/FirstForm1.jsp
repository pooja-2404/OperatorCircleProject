<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.HashSet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Dropdown from HashMap</title>
<style>
body {
	background-color: #d7d0d0;
	margin: 20px;
	padding: 30px;
}

th {
	font-style: italic;
	font-size: 20px;
	padding: 20px;
}

th, td {
	border: 1px solid black;
	height: 20px;
	width: 30px;
}

table {
	padding: 50px;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function() {
		function processData(){		
			$.ajax({
				url : "ProcessMaster",
				method : "POST",
				data : $("#date").serialize(),
				success : function(response) {
					var data = response.dataList;
					$("#table1 tbody").empty();
                   $.each(data, function (index, item) {
                       // $("#table1 tbody").append(
                            var row= "<tr>" +
                            "<td>" + item.Id + "</td>" +
                            "<td>" + item.operator + "</td>" +
                            "<td>" + item.circle + "</td>" +
                            "<td>" + item.startdate + "</td>" +
                            "<td>" + item.enddate + "</td>" +                            
                            "<td>" + item.status + "</td>" +
                            "<td>" + item.processStatus + "</td>";
                            if (item.processStatus === 'Done') {
                                row += "<td><a href='/OperatorCircleProject/Dashboard?Id=" + item.Id + "&status=" + item.status + "' download>Download</a></td>";
                            } else {
                                row += "<td></td>";
                            }
                            row += "</tr>";
                            $("#table1 tbody").append(row);
                        //);
                    });
				},			
		});
		}
		processData();
	setInterval(processData,3000);
	});
	$(document).ready(function() {
		$("#insert-button").click(function() {
			$.ajax({
				url : "InsertSubmit",
				method : "POST",
				data : $("#date").serialize(),
				success : function(response) {
					var data = response.dataList;
					$("#table1 tbody").empty();
                    $.each(data, function (index, item) {
                        $("#table1 tbody").append(
                            "<tr>" +
                            "<td>" + item.Id + "</td>" +
                            "<td>" + item.operator + "</td>" +
                            "<td>" + item.circle + "</td>" +
                            "<td>" + item.startdate + "</td>" +
                            "<td>" + item.enddate + "</td>" +                            
                            "<td>" + item.status + "</td>" +
                            "<td>" + item.processStatus + "</td>" +
                            "</tr>"
                        );
                    });
				},
		});
		
		});
	});
	
	$(document).ready(function() {			
			function processData(){
		$.ajax({
			url : "ResultSetToExcel",
				method : "GET",
				success : function(response) {
					
				},				
		});		
		}			
		setInterval(processData, 5000);
		});
	
	
</script>
</head>
<body>
	<form id="date">

		<label>Operator</label> <select id="operator" name="operator" required>
			<c:forEach var="data" items="${hashmap.keySet()}">
				<option value="${data}">${data}</option>
			</c:forEach>
		</select> <label>Circle</label> <select id="circle" name="circle" required>
		
			<option val="Select Circle..." selected>Select Circle</option>
		</select>
		<script>
        const operatorDropdown = document.getElementById('operator');
    const circleDropdown = document.getElementById('circle');
    operatorDropdown.addEventListener('change', function() {
        const selectedOperator = operatorDropdown.value;
        circleDropdown.innerHTML = '';
        if (selectedOperator) {
            <c:forEach var="entry" items="${hashmap.entrySet()}">
                if ("<c:out value='${entry.key}'/>" === selectedOperator) {
                    <c:forEach var="value" items="${entry.value}">
            circleDropdown.innerHTML += '<option value="${value}">${value}</option>';
                    </c:forEach>
                }
            </c:forEach>
        }
    });
</script>
		<label>Status</label> <select id="status" name="status">
			<option value="Delivered">Delivered</option>
			<option value="Failed">Failed</option>
		</select> <label for="startdate">Select a Start Date:</label> <input
			type="date" id="startdate" name="startdate" value="2023-10-01">
		<label for="enddate">Select a End Date:</label> <input type="date"
			id="enddate" name="enddate"
			value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date())%>">
		<button type="button" id="insert-button" name="insert-button">Submit</button>
	</form>
	<form id="form1">
		<div>
			<table id="table1">
				<thead>
					<tr>
						<th>Id</th>
						<th>Operator</th>
						<th>Circle</th>
						<th>StartDate</th>
						<th>EndDate</th>
						<th>Status</th>
						<th>ProcessStatus</th>
						<th>Link</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

	</form>

</body>
</html>