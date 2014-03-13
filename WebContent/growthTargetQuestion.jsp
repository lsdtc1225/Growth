<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
            <h3>Course Name: <c:out value="${courseBean.courseDTO.courseName}" /></h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <div id = "form">
            <h2>
                Would you like to 
                <br>tier your students?
            </h2>
            <form action="growthTargetQuestion.jsp" method="post">
                <input type="hidden" name="tierSubmitted" value="true" />
                <input type="hidden" name="cID" value="<c:out value="${param.cID}"  />" />
                <input type="hidden" name="update" value="false" />
                <input type="radio" name="tier" value="true" checked><b>Yes</b> 
                <input type="radio" name="tier" value="false"><b>No</b>
                <br>
                <br>
                <c:if test="${! param.tierSubmitted}">
                    <input type="submit" value="Next" />
                    <br>
                </c:if>
            </form>
             
            <c:if test="${param.tierSubmitted}">    
                <c:choose>
                    <c:when test="${param.tier}">
                        <form action="Tier" method="post">
                            <input type="hidden" name="tier" value="true" />
                            <input type="hidden" name="cID" value="<c:out value="${param.cID}"  />" />
                            <input type="hidden" name="update" value="false" />
                            <b>How many tiers?</b>
                            <select name="numberOfTiers">
                                <option value="2">2</option>
                                <option value="3" selected="selected">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                            </select>
                            <br>
                            <input type="submit" value="Next" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <jsp:forward page="Tier?tier=false" />
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </body>
</html>