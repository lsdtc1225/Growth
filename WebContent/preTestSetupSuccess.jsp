<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <title>Pre-Test Setup Success !</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>PRE-TEST SET UP</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id="form">
            <form action="mainUserPage.jsp" method="post">
                <h1>Pre-Test Record Setup Success !</h1>
                <input type="submit" value="Home"/>
            </form>
        </div>
    </body>
</html>