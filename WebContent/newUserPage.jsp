<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="schoolDistrictListBean" scope="request" class="com.pinpointgrowth.beans.SchoolDistrictListBean"/>

<html>

    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
        <script>
            $(function() {
                var availableTags = 
                [<c:forEach var="districtDTO" items="${schoolDistrictListBean.districtList}" varStatus="rowCounter">
                    "<c:out value="${districtDTO.districtName}" />"
                    <c:if test="${rowCounter.count < schoolDistrictListBean.districtList.size()}" >
                        <c:out value="," />
                    </c:if>
                </c:forEach>
                ];
                $( "#tags" ).autocomplete({
                    source: function( request, response ) {
                        var matches = $.map( availableTags, function(tag) {
                            if ( tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0 ) {
                                return tag;
                            }
                        });
                        response(matches);
                    },max: 5
                });
            });
        </script>
    </head>

    <title>New User</title>
    
    <script>
        function fillUserName(){
            document.getElementById("user").value = document.getElementById("email").value;
        }
    </script>
        
    <body>
        <div id = "headerimage">
            <img src = "images/pinpoint-growth-web.png" alt = "Pinpoint Growth" width = "500">
        </div>

        <jsp:useBean id="validationBean" scope="request" class="com.pinpointgrowth.beans.NewUserValidationBean"/>
        
        <div id = "header">
            <header>
                <h1>New User Registration</h1>
            </header>
        </div>
            
        <div id = "form">
            <form class="form" action="newUserValidation.jsp" method="POST">
                <input type="hidden" name="submitted" value="true" />
                First Name: <input type="text" name="firstName" value="<c:out value="${validationBean.firstName}" />">
                <c:if test="${param.submitted && !validationBean.firstNameValid()}">
                    <br>
                    <font color="red">Please enter your first name.</font>
                    <br>
                </c:if>
                <br>

                Last Name: <input type="text" name="lastName" value="<c:out value="${validationBean.lastName}" />">
                <c:if test="${param.submitted && !validationBean.lastNameValid()}">
                    <br>
                    <font color="red">Please enter your last name.</font>
                    <br>
                </c:if>
                <br>

                Email Address: <input type="text" id="email"  onblur="fillUserName()" name="emailAddress" value="<c:out value="${validationBean.emailAddress}" />">
                <c:if test="${param.submitted && !validationBean.emailAddressValid()}">
                    <br>
                    <font color="red">Please enter a valid email address.</font>
                    <br>
                </c:if>
                <br>

                <label for="tags">School District: </label>
                <input id="tags" name="schoolDistrict" value="<c:out value="${validationBean.schoolDistrict}" />"/>
                <c:if test="${param.submitted && !validationBean.schoolDistrictValid()}">
                    <br>
                    <font color="red">Please enter a valid school district.</font>
                    <br>
                </c:if>
                <br>

                District Key: <input type="text" name="key" value="<c:out value="${validationBean.key}" />">
                <c:if test="${param.submitted && !validationBean.keyValid()}">
                    <br>
                    <font color="red">District key does not match our records. Please enter a valid district key.</font>
                    <br>
                </c:if>
                <br>

                Username: <input type="text" id="user" name="username" value="<c:out value="${validationBean.username}" />">
                <c:if test="${param.submitted && !validationBean.usernameValid()}">
                    <br>
                    <font color="red">Please enter a username.</font>
                    <br>
                </c:if>
                <c:if test="${param.submitted && validationBean.usernameTaken()}">
                    <br>
                    <font color="red">Username taken. Please choose another username.</font>
                    <br>
                </c:if>
                <br>

                Password: <input type="password" name="password">
                <c:if test="${param.submitted && !validationBean.passwordValid()}">
                    <br>
                    <font color="red">Please enter a valid password. Passwords must have at least eigtht characters.<br>Passwords must contain at least one letter and one number.</font>
                    <br>
                </c:if>
                <br>

                Confirm Password: <input type="password" name="confirmPassword">
                <c:if test="${param.submitted && validationBean.passwordValid() && !validationBean.passwordsMatch()}">
                    <br>
                    <font color="red">Passwords do not match. Please try again.</font>
                    <br>
                </c:if>
                <br>
                Passwords must contain eight characters, one letter, and one number.
                <br>
                <input type="submit" value="Submit">
            </form>
        </div>
    </body>

</html>