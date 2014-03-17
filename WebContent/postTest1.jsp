<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
                Number of Performance Range You Entered Is: <c:out value="${preTestSetupBean.numberOfRange}" />
                <br/>
                <input type="submit" value="next" />
            </form>
        </div>

    </body>
</html>