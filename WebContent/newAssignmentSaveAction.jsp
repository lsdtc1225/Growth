<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="courseBean" scope="request" class="com.pinpointgrowth.beans.CourseBean">
    <jsp:setProperty name="courseBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="newAssignmentBean" scope="request" class="com.pinpointgrowth.beans.NewAssignmentBean">
    <jsp:setProperty name="newAssignmentBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="newAssignmentSaveBean" scope="page" class="com.pinpointgrowth.beans.NewAssignmentSaveActionBean"></jsp:useBean>
<jsp:setProperty property="rubricID" name="newAssignmentSaveBean" value= "${newAssignmentBean.rubricID}" />
<jsp:setProperty property="assignmentName" name="newAssignmentSaveBean" value= "${newAssignmentBean.assignmentName}" />
<jsp:setProperty property="courseID" name="newAssignmentSaveBean" value= "${newAssignmentBean.courseID}" />

<c:choose>
    <c:when test="${newAssignmentSaveBean.saveNewAssignmentData()}">
        <jsp:forward page="assignmentSuccessPage.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="errorPage.jsp" />
    </c:otherwise>
</c:choose>