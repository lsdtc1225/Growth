<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Course</title>
    </head>

    <body>
        <div id = "frontheader">
            <img src="images/pinpoint-growth-web.png" align="top">
        </div>

        <div id = "frontForm">
            <h3>
                <a href = "mainUserPage.jsp">Go Back</a>
            </h3>
        </div>

        <div id = "form">
            <h3>Add a new course.</h3>
            <jsp:useBean id="addCourseManualBean" scope="request" class="com.pinpointgrowth.beans.AddCourseManualBean" >
                <jsp:setProperty name="addCourseManualBean" property="*" />
            </jsp:useBean>

            <c:if test="${param.numberofStudentsSubmitted}">
                <jsp:useBean id="sessionAddCourseManualBean" scope="session" class="com.pinpointgrowth.beans.AddCourseManualBean" ></jsp:useBean>
                <jsp:setProperty name="sessionAddCourseManualBean" property="courseName" value="${addCourseManualBean.courseName}"/>
                <jsp:setProperty name="sessionAddCourseManualBean" property="courseTerm" value="${addCourseManualBean.courseTerm}"/>
                <jsp:setProperty name="sessionAddCourseManualBean" property="courseRoom" value="${addCourseManualBean.courseRoom}"/>
                <jsp:setProperty name="sessionAddCourseManualBean" property="numberOfStudents" value="${addCourseManualBean.numberOfStudents}"/>
            </c:if>


            <form action="courseAddManual.jsp" method="POST">
                <input type="hidden" name ="numberofStudentsSubmitted" value="true" />
                Course Name: <input type="text" name="courseName" value="<c:out value="${addCourseManualBean.courseName}" />" />
                <br>
                Course Term: <input type="text" name="courseTerm" value="<c:out value="${addCourseManualBean.courseTerm}" />" />
                <br>
                Course Length: 
                <select name="courseRoom">
                    <c:forEach items="${addCourseManualBean.lengthOptions}" var="option" varStatus ="status">
                        <option value="<c:out value="${option}" />"><c:out value="${option}" /></option>
                    </c:forEach>
                </select>
                <br>
                Number of students: <input type="text" name="numberOfStudents" size="1" value="<c:out value="${addCourseManualBean.numberOfStudents}" />"/>
                <br>
                <c:if test="${!param.numberofStudentsSubmitted}">
                    <input type="submit" value="Submit" />
                    <br>
                </c:if>
            </form>
          
            <c:if test="${param.numberofStudentsSubmitted}">
                <form action="CourseAddManual" method="POST">
                    <table border="1">
                        <tr>
                            <td>First Name</td>
                            <td>Last Name</td>
                            <td>Grade</td>
                        </tr>
                        <c:forEach var="i" begin="1" end="${addCourseManualBean.numberOfStudents}" step="1" varStatus ="status">
                            <tr>
                                <td><input type="text" name="studentFirstName_${i}" /></td>
                                <td><input type="text" name="studentLastName_${i}" /></td>
                                <td><input type="text" name="studentGrade_${i}" size="1" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <br>
                    <input type="submit" value="Add Course" />
                </form>
            </c:if>
        </div>
    </body>
</html>