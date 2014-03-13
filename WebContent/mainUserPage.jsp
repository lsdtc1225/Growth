<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="courseDisplayBean" scope="request" class="com.pinpointgrowth.beans.CourseDisplayBean" ></jsp:useBean>
<jsp:setProperty property="userName" name="courseDisplayBean" value="${loginInfo.username}" />

<html>
    <title>Welcome, <c:out value="${loginInfo.username}" />!</title>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
    
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>
        
        <div id = "frontForm">
            <h3>Welcome, <c:out value="${loginInfo.username}" />!</h3>
            <a href = "frontPage.jsp">Logout</a>
        </div>
        
        <div>
            <header>
                <h1>Courses</h1>
            </header>
        </div>
        
        <c:forEach var="courseDTO" items="${courseDisplayBean.courseNames}" varStatus="rowCounter">
            <h3><a href="ViewCourse?cID=<c:out value="${courseDTO.courseID}" /> ">${courseDTO.courseName}</a></h3>
        </c:forEach>
        
        <div>
            <h3><a href="addCoursePage.jsp">Add a Course</a></h3>
            <br>
            <h3><a href="EvaluateTeacher">See Final Evaluation Report</a></h3>
        </div>
    
    </body>
    
</html>