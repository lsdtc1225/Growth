<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="com.pinpointgrowth.constants.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<sql:setDataSource var="studentEnrolled" driver="<%=Constants.JDBC_DRIVER_CLASS%>" url="<%=Constants.DATABASE_URL%>" user="<%=Constants.DATABASE_USERNAME%>"  password="<%=Constants.DATABASE_PASSWORD%>"/>

<sql:query dataSource="${studentEnrolled}" var="resultSet">SELECT S.S_ID, SFName, SLName FROM Pinpoint.Enrolled E, Pinpoint.Student S WHERE E.S_ID = S.S_ID AND C_ID = <c:out value="${preTestSetupBean.cID}"/>
</sql:query> 

<sql:query dataSource="${studentEnrolled}" var="preTest"> SELECT * FROM Pinpoint.PreTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/></sql:query>
<sql:query dataSource="${studentEnrolled}" var="postTest"> SELECT * FROM Pinpoint.PostTest WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/></sql:query>
      
      
          
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
    <h1>Evaluation Page !</h1>
    
     
     
     <div id="form">

            <form action="PostTestScore" method="post">
                <table border="1">
                    <tr>
                        <th>Student FirstName</th> <th>Student LastName</th><th>pre-Test Score(0-100)</th> <th>post-Test Score(0-100)</th><th>growth</tr>
                    <c:forEach var="row" items="${resultSet.rows}">
                        <tr>
                            <td><c:out value="${row.SFName}"/></td>
                            <td><c:out value="${row.SLName}"/></td>
                            
							<sql:query dataSource="${studentEnrolled}" var="grade" > SELECT * FROM Pinpoint.StudentScore WHERE C_ID = <c:out value="${preTestSetupBean.cID}"/> AND S_ID = <c:out value="${row.S_ID}"/></sql:query>
                            <td>
                            <c:forEach var="pre" items="${grade.rows}">
							<c:out value="${pre.PreGradeTrad}"/>
							<c:set var="preScore" scope="session" value="${pre.PreGradeTrad}"/>
							
							</c:forEach>
                            </td>
                            
                            <td>
                            <c:forEach var="post" items="${grade.rows}">
							<c:out value="${post.PostGradeTrad}"/>
							<c:set var="postScore" scope="session" value="${post.PostGradeTrad}"/>
							</c:forEach>
                            </td>
                            
                            
                            <td>
                          
                           
                            </td>
                            
                            
                        </tr>
                    </c:forEach>
                </table>
                <br/>
             
            </form>
        </div>  
    
</body>
</html>