<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<sql:setDataSource var="studentEnrolled" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<sql:query dataSource="${studentEnrolled}" var="resultSet">SELECT S.S_ID, SFName, SLName FROM Pinpoint.Enrolled E, Pinpoint.Student S WHERE E.S_ID = S.S_ID AND C_ID = <c:out value="${preTestSetupBean.cID}"/>
</sql:query> 

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <title>Post-Test Setup</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>POST-TEST SET UP</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id="form">
            <h2>Enter Students' Scores</h2>
            <form action="PostTestScore" method="post">
                <table border="1">
                    <tr>
                        <th>Student FirstName</th> <th>Student LastName</th> <th>post-Test Score(0-100)</th>
                    </tr>
                    <c:forEach var="row" items="${resultSet.rows}">
                        <tr>
                            <td><c:out value="${row.SFName}"/></td>
                            <td><c:out value="${row.SLName}"/></td>
                            <td><input type="text" name="student_${row.S_ID}" value=""/></td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" value="Submit"/>
            </form>
        </div>  
    </body>
</html>