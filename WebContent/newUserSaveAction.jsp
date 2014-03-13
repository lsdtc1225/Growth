<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="validationBean" scope="request" class="com.pinpointgrowth.beans.NewUserValidationBean">
    <jsp:setProperty name="validationBean" property="*" />      
</jsp:useBean>

<jsp:useBean id="newUserSaveBean" scope="page" class="com.pinpointgrowth.beans.NewUserSaveActionBean"></jsp:useBean>
<jsp:setProperty name="newUserSaveBean" property="firstName" value="${validationBean.firstName}" />
<jsp:setProperty name="newUserSaveBean" property="lastName" value="${validationBean.lastName}" />
<jsp:setProperty name="newUserSaveBean" property="emailAddress" value="${validationBean.emailAddress}" />
<jsp:setProperty name="newUserSaveBean" property="schoolDistrict" value="${validationBean.schoolDistrict}" />
<jsp:setProperty name="newUserSaveBean" property="username" value="${validationBean.username}" />
<jsp:setProperty name="newUserSaveBean" property="password" value="${validationBean.password}" />

<c:choose>
    <c:when test="${newUserSaveBean.saveNewUserData()}">
        <jsp:forward page="mainUserPage.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="errorPage.jsp" />
    </c:otherwise>
</c:choose>