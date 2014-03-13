<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="validationBean" scope="request" class="com.pinpointgrowth.beans.NewUserValidationBean">
    <jsp:setProperty name="validationBean" property="*" />      
</jsp:useBean>

<c:choose>
    <c:when test="${validationBean.allFieldsValid()}">
        <jsp:useBean id="loginInfo" scope="session" class="com.pinpointgrowth.beans.LoginBean" />
        <jsp:setProperty name="loginInfo" property="username" value="${validationBean.username}" />
        <jsp:forward page="newUserSaveAction.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="newUserPage.jsp" />
    </c:otherwise>
</c:choose>

