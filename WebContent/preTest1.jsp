<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord">
</jsp:useBean>
    
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SLO Setup</title>
    </head>
    
    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>Tradition Performance</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id = "form">
            <h3>PreTest</h3>
            <form action="preTest2.jsp" method="post">
                <input type="hidden" name="TestSubmitted" value="true" />
                Name for this test: <input type="text" name="testName" value="Pre-test 1" />
                <br/>
                <input type="submit" value="Next" />
                <br/>
            </form>
        </div>
    </body>
</html>