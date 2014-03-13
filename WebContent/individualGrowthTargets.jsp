<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="growthTargetBean" scope="request" class="com.pinpointgrowth.beans.GrowthTargetBean">
    <jsp:setProperty name="growthTargetBean" property="*" />      
</jsp:useBean>
<jsp:setProperty name="growthTargetBean" property="courseID" value="${param.cID}" />

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Setup Growth Targets</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3><a href="mainUserPage.jsp">Back to main page</a></h3>
        </div>

        <div id = "form">
            <h3>Input Growth Targets</h3>
            <form action="SaveTargets" method="post" >
                <input type="hidden" name="cID" value="${param.cID}" />
                <input type="hidden" name="numberOfStudents" value="${growthTargetBean.targets.size()}" />
                <table border="1">
                    <c:forEach var="student" items="${growthTargetBean.targets}" varStatus="rowCounter">
                        <tr>
                            <td>
                                <c:out value="${student.lastName}" />, <c:out value="${student.firstName}" />
                                <input type="hidden" name="sID_${rowCounter.index}" value="<c:out value="${student.studentID}" />" />
                            </td>
                            <td>
                                <c:out value="${student.score}" />
                            </td>
                            <td>
                                <input type="text" size="1" name= "targetForStudent_${rowCounter.index}" value="<c:out value="${student.growthTarget}" />" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input type="Submit" value="Save" />
            </form>
        </div>
    </body>

</html>