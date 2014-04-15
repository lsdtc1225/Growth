<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
	<head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <title>Add Student Success !</title>
    </head>
	<body>
		<div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>Add Student</h3>
        </div>

        <div id="form">
            <form action="AfterPreSuccess" method="post">
                <h1>Add Student Success !</h1>
                <input type="hidden" name="cID" value="<c:out value="${param.cID}"/>"/>
                <!-- <input type="hidden" name="cName" value="<c:out value="${preTestSetupBean.cName}"/>"/> -->
                <input type="hidden" name="cName" value="<%= (String)request.getAttribute("cName")%>"/>
                <input type="submit" value="Home"/>
            </form>
        </div>
	</body>
</html>