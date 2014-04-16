<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
        </div>

        <div id="form">
            <h2>Enter Student's Scores</h2>
            <form action="TraditionalAddStudentScore" method="POST">
                <input type="hidden" name="cID" value="<c:out value="${param.cID}"/>"/>
                <table border="1">
                    <tr>
                        <th>Student FirstName</th>
                        <th>Student LastName</th>
                        <th>pre-Test Score(0-100)</th>
                    </tr>
                    <c:forEach var="student" items="${studentList}" varStatus="rowCounter">
                        <tr>
                            <td><c:out value="${student.studentFirstName}"/></td>
                            <td><c:out value="${student.studentLastName}"/></td>
                            <td><input type="text" name="preTest_${student.sID}" /></td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" value="Next" />
            </form>
        </div>

    </body>
</html>