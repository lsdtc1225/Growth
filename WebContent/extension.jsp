<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Extension Activity</title>
    </head>
  
    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "form">
            <h2>Would you like to <br>add an extension activity for your top tier?</h2>
            <form action="extension.jsp" method="post">
                <input type="hidden" name="submitted" value="true" />
                <input type="hidden" name="cID" value="<c:out value="${param.cID}"  />" />
                <input type="radio" name="extension" value="true" checked><b>Yes</b> 
                <input type="radio" name="extension" value="false"><b>No</b>
                <br>
                <br>
                <c:if test="${! param.submitted}">
                    <input type="submit" value="Next" />
                    <br>
                </c:if>
            </form>
         
            <c:if test="${param.submitted}">    
                <c:choose>
                    <c:when test="${param.extension}">
                        <form action="Extension" method="post">
                            <input type="hidden" name="cID" value="<c:out value="${param.cID}"  />" />
                            <b>Extension Activity Description</b>
                            <br>
                            <textarea name="description" rows="5" cols="25"></textarea>
                            <br>
                            <input type="submit" value="Submit" />
                        </form>
                    </c:when>
                    <c:otherwise>
                        <jsp:forward page="LockScores" >
                            <jsp:param name="cID" value="${param.cID}" />
                        </jsp:forward>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>

    </body>
    
</html>