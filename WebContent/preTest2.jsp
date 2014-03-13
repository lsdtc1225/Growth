<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.pinpointgrowth.traditional.PreTestRecord"%>
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
            String testName = request.getParameter("testName"); 
            session.setAttribute("testName",testName); 
        %>

        <h2>TestName:<%=testName%> </h2>
        <br/>

        <% 
            String studentNumberString = request.getParameter("numberOfStudent"); 
            int numberOfStudent = Integer.parseInt(studentNumberString);
            session.setAttribute("numberOfStudent",numberOfStudent);
            out.println("Student Name          Student Score <br>");
            for (int i = 1 ; i <= numberOfStudent; i++){
                String studentName = request.getParameter("StudentName_"+i+""); 
                preTest.AddName(studentName);
                String studentScore = request.getParameter("StudentScore_"+i+"");
                int scoreOfStudent = Integer.parseInt(studentScore);
                preTest.AddScore(scoreOfStudent);
                out.println(studentName+"       " + scoreOfStudent + "<br>");
            }
        %>

        <form action="preTest3.jsp" method="post">
            How many performance descriptors do you want? (3-5)
            <input type="text" name="numberOfDes" />
            <input type="hidden" name="numberOfDes" value="3" size="1"/>
            <br/>
            <input type="submit" value="set up cut score" />
        </form>
    </body>

</html>