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
            <h3>Create New Rubric</h3>
            <a href = "mainUserPage.jsp">Go Back to Courses</a>
        </div>

        <div id = "form">
            <h3>New Rubric</h3>
            <form action="rubricBasedSetup.jsp" method="post">
                <input type="hidden" name="numberofSLOSubmitted" value="true" />
                Name for this rubric: 
                <c:choose>
                    <c:when test="${!param.numberofSLOSubmitted}">
                        <input type="text" name="rubricName" value="Rubric 1" />
                        <br>
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="rubricName" value="${param.rubricName}" />
                        <br>
                    </c:otherwise>
                </c:choose>
                
                Number of learning objectives for this rubric: 
                <c:choose>
                    <c:when test="${!param.numberofSLOSubmitted}">
                        <input type="text" name="numberOfSLO" value="2" size="1" />
                        <br>
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="numberOfSLO" size="1" value="<c:out value="${param.numberOfSLO}" />"/>
                        <br>
                    </c:otherwise>
                </c:choose>
                <c:if test="${!param.numberofSLOSubmitted}">
                     Would you like to add weights to the objectives? 
                    <input type="radio" name="yesWeights" value="true" checked>yes
                    <input type="radio" name="yesWeights" value="false">no
                    <br>
                    <input type="submit" value="Next" /><br>
                </c:if>
            </form>
            <c:if test="${param.numberofSLOSubmitted}">
                <c:choose>
                    <c:when test="${param.yesWeights}">
                        <form action="CreateRubric" method="POST">
                            <input type="hidden" name="postScore" value="N" />
                            <input type="hidden" name="rubricName" value="${param.rubricName}" />
                            <input type="hidden" name="numberOfSLO" value="${param.numberOfSLO}" />
                            <table border="1">
                                <tr>
                                    <td>Objective Name</td>
                                    <td>Maximum Student Score</td>
                                    <td>Weight</td>
                                    <td>Description</td>
                                </tr>
                                <c:forEach var="i" begin="1" end="${param.numberOfSLO}" step="1" varStatus ="status">
                                    <tr>
                                        <td><input type="text" name="SLOName_${i}" /></td>
                                        <input type="hidden" name="SLOMinScore_${i}" value="1" size="1"/>
                                        <td>
                                            <select name="SLOMaxScore_${i}">
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5" selected="selected">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="SLOWeight_${i}">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                            </select>
                                        </td>
                                        <td>
                                            <textarea name="SLODesc_${i}" ROWS=3 COLS=30 ></textarea>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <br>
                            <input type="submit" value="Create Rubric" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="CreateRubric" method="POST">
                            <input type="hidden" name="postScore" value="N" />
                            <input type="hidden" name="rubricName" value="${param.rubricName}" />
                            <input type="hidden" name="numberOfSLO" value="${param.numberOfSLO}" />
                            <table border="1">
                                <tr>
                                    <td>Learning Objective Name</td>
                                    <td>Maximum Student Score</td>
                                    <td>Description</td>
                                </tr>
                                <c:forEach var="i" begin="1" end="${param.numberOfSLO}" step="1" varStatus ="status">
                                    <tr>
                                        <td><input type="text" name="SLOName_${i}" /></td>
                                        <input type="hidden" name="SLOMinScore_${i}" value="1" size="1"/>
                                        <td>
                                            <select name="SLOMaxScore_${i}">
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5" selected="selected">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                            </select>
                                        </td>
                                        <td>
                                            <textarea name="SLODesc_${i}" ROWS=3 COLS=30 ></textarea>
                                        </td>
                                    </tr>
                                    <input type="hidden" name="SLOWeight_${i}" value="1" />
                                </c:forEach>
                            </table>
                            <br>
                            <input type="submit" value="Create Rubric" />
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </body>
    
</html>