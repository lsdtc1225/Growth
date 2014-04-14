<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<jsp:setProperty name="preTestSetupBean" property="numberOfRange" value="${param.numberOfRange }"/>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <title>Pre-Test Setup</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>PRE-TEST SET UP</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id = "form">
            <c:choose>
                <c:when test="${preTestSetupBean.numberOfRange >= 3 && preTestSetupBean.numberOfRange <= 5}">
                    <form action="PreTest" method="post">
                        <table border="1">
                            <tr>
                                <th>Cut Score</th><th>Performance Descriptor</th>
                            </tr>

                            <c:if test="${preTestSetupBean.numberOfRange == 3 }">
                                <tr>
                                    <td><input type="text" name="topScore_1" value="30"></td>
                                    <td><input type="text" name="description_1" value="below proficient"></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_2" value="60"></td>
                                    <td><input type="text" name="description_2" value="proficient"></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_3" value="100"></td>
                                    <td><input type="text" name="description_3" value="above proficient"></td>
                                </tr>
                            </c:if>
                            <c:if test="${preTestSetupBean.numberOfRange == 4 }">
                                <tr>
                                    <td><input type="text" name="topScore_1" value="25"></td>
                                    <td><input type="text" name="description_1" value="basic"></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_2" value="50"></td>
                                    <td><input type="text" name="description_2" value="below proficient"></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_3" value="75"></td>
                                    <td><input type="text" name="description_3" value="proficient"></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_4" value="100"></td>
                                    <td><input type="text" name="description_4" value="above proficient"></td>
                                </tr>
                            </c:if>
                            <c:if test="${preTestSetupBean.numberOfRange == 5 }">
                                <tr>
                                    <td><input type="text" name="topScore_1" value="20"></td>
                                    <td><input type="text" name="description_1" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_2" value="40"></td>
                                    <td><input type="text" name="description_2" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_3" value="60"></td>
                                    <td><input type="text" name="description_3" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_4" value="80"></td>
                                    <td><input type="text" name="description_4" value=""></td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="topScore_5" value="100"></td>
                                    <td><input type="text" name="description_5" value=""></td>
                                </tr>
                            </c:if>
                        </table>
                        <br/>
                        <input type="submit" value="next"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="mainUserPage.jsp" method="post">
                        <font color="red">
                            WARNING:Invalid range!
                            <br/>
                            Please enter number between 3 - 5
                        </font>
                        <br/>
                        <input type="submit" value="back"/>
                    </form>
                </c:otherwise>
            </c:choose>

        </div>
    </body>
</html>