<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
   
          
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <title>Student Traditional Evaluation</title>
    </head>

    <body>
        <div id="frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        
        <div id="form">
            <h1>Final Evaluation !</h1>
            <form action="mainUserPage.jsp" method="post">
                <table border="1">
                    <tr>
                        <th>Performance Evaluation</th><th>First Name</th><th>Last Name</th><th>Traditional Evaluation</th>
                    </tr>
                    <c:forEach var="evaluationDTO" items="${studentEvaluationBean.evaluationList}" varStatus="rowCounter">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${evaluationDTO.performancePassed}">
                                        <img src = "images/check mark.png" alt = "Check Mark" height = "20" width = "20">
                                    </c:when>
                                    <c:otherwise>
                                        <img src = "images/x_mark.png" alt = "X Mark" height = "20" width = "20">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${evaluationDTO.SFName}"/></td>
                            <td><c:out value="${evaluationDTO.SLName}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${evaluationDTO.passed}">
                                        <img src = "images/check mark.png" alt = "Check Mark" height = "20" width = "20">
                                    </c:when>
                                    <c:otherwise>
                                        <img src = "images/x_mark.png" alt = "X Mark" height = "20" width = "20">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>

                <h2>Statistics</h2>
                <table border="1">
                    <tr>
                        <td></td>
                        <td>Performance Based</td>
                        <td>Traditional Based</td>
                    </tr>
                    <tr>
                        <td>Weight</td>
                        <td><fmt:formatNumber type="percent" maxIntegerDigits="5" value="${studentEvaluationBean.performanceWeight}" /></td>
                        <td><fmt:formatNumber type="percent" maxIntegerDigits="5" value="${studentEvaluationBean.traditionalWeight}" /></td>
                    </tr>
                    <tr>
                        <td>Pass Rate</td>
                        <td><fmt:formatNumber type="percent" maxIntegerDigits="5" value="${studentEvaluationBean.performancePassRate}" /></td>
                        <td><fmt:formatNumber type="percent" maxIntegerDigits="5" value="${studentEvaluationBean.traditionalPassRate}" /></td>
                    </tr>
                </table>
                <br/>
                Final Evaluation Rate for this course is:<fmt:formatNumber type="percent" maxIntegerDigits="5" value="${studentEvaluationBean.result}" />
                <br/>
                <input type="submit" value="Home"/>
            </form>
        </div>  
    </body>
</html>