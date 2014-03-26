<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <script language="javascript" type="text/javascript">
            function getConfirmation(){
                var returnValue = confirm("Are you sure to delete this course ?");
                if(returnValue == true){
                    return true;
                }else{
                    return false;
                }
            }
        </script>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>
                User Name: <c:out value = "${loginInfo.username }" />
                <br/>
                Course Name: <c:out value="${courseBean.courseDTO.courseName}" />
                <br/>
                Course Term: <c:out value="${courseBean.courseDTO.term}" />
            </h3>
            <a href = "mainUserPage.jsp">Go Back</a>
        </div>

        <h3><a href="ViewCourse?cID=<c:out value="${courseBean.courseDTO.courseID}" /> "> Performance based</a></h3>
        <h3><a href="ViewCourseTraditional?cID=<c:out value="${courseBean.courseDTO.courseID}" /> ">Traditional based</a></h3>
        <h3><a href="CourseDelete?cID=<c:out value="${courseBean.courseDTO.courseID}" />" onclick="return getConfirmation()">Remove This Course</a></h3>
    </body>
</html>