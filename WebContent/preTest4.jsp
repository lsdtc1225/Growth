<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.pinpointgrowth.traditional.PreTestRecord"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="preTest" scope="session" class="com.pinpointgrowth.traditional.PreTestRecord"></jsp:useBean>

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
            for (int i = 1 ; i < numberOfDes; i++){
                String cutPointStr = request.getParameter("cutpoint_"+i+""); 
                int cutPoint = Integer.parseInt(cutPointStr);
                preTest.insertCutPoint(cutPoint);
            }
        %>

        <form action="preTest5.jsp" method="post">
            <table border="1">
                <c:forEach var="i" begin="1" end="${preTest.getNumberOfDes()}" step="1" varStatus ="status">
                    <tr>
                        <td>range <c:out value="${i}"/> </td>
                        <td><input type="text" name="rangeDescription_${i}" /></td>
                        <input type="hidden" name="rangeDescription_${i}" value="1" size="1"/>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" value="go to next step" />
        </form>
    </body>

</html>