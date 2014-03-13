<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

    <title>Growth Targets added!</title>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "form">
            <h1>
                Must enter all student data before setting up growth targets.<a href="chooseAssignment.jsp?cID=<c:out value="${param.cID}" />">Go back to Input Student Data</a>
            </h1>
        </div>
    </body>
    
</html>