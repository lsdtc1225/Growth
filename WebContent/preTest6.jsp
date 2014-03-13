<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.pinpointgrowth.traditional.PreTestRecord"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord"></jsp:useBean>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>Tradition Performance</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <h3> Here is info for the class </h3>

        <%
            int count = 0;
            while (count < preTest.getStudentRecordCount()){
                String studentName = preTest.getStudentName(count);
                int studentScore = preTest.getStudentScore(count);
                String result = preTest.checkStudentScore(studentScore);
                count++;
                out.println(studentName+"       " + studentScore + "      "  + result + "<br>");    
            }
        %>
        <a href="TraditionalHomePage.jsp">Go back to traditional home page</a>
        <br/>
    </body>

</html>
