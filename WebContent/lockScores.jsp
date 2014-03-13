<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:forward page="LockScores" >
    <jsp:param name="cID" value="${param.cID}" />
</jsp:forward>