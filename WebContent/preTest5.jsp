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

        <% 
            int numberOfDes = preTest.getNumberOfDes();
            session.setAttribute("numberOfDes",numberOfDes);
            for (int i = 1 ; i <= numberOfDes; i++){
                String descrptionStr = request.getParameter("rangeDescription_"+i+""); 
                preTest.insertDescription(descrptionStr);
            }
        %>

        <jsp:forward page="/preTest6.jsp" />
        <a href="TraditionalHomePage.jsp">Go back to traditional home page</a>
        <br/>
    </body>

</html>