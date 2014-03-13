<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="lateStudentBean" scope="request" class="com.pinpointgrowth.beans.LateStudentBean">
    <jsp:setProperty name="lateStudentBean" property="*" />      
</jsp:useBean>
<jsp:setProperty property="courseID" name="lateStudentBean" value="${param.cID}"/>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Student</title>
    </head>

    <body>
        <form action="SaveStudent" method="post">
            <input type="hidden" name="cID" value="${param.cID}" />
            First Name: <input type="text" name="firstName" />
            <br>
            Last Name: <input type="text" name="lastName" />
            <br>
            Grade Level: <input type="text" name="gradeLevel" size="1" />
            <br>
            <c:forEach var="lateStudentDTO" items="${lateStudentBean.lateStudentList}" varStatus="rowCounter">
                <c:out value="${lateStudentDTO.assignmentName}" />
                <br>
                <c:forEach var="objectiveDTO" items="${lateStudentDTO.objectiveList}" varStatus="rowCounter2">
                    <table>
                        <tr>
                            <td><c:out value="${objectiveDTO.objectiveName}" /></td>
                            <td><input type="text" name="<c:out value="${objectiveDTO.objectiveID}" />" value="0" size="1" /></td>
                        </tr>
                    </table>
                    <br>
                    <br>
                </c:forEach>
            </c:forEach>
            <input type="submit" value="Save New Student" />
        </form>
    </body>

</html>