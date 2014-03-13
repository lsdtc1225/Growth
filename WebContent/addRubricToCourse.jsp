<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Rubric to Course</title>
    </head>


    <body>
        <jsp:useBean id="courseListRubricListBean" scope="request" class="com.pinpointgrowth.beans.CourseListRubricListBean" >
            <jsp:setProperty name="courseListRubricListBean" property="*" />
        </jsp:useBean>

        <form action="MyServlet" method="post">
            <c:forEach items="${courseListRubricListBean.courseList}" var="course" >
                <a href = "#" onClick="showRubrics(<c:out value="${course.courseID}" />)" ><c:out value="${course.courseName}" /></a>
                <br>
                <div id="${course.courseID}" style="display:none;">
                    <c:forEach items="${courseListRubricListBean.rubricList}" var="rubric" >
                        <c:out value="${rubric.rubricName}" />
                        <input type="checkbox" name="${course.courseID}_${rubric.rubricID}" />
                        <br>
                    </c:forEach>
                </div>
            </c:forEach>
            <input type="submit" value="Submit" />
        </form>
        
        <script>
    		function showRubrics(courseID){
        		document.getElementById(courseID).style.display = 'inline';
    		}
		</script>

    </body>

</html>