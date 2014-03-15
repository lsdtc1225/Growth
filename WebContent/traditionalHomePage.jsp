<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord">
</jsp:useBean>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>
                Course Name: <c:out value="${courseBean.courseDTO.courseName}" />
                <br/>
                Course Term: <c:out value="${courseBean.courseDTO.term}" />
            </h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <div>
            <header>
                <h1>TraditionalBased</h1>
            </header>
        </div>

        <div>
            <a href="preTest1.jsp">Setup Pre-test</a>
            <br/>
        </div>

        <c:if test="${preTest.isReady()}">
            <a href="ViewPreTest.jsp">View Pre-test</a>
            <br/>
        </c:if>
    </body>
</html>