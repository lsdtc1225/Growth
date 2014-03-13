<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SLO Setup</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>Tradition Performance</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id = "form">
            <h3>PreTest</h3>
            <form action="preTest.jsp" method="post">
                <input type="hidden" name="TestSubmitted" value="true" />
                Name for this test: 
                <c:choose>
                    <c:when test="${!param.TestSubmitted}">
                        <input type="text" name="testName" value="Pre-test 1" /><br>
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="testName" value="${param.testName}" /><br>
                    </c:otherwise>
                </c:choose>
                Number of students for this test: 
                <c:choose>
                    <c:when test="${!param.TestSubmitted}">
                        <input type="text" name="numberOfStudent" value="5" size="1" /><br>
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="numberOfStudent" size="1" value="<c:out value="${param.numberOfStudent}" />"/><br>
                    </c:otherwise>
                </c:choose>
                <input type="submit" value="Next" /><br>
            </form>
            <c:if test="${param.TestSubmitted}">
                <form action="preTest2.jsp" method="POST">
                    <input type="hidden" name="postScore" value="N" />
                    <input type="hidden" name="testName" value="${param.testName}" />
                    <input type="hidden" name="numberOfStudent" value="${param.numberOfStudent}" />
                    <table border="1">
                        <tr>
                            <td>Student Name</td>
                            <td>Score(0-100)</td>
                        </tr>
                        <c:forEach var="i" begin="1" end="${param.numberOfStudent}" step="1" varStatus ="status">
                            <tr>
                                <td><input type="text" name="StudentName_${i}" /></td>
                                <input type="hidden" name="StudentName_${i}" value="1" size="1"/>
                                <td><input type="text" name="StudentScore_${i}" /></td>
                                <input type="hidden" name="StudentScore_${i}" value="1" size="1"/>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                    <input type="submit" value="Create Test Report" />
                </form>
            </c:if>
        </div>
    </body>

</html>