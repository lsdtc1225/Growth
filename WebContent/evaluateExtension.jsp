<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="courseBean" scope="request" class="com.pinpointgrowth.beans.CourseBean">
    <jsp:setProperty name="courseBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="studentBean" scope="request" class="com.pinpointgrowth.beans.StudentDisplayBean">
    <jsp:setProperty name="studentBean" property="*" />      
</jsp:useBean>

<jsp:setProperty property="courseIDForSetup" name="courseBean" value="${param.cID}" />
<c:if test="${courseBean.createDTO()}" >
</c:if>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Remove Student</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>Course Name: <c:out value="${courseBean.courseDTO.courseName}" /></h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

            
        <div id = "form">
            <h3>Evaluate Extension Activity</h3>
            Extension:
            <br>
            <textarea rows="5" cols="25" readonly="readonly"><c:out value="${extension}" /></textarea>
            <br>
            <form action="ExtensionMetSave" method="post">
                <input type="hidden" name="cID" value="${param.cID}" />
                Select the students that accomplished the tasks required for the extension activity.
                <br>
                <c:forEach var="studentDTO" items="${studentBean.studentList}" varStatus="rowCounter">
                    <c:out value="${studentDTO.lastName}" />, <c:out value="${studentDTO.firstName} " />
                    <input type="checkbox" name="student_${studentDTO.studentID}" value="true" />
                    <br>
                </c:forEach>
                <br>
                <input type="submit" value="Submit" />
            </form>
        </div>

    </body>
</html>