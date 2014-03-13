<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="assignmentListBean" scope="request" class="com.pinpointgrowth.beans.AssignmentListBean"></jsp:useBean>
<jsp:setProperty property="courseID" name="assignmentListBean" value="${param.cID}" />
<jsp:setProperty property="postScore" name="assignmentListBean" value="N" />

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Assignment</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>
        <div id = "frontForm">
            <h3>Choose Assignment</h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <c:choose>
            <c:when test = "${assignmentListBean.assignmentList.size() > 0}">
                <div id = "form">
                    <h3>Choose assignment</h3>
                    <form action="StudentEvaluation" method="post">
                        <input type="hidden" name="courseID" value="${param.cID}" />
                        Assignment Name: 
                        <select name="assignmentID">
                            <c:forEach var="assignmentDTO" items="${assignmentListBean.assignmentList}" varStatus="rowCounter">
                                <option value="<c:out value="${assignmentDTO.assignmentID}" /> "><c:out value="${assignmentDTO.assignmentName}" /></option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="submit" value="Submit" />
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div id = "form">
                    <h2>
                        <b>No Assignments available for grading.</b>
                        <br>
                        <a href="createAssignment.jsp?cID=<c:out value="${param.cID}" />">Go to Create Assignment</a>
                        <br>
                        <a href="mainUserPage.jsp">Go back to your main page.</a>
                    </h2>
                </div>
            </c:otherwise>
        </c:choose>
        
    </body>
</html>