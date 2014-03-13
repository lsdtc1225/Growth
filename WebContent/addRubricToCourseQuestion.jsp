<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Rubric to Course</title>
    </head>

    <body>
        <form action="PrepareCourseRubricListBean" method="post">
            <input type="hidden" name="questionSubmitted" value="true" />
            Would you like to attach one or more rubrics to your courses?
            <br>
            Yes <input type="radio" name="attachRubric" value="true" />
            <br>
            No <input type="radio" name="attachRubric" value="false" />
            <br>
            <input type="submit" value="Submit" />
        </form>
    </body>

</html>