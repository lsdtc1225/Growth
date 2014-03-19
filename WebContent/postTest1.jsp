<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<sql:setDataSource var="testRecord" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<sql:query dataSource="${testRecord}" var="preTestResultSet"> SELECT * FROM Pinpoint.PreTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/></sql:query>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post-Test Setup</title>
    </head>

    <body>
        <div id="frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id="frontForm">
            <h3>POST-TEST SET UP</h3>
            <a href="mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id="form">
            <form action="postTest2.jsp" method="post">
                Number of Performance Range You Entered Is: <c:out value="${preTestResultSet.rowCount}" />
                <br/>
                <input type="submit" value="next" />
            </form>
        </div>

    </body>
</html>