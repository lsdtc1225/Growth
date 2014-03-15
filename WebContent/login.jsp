<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="loginBean" scope="request" class="com.pinpointgrowth.beans.LoginBean">
    <jsp:setProperty name="loginBean" property="*" />      
</jsp:useBean>

<c:choose>
    <c:when test="${loginBean.usernamePasswordSuccess()}">
        <jsp:useBean id="loginInfo" scope="session" class="com.pinpointgrowth.beans.LoginBean" />
        <jsp:setProperty name="loginInfo" property="username" value="${loginBean.username}" />
        <jsp:forward page="mainUserPage.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="frontPage.jsp" />
    </c:otherwise>
</c:choose>