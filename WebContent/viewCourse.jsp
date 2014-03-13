<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="courseBean" scope="request" class="com.pinpointgrowth.beans.CourseBean">
    <jsp:setProperty name="courseBean" property="*" />      
</jsp:useBean>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Course</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>
                Course Name: <c:out value="${courseBean.courseDTO.courseName}" />
                <br>
                Course Term: <c:out value="${courseBean.courseDTO.term}" />
            </h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>
        <h2>
            <c:choose>
                <c:when test="${courseBean.courseDTO.getLocked()}">
                    <a href="lateStudentAdd.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Add Student</a>
                    <br>
                    <a href="RemoveStudent?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Remove Student</a>
                    <br>
                    <a href="postRubricBasedSetup.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Setup Post Rubric</a>
                    <br>
                    <a href="postCreateAssignment.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Create Post Assignment</a>
                    <br>
                    <a href="postChooseAssignment.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Enter Post Assessment Data</a>
                    <br>
                    <a href="ExtensionMet?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Evaluate Extension Activity</a>
                    <br>
                </c:when>
                <c:otherwise>
                    <a href="rubricBasedSetup.jsp">Setup Rubric</a>
                    <br>
                    <a href="createAssignment.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Create Assignment</a>
                    <br>
                    <a href="chooseAssignment.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Input Student Data</a>
                    <br>
                    <a href="growthTargetQuestion.jsp?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Setup Growth Targets</a>
                    <br>
                    <a href="RemoveStudent?cID=<c:out value="${courseBean.courseDTO.courseID}" />">Remove Student</a>
                    <br>
                </c:otherwise>
            </c:choose>
        </h2>
    </body>

</html>

