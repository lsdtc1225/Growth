<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.pinpointgrowth.traditional.PreTestRecord"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord">
</jsp:useBean>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>Tradition Performance</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <% 
            int numberOfDes = preTest.getNumberOfDes();
            session.setAttribute("numberOfDes",numberOfDes);
            for (int i = 1 ; i <= numberOfDes; i++){
                String descrptionStr = request.getParameter("rangeDescription_"+i+""); 
                preTest.insertDescription(descrptionStr);
            }
        %>

        <div id="form">
            <form action="PreTest5.jsp" method="POST">
                <input type="hidden" name="TestSubmitted" value="true" />
                Number of students for this test: 
                <c:choose>
                    <c:when test="${!param.TestSubmitted}">
                        <input type="text" name="numberOfStudent" value="5" size="1" />
                        <br>
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="numberOfStudent" size="1" value="<c:out value="${param.numberOfStudent}" />"/><br>
                    </c:otherwise>
                </c:choose>

                <input type="submit" value="Next" /><br>
            </form>

            <c:if test="${param.TestSubmitted}">
                <form action="PreTest6.jsp" method="POST">
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