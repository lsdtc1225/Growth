<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<sql:setDataSource var="dbSource" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<sql:query dataSource="${dbSource}" var="resultSet"> SELECT * FROM Pinpoint.PreTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/></sql:query>

<sql:query dataSource="${dbSource}" var="preTestResultSet"> SELECT * FROM Pinpoint.PreTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/></sql:query> 

<jsp:setProperty name="preTestSetupBean" property="numberOfRange" value="${preTestResultSet.rowCount}"/>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <title>Post-Test Setup</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top" />
        </div>

        <div id = "frontForm">
            <h3>POST-TEST SET UP</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id = "form">
            <c:choose>
                <c:when test="${preTestSetupBean.numberOfRange >= 3 && preTestSetupBean.numberOfRange <= 5}">
                    <form action="PostTest" method="post">
                        <table border="1">
                            <tr>
                                <th>Example</th><th>Top Score in range</th><th>Range Description</th>
                            </tr>

                            <c:if test="${preTestSetupBean.numberOfRange == 3 }">
                                <c:forEach  var="row" begin="0" end="0" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 30 this range will be 0-30</td>
                                        <td><input type="text" name="topScore_1" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_1" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="1" end="1" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 60 this range will be 31-60</td>
                                        <td><input type="text" name="topScore_2" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_2" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="2" end="2" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 100 this range will be 61-100</td>
                                        <td><input type="text" name="topScore_3" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_3" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            <c:if test="${preTestSetupBean.numberOfRange == 4 }">
                                <c:forEach  var="row" begin="0" end="0" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 25 this range will be 0-25</td>
                                        <td><input type="text" name="topScore_1" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_1" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="1" end="1" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 50 this range will be 26-50</td>
                                        <td><input type="text" name="topScore_2" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_2" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="2" end="2" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 75 this range will be 51-75</td>
                                        <td><input type="text" name="topScore_3" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_3" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="3" end="3" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 100 this range will be 76-100</td>
                                        <td><input type="text" name="topScore_4" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_4" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            
                            <c:if test="${preTestSetupBean.numberOfRange == 5 }">
                                <c:forEach  var="row" begin="0" end="0" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 20 this range will be 0-20</td>
                                        <td><input type="text" name="topScore_1" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_1" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="1" end="1" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 40 this range will be 21-40</td>
                                        <td><input type="text" name="topScore_2" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_2" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="2" end="2" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 60 this range will be 41-60</td>
                                        <td><input type="text" name="topScore_3" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_3" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="3" end="3" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 80 this range will be 61-80</td>
                                        <td><input type="text" name="topScore_4" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_4" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
                                <c:forEach  var="row" begin="4" end="4" items="${resultSet.rows}">
                                    <tr>
                                        <td>if 100 this range will be 81-100</td>
                                        <td><input type="text" name="topScore_5" value=<c:out value="${row.CutScore}"/>></td>
                                        <td><c:out value="${row.Description}"/></td>
                                        <input type="hidden" name="description_5" value="<c:out value="${row.Description}"/>"/>
                                    </tr>
                                </c:forEach>
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