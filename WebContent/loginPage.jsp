<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <title>Login</title>
    
    <body>
        <div id = "headerimage">
            <img src = "images/pinpoint-growth-web.png" alt = "Pinpoint Growth" width = "500">
        </div>

        <div id = "header">
            <header>
                <h1>Login</h1>
            </header>
        </div>
        
        <div id = "form">
            <form action="login.jsp" method="post">
                <input type="hidden" name="submitted" value="true">
                Username: <input type="text" name="username" value="<c:out value="${loginBean.username}"/>" /> 
                <br>
                Password: <input type="password" name="password" />
                <br> 
                <a href="newUserPage.jsp">Register</a>
                <c:if test="${param.submitted && !loginBean.usernamePasswordSuccess()}">
                    <br>
                    <font color="red"> Invalid username/password combination.Please try again or click <a href="newUserPage.jsp">here</a> to register an account.</font>
                    <br>
                </c:if>
                <input type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>