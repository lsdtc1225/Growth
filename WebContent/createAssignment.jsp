<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="courseBean" scope="request" class="com.pinpointgrowth.beans.CourseBean">
    <jsp:setProperty name="courseBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="rubricDisplayBean" scope="request" class="com.pinpointgrowth.beans.RubricDisplayBean"></jsp:useBean>
<jsp:setProperty property="userName" name="rubricDisplayBean" value="${loginInfo.username}"/>
<jsp:setProperty property="postScore" name="rubricDisplayBean" value="N"/>

<jsp:useBean id="newAssignmentBean" scope="request" class="com.pinpointgrowth.beans.NewAssignmentBean"></jsp:useBean>
<jsp:setProperty property="courseIDForSetup" name="courseBean" value="${param.cID}" />
<c:if test="${courseBean.createDTO()}" >
</c:if>
<jsp:setProperty property="courseID" name="newAssignmentBean" value="${param.cID}" />

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
            <h3>Course Name: <c:out value="${courseBean.courseDTO.courseName}" /></h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <c:choose>
            <c:when test = "${rubricDisplayBean.rubricList.size() > 0}">
                <div id = "form">
                    <h3>New Assignment</h3>
                    <form action="newAssignmentSaveAction.jsp" method="post">
                        <input type="hidden" name=courseID value="${param.cID}" />
                        Assignment Name: <input type="text" name="assignmentName" />
                        <br>
                        Assignment Rubric: 
                        <select name="rubricID">
                            <c:forEach var="rubricDTO" items="${rubricDisplayBean.rubricList}" varStatus="rowCounter">
                                <option value="<c:out value="${rubricDTO.rubricID}" />" >
                                    <c:out value="${rubricDTO.rubricName}" />
                                </option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="submit" value="Create Assignment" />
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div id = "form">
                    <h2>
                        <b>No Rubric available for assignments.</b>
                        <br>
                        <a href="rubricBasedSetup.jsp">Go to Setup Rubric</a>
                        <br>
                        <a href="mainUserPage.jsp">Go back to your main page.</a>
                    </h2>
                </div>
            </c:otherwise>
        </c:choose>
    </body>

</html>