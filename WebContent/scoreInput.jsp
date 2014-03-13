<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="studentBean" scope="request" class="com.pinpointgrowth.beans.StudentBean">
    <jsp:setProperty name="studentBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="objectiveDisplayBean" scope="request" class="com.pinpointgrowth.beans.ObjectiveDisplayBean">
    <jsp:setProperty name="objectiveDisplayBean" property="*" />      
</jsp:useBean>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input Student Data</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>Student Name: <c:out value="${studentBean.studentDTO.lastName}" />, <c:out value="${studentBean.studentDTO.firstName}" /></h3>
            <a href = "studentList.jsp">Go Back</a>
        </div>

        <div id = "form">
            <form action="SaveScores" method="post">
                <input type="hidden" name="aID" value="${param.aID}" />
                <input type="hidden" name="sID" value="${param.sID}" />
                <table border="1">
                    <c:forEach var="objectiveDTO" items="${objectiveDisplayBean.objectiveList}" varStatus="rowCounter">
                        <tr>
                            <td><c:out value="${objectiveDTO.objectiveName}" /></td>
                            <td><input type="text" size="1" name="<c:out value="${objectiveDTO.objectiveID}" />" autocomplete="off" value="<c:out value="${objectiveDTO.performanceScore}" />" /></td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="submit" value="Submit Scores" />
            </form>
        </div>
    </body>
</html>