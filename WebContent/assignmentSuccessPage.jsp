<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="addCourseExcelDisplayBean" scope="request" class="com.pinpointgrowth.beans.AddCourseExcelDisplayBean">
    <jsp:setProperty name="addCourseExcelDisplayBean" property="*" />      
</jsp:useBean>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Course</title>
    </head>

    <body>
        <div id="frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>
        <h2>Assignment Saved!</h2>
        <h2>
            <a href = "mainUserPage.jsp">Go Back</a>
        </h2>
    </body>

</html>