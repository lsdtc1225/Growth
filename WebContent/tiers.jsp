<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="tierBean" scope="request"class="com.pinpointgrowth.beans.TierBean">
    <jsp:setProperty name="tierBean" property="*" />      
</jsp:useBean>
<jsp:setProperty name="tierBean" property="courseID" value="${param.cID}" />
<c:if test="${tierBean.saveTiers()}" >
</c:if>
<c:if test="${tierBean.savePercents()}" >
</c:if>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tiers</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>Tier setup</h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <div id = "form">
            <h3>Set up tiers</h3>
            <form method="post" action="Tier">
                <input type="hidden" name="cID" value="${param.cID}" />
                <input type="hidden" name="tier" value="true" />
                <input type="hidden" name="numberOfTiers" value="${tierBean.tiers.size()}" />
                <input type="hidden" name="update" value="true" />
                <input type="hidden" name="submitted" value="true" />
                <table border="1">
                    <tr>
                        <td>Tier</td>
                        <td>Percent</td>
                        <td>Growth Target</td>
                    </tr>
                    <c:forEach var="tier" items="${tierBean.tiers}" varStatus="rowCounter">
                        <tr>
                            <td>Tier <c:out value="${rowCounter.count}" /></td>
                            <td><input type="text" size="1" name="<c:out value="${rowCounter.index}" />" value="<fmt:formatNumber type = "percent" maxFractionDigits="2"><c:out value="${tier.size() / tierBean.totalStudents}" /></fmt:formatNumber>" /></td>
                            <td><input type="text" size="1" name="target_${rowCounter.index}" value="${tierBean.getPercent(rowCounter.index)}" /></td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="submit" value="Save Growth Targets and Update Tiers" />
            </form>
            <br>
            <c:forEach var="tier" items="${tierBean.tiers}" varStatus="rowCounter1">
                <table border="1">
                    <c:forEach var="student" items="${tier}" varStatus="rowCounter">
                        <tr bgcolor="<c:out value="${tierBean.colors.get(rowCounter1.index)}" />">
                            <td><c:out value="${student.lastName}" />, <c:out value="${student.firstName}" /></td>
                            <td><c:out value="${student.score}" /></td>
                        </tr>
                    </c:forEach>
                </table>
                <h3>-------------------------------------------</h3>
            </c:forEach>
            <br>
            Note: Please make sure you have pushed the "Save Growth Targets and Update Tiers" button 
            <br> 
            at least once before finalizing.
            <br>
            <form action="extension.jsp?cID=<c:out value="${param.cID}" />" method="post">
                <input type="submit" value="Finalize Tiers" />
            </form>
        </div>
    </body>
    
</html>