<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pinpointgrowth.traditionalBeans.FinalEvaluationBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Welcome, <c:out value="${loginInfo.username}" />!</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>
        
        <div id = "frontForm">
            <h3>Welcome, <c:out value="${loginInfo.username}" />!</h3>
            <a href = "frontPage.jsp">Logout</a>
        </div>

        <div>
            <header>
                <h1>Final Evaluation</h1>
            </header>
        </div>


        <div id="form">

            
            <form action="mainUserPage.jsp" method="POST">
                <table border="1">
                    <tr>
                        <td>Course Name</td>
                        <td>Rate</td>
                    </tr>
                    <c:forEach var="finalEvaluationBean" items="${finalEvaluationBeanList}" varStatus="rowCounter">
                        <tr>
                            <td><c:out value="${finalEvaluationBean.cName}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${finalEvaluationBean.result < 0}">
                                        In Progress
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber type="percent" maxIntegerDigits="5" value="${finalEvaluationBean.result}" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                Average Rate: <fmt:formatNumber type="percent" maxIntegerDigits="5" value="${averageResult}" />
                <br/>
                <input type="submit" value="Home"/>
            </form>
        </div>
    </body>

</html>