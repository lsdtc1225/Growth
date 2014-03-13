<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="addCourseExcelDisplayBean" scope="request" class="com.pinpointgrowth.beans.AddCourseExcelDisplayBean">
    <jsp:setProperty name="addCourseExcelDisplayBean" property="*" />      
</jsp:useBean>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Course</title>
    </head>

    <body>
        <p>You have successfully added the following course: </p>
        <br>
        Course Name: <c:out value="${addCourseExcelDisplayBean.courseName}" />
        <br>
        Course Term: <c:out value="${addCourseExcelDisplayBean.courseTerm}" />
        <br>
        Course Room: <c:out value="${addCourseExcelDisplayBean.courseRoom}" />
        <br>
        Students:
        <br>
        <table border = "1">
            <tr>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Grade</td>
            </tr>
            <c:forEach var="student" items="${addCourseExcelDisplayBean.studentList}">
                <tr>
                  <td>${student.studentFirstName}</td>
                  <td>${student.studentLastName}</td>
                  <td>${student.studentGrade}</td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <a href="mainUserPage.jsp">Go Back </a>
    </body>

</html>