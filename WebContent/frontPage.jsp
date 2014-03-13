<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<%--    <jsp:useBean id="loginBean" --%>
<%--        scope="request" --%>
<%--        class="com.pinpointgrowth.beans.LoginBean" --%>
<%--    /> --%>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Pinpoint Growth</title>
    </head>

    <body>
    
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>
    
        <div id = "frontForm">
            <form action="login.jsp" method="post">
                Username: <input type="text" name="username" value="<c:out value="${loginBean.username}"/>" /> 
                <br> 
                Password: <input type="password" name="password" /> 
                <br> 
                <a href="newUserPage.jsp">Register</a>
                <c:if test="${param.submitted && !loginBean.usernamePasswordSuccess()}">
                    <br>
                    <font color="red"> 
                        Invalid username/password combination. Please try again or click <a href="newUserPage.jsp">here</a> to register an account.
                    </font>
                    <br>
                </c:if>
                <input type="submit" value="Submit">
            </form>
        </div>


        <div id = "colorwheel">
            <img src="images/colorwheel.png" height = "300" width = "300" usemap="#colorwheelmap">
            
            <map name="colorwheelmap">
                <area shape="poly" coords="155,147,299,103,151,3" alt="info" href="info.jsp">
                <area shape="poly" coords="148,147,148,3,11,102" alt="tutorials" href="tutorials.jsp">
                <area shape="poly" coords="147,150,10,105,62,266" alt="settings" href=
                    "<c:choose>
                        <c:when test="${loginBean.username} == NULL">
                            login.jsp
                        </c:when>
                        <c:otherwise>
                            settings.jsp
                        </c:otherwise>
                    </c:choose>"
                >
                <area shape="poly" coords="152,150,289,106,238,266" alt="courses" href=
                    "<c:choose>
                        <c:when test="${loginBean.username} == NULL">
                            login.jsp
                        </c:when>
                        <c:otherwise>
                            mainUserPage.jsp
                        </c:otherwise>
                    </c:choose>"
                >
                
                <area shape="poly" coords="149,152,65,269,234,269" alt="addCourses" href=
                    "<c:choose>
                        <c:when test="${loginBean.username} == NULL">
                            login.jsp
                        </c:when>
                        <c:otherwise>
                            addCoursePage.jsp
                        </c:otherwise>
                    </c:choose>"
                >
            </map>
        </div>

</body>

</html>