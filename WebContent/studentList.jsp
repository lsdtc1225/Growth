<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="studentDisplayBean" scope="request" class="com.pinpointgrowth.beans.StudentDisplayBean">
    <jsp:setProperty name="studentDisplayBean" property="*" />      
</jsp:useBean>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Data Input</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h2><a href = "mainUserPage.jsp">Go Back</a></h2>
        </div>

        <div id = "sideForm">
            <b>Click a student's name to enter scores. Students in red are incomplete, students in green are complete.</b>
        </div>

        <div id = "form">
            <h2>Students</h2>
            <table border="0">
                <c:forEach var="studentDTO" items="${studentDisplayBean.studentList}" varStatus="rowCounter">
                    <tr>
                        <c:choose>
                            <c:when test="${studentDTO.performanceScoresEntered}">
                                <td> <img src = "images/check mark.png" alt = "Check Mark" height = "20" width = "20"> </td>
                                <td bgcolor="#83ff31">
                            </c:when>
                            <c:otherwise>
                                <td><img src = "images/x_mark.png" alt = "X Mark" height = "20" width = "20"> </td>
                                <td bgcolor="#fd3f3f">
                            </c:otherwise>
                        </c:choose>
                        <b>
                            <a href="ScoreInput?aID=<c:out value="${param.assignmentID}" />&sID=<c:out value="${studentDTO.studentID}" />"><c:out value="${studentDTO.lastName}" />, <c:out value="${studentDTO.firstName}" /></a>
                        </b>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
    
</html>