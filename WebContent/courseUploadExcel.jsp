<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


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
            <h3><a href = "mainUserPage.jsp">Go Back</a></h3>
        </div>
        <div id="form">
            <h3>Upload a file.</h3>
            <form action="ParseExcel" method="POST" enctype="multipart/form-data">
                <input type="file" name="filename"/>
                <br>
                <input type="submit" value="Upload" />
            </form>
        </div>
    </body>

</html>