<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.pinpointgrowth.traditional.PreTestRecord"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord">
</jsp:useBean>

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
            String testName = request.getParameter("testName"); 
            session.setAttribute("testName",testName); 
            preTest.setTestName(testName);
        %>

        <div id = "form">
            <h2>Testname : <%=testName%> </h2>
            <br/>
            <form action="preTest3.jsp" method="post">
                How many performance descriptors do you want? (3-5)
                <input type="text" name="numberOfDes" />
                <input type="hidden" name="numberOfDes" value="3" size="1"/>
                <br/>
                <input type="submit" value="set up cut score" />
            </form>
        </div>
    </body>
</html>