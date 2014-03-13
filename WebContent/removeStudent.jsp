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
            <h3>Remove Student</h3>
            <form action="RemoveStudentSave" method="post">
                Student to Remove: 
                <select name="sID">
                    <c:forEach var="studentDTO" items="${studentBean.studentList}" varStatus="rowCounter">
                        <option value="<c:out value="${studentDTO.studentID}" />" ><c:out value="${studentDTO.lastName}" />, <c:out value="${studentDTO.firstName}" /></option>
                    </c:forEach>
                </select>
                <br>
                <input type="submit" value="Remove Student" />
            </form>
        </div>
    </body>

</html>