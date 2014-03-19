<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

	<c:forEach items = "${sessionScope}" var = "attr">
	${attr.key} = ${attr.value}
	<br/>
	</c:forEach>
	<br/>
	<c:forEach items = "${requestScope}" var = "attr">
	${attr.key} = ${attr.value}
	<br/>
	</c:forEach>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
		<title>Traditional Evaluation</title>
	</head>
	<body>
	    <h1>Evaluation Page !</h1>
	    <c:out value="${preBean.cID}"/>
	    <br/>
	    <c:out value="${preBean.cName}"/>
	    <br/>
	    <c:out value="${preBean.numberOfRange}"/>
	    <br/>
	    <c:out value="${testArrayList[0]}"/>
	    <br/>
	    <c:out value="${testArrayList[1]}"/>
	</body>
</html>