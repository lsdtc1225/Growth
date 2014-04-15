<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<sql:setDataSource var="testRecord" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Student</title>
	</head>

	<body>
		<div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>
        <div id = "frontForm">
            <h3>ADD NEW STUDENT</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id="form">
        	<c:if test="${!param.numberOfStudentSubmitted}">
	        	<form action="traditionalAddStudent.jsp" method="POST">
	        		<input type="hidden" name="numberOfStudentSubmitted" value="true"/>
	        		<input type="hidden" name="cID" value="<c:out value="${param.cID}"/>"/>
	        		Number of Student: <input type="text" name="numberOfStudent" />
	        		<br/>
        			<input type="submit" value="Next" />
        			<br/>
	        	</form>
	        </c:if>

        	<c:if test="${param.numberOfStudentSubmitted}">
        		<form action="TraditionalAddStudent" method="POST">
        			Number of Student: <c:out value=" ${param.numberOfStudent} "/>
        			<br/>
        			<input type="hidden" name="cID" value="<c:out value="${param.cID}"/>" />
        			<input type="hidden" name="numberOfStudent" value="${param.numberOfStudent}" />
        			<br/>
        			<table border="1">
        				<tr>
        					<td>First Name</td>
        					<td>Last Name</td>
        					<td>Grade Level</td>
        				</tr>
        				<c:forEach var="i" begin="1" end="${param.numberOfStudent}" step="1" varStatus="status">
        					<tr>
        						<td><input type="text" name="studentFirstName_${i}" /></td>
        						<td><input type="text" name="studentLastName_${i}" /></td>
        						<td><input type="text" name="studentGrade_${i}" size="5"/></td>
        					</tr>
	        			</c:forEach>
        			</table>
        			<br/>
        			<input type="submit" value="Next"/>
        		</form>
	        </c:if>
        </div>
	</body>
</html>