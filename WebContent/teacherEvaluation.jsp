<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="teacherEvaluationBean" scope="request" class="com.pinpointgrowth.beans.TeacherEvaluationBean">
    <jsp:setProperty name="teacherEvaluationBean" property="*" />      
</jsp:useBean>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evaluation</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3><a href="mainUserPage.jsp">Back to main page</a></h3>
        </div>

        <div id = "sideForm">
            <h3>Your Numerical Rating Explained</h3>
            <table border = "1">
                <tr>
                    <td>Percentage of students<br>that met or exceeded growth</td>
                    <td>Descriptive Rating</td>
                    <td>Numerical Rating</td>
                </tr>
                <tr>
                    <td align = "center">90 - 100</td>
                    <td align = "center">Most Effective</td>
                    <td align = "center">5</td>
                </tr>
                <tr>
                    <td align = "center">80 - 89</td>
                    <td align = "center">Above Average</td>
                    <td align = "center">4</td>
                </tr>
                <tr>
                    <td align = "center">70 - 79</td>
                    <td align = "center">Average</td>
                    <td align = "center">3</td>
                </tr>
                <tr>
                    <td align = "center">60 - 69</td>
                    <td align = "center">Approaching Average</td>
                    <td align = "center">2</td>
                </tr>
                <tr>
                    <td align = "center">50 - 59</td>
                    <td align = "center">Least Effective</td>
                    <td align = "center">1</td>
                </tr>
            </table>
        </div>

        <div id = "form">
            Percentage: <fmt:formatNumber type = "percent"><c:out value="${teacherEvaluationBean.percentage}" /></fmt:formatNumber>
            <br>
            Numerical Rating: <c:out value="${teacherEvaluationBean.numericalRating}" />
            <br>
            <table border = "1">
                <tr>
                    <td>Student Name</td>
                    <td>Class</td>
                    <td>Pre-Score</td>
                    <td>Post-Score</td>
                    <td>Growth</td>
                    <td>Target</td>
                    <td>Met?</td>
                </tr>
                <c:forEach var="finalEvaluationDTO" items="${teacherEvaluationBean.yesList}" varStatus="rowCounter">
                    <tr>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.lastName}" />, <c:out value="${finalEvaluationDTO.studentDTO.firstName}" /></td>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.courseName}" /></td>
                        <td><c:out value="${finalEvaluationDTO.preScore}" /></td>
                        <td><c:out value="${finalEvaluationDTO.postScore}" /></td>
                        <td><fmt:formatNumber maxFractionDigits = "2"><c:out value="${finalEvaluationDTO.growth}" /></fmt:formatNumber></td>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.growthTarget}" /></td>
                        <td><c:out value="yes" /></td>
                        <td>
                            <c:if test="${finalEvaluationDTO.topTier}">
                                <c:choose>
                                    <c:when test="${finalEvaluationDTO.extensionMet}">
                                        <img src = "images/check mark.png" alt = "Check Mark" height = "20" width = "20">
                                    </c:when>
                                    <c:otherwise>
                                        <img src = "images/x_mark.png" alt = "X Mark" height = "20" width = "20">
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <br>
            <table border = "1">
                <c:forEach var="finalEvaluationDTO" items="${teacherEvaluationBean.noList}" varStatus="rowCounter">
                    <tr>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.lastName}" />, <c:out value="${finalEvaluationDTO.studentDTO.firstName}" /></td>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.courseName}" /></td>
                        <td><c:out value="${finalEvaluationDTO.preScore}" /></td>
                        <td><c:out value="${finalEvaluationDTO.postScore}" /></td>
                        <td><fmt:formatNumber maxFractionDigits = "2"><c:out value="${finalEvaluationDTO.growth}" /></fmt:formatNumber></td>
                        <td><c:out value="${finalEvaluationDTO.studentDTO.growthTarget}" /></td>
                        <td><c:out value="no" /></td>
                        <td>
                            <c:if test="${finalEvaluationDTO.topTier}">
                                <c:choose>
                                    <c:when test="${finalEvaluationDTO.extensionMet}">
                                        <img src = "images/check mark.png" alt = "Check Mark" height = "20" width = "20">
                                    </c:when>
                                    <c:otherwise>
                                        <img src = "images/x_mark.png" alt = "X Mark" height = "20" width = "20">
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>

</html>