<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="preTestSetupBean" scope="session" class="com.pinpointgrowth.traditionalBeans.PreTestSetupBean">
    <jsp:setProperty name="preTestSetupBean" property="*"/>
</jsp:useBean>
<jsp:setProperty name="preTestSetupBean" property="cID" value="${param.cID}"/>
<jsp:setProperty name="preTestSetupBean" property="cName" value="${param.cName}"/>

<sql:setDataSource var="testRecord" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<sql:query dataSource="${testRecord}" var="preTestResultSet">SELECT * FROM Pinpoint.PreTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/>;</sql:query> 

<sql:query dataSource="${testRecord}" var="postTestResultSet">SELECT * FROM Pinpoint.PostTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/>;</sql:query>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>
                User Name: <c:out value = "${loginInfo.username}"/>
                <br/>
                Course Name: <c:out value="${courseBean.courseDTO.courseName}" />
                <br/>
                Course Term: <c:out value="${courseBean.courseDTO.term}" />
            </h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <div>
                <h1>TraditionalBased</h1>
        </div>

        <div>
            <h3>
	            <a href="preTest1.jsp?cID=<c:out value="${param.cID}"/>&cName=<c:out value="${courseBean.courseDTO.courseName}"/>">Setup Pre-Test</a>
	            <br/>
            </h3>
        </div>
        
        <c:if test="${preTestResultSet.rowCount!=0}">
            <div>
                <h3>
                    <a href = "postTest2.jsp?cID=<c:out value="${param.cID}"/>&cName=<c:out value="${courseBean.courseDTO.courseName}"/>">Setup Post-Test</a>
                    <br/>
                </h3>
            </div>
        </c:if>
        <br/>
        <c:if test="${(preTestResultSet.rowCount!=0) && (postTestResultSet.rowCount==0)}">
            <div>
                <h3>
                    <a href="traditionalAddStudent.jsp?cID=<c:out value="${param.cID}"/>">Add Student</a>
                </h3>
            </div>
        </c:if>

        <c:if test="${param.SE}">
            <jsp:forward page="TraditionalEvaluation" />
        </c:if>
    </body>
</html>