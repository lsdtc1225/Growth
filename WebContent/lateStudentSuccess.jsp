<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="lateStudentScoreBean" scope="request" class="com.pinpointgrowth.beans.GrowthTargetBean">
    <jsp:setProperty name="lateStudentScoreBean" property="*" />      
</jsp:useBean>
<jsp:setProperty name="lateStudentScoreBean" property="courseID" value="${param.cID}" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Setup Growth Target</title>
    </head>

    <body>
        <form action="SaveTargets" method="post" >
            <input type="hidden" name="cID" value="${param.cID}" />
            <input type="hidden" name="numberOfStudents" value="${lateStudentScoreBean.targets.size()}" />
            <table border="1">
                <c:forEach var="student" items="${lateStudentScoreBean.targets}" varStatus="rowCounter">
                    <tr>
                        <td>
                           <c:out value="${student.lastName}" />, <c:out value="${student.firstName}" />
                           <input type="hidden" name="sID_${rowCounter.index}" value="<c:out value="${student.studentID}" />" />
                        </td>
                        <td><c:out value="${student.score}" /></td>
                        <td>
                            <input type="text" size="1" name= "targetForStudent_${rowCounter.index}" value="<c:out value="${student.growthTarget}" />" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <input type="Submit" value="Save" />
        </form>
    </body>

</html>