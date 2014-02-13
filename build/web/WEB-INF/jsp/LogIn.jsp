<%-- 
    Document   : LogIn
    Created on : 9-feb-2014, 15.00.31
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html">
<html>
    <head>
        <%@include file="../../resources/common/header.jsp" %>       
    </head>
    <body>
        <script type="text/javascript">
            function doAjaxPost() {
                // get the form values  
                var mail = $('#email').val();
                var pwd = $('#pwd').val();

                if (validateForm(mail, pwd)) {
                    $.ajax({
                        type: "POST",
                        url: 'LogIn.htm',
                        data: "email=" + mail + "&pwd=" + pwd,
                        success: function(response) {
                            // we have the response  
                            $('#info').html(response);
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                }
            }

            function validateForm(mail, pwd) {
                if (mail === "") {
                    $('#mail_err').show();
                    return false;
                } else if (pwd === "") {
                    $('#pwd_err').show();
                    return false;
                } else {
                    $('#mail_err').hide();
                    $('#pwd_err').hide();
                    return true;
                }
            }
        </script>       
        <h1>Pizzeria</h1>
        <article>    
            <table>   
                <form:form commandName="user">  
                    <tr><td><p><label for=email>Email: </label></p></td><td><form:input path="email" id="email" type="text" value="email"/></td><td><p hidden="true" class="error" id="mail_err">Non hai inserito la mail!</p></td></tr>
                    <tr><td><p><label for=user>Password: </label> </p></td><td><form:input path="pwd" id="pwd" type="password" value="password"/><td><p hidden="true" class="error" id="pwd_err">Non hai inserito la password!</p></td></td></tr>
                    <tr><td><input type="button" onclick="doAjaxPost()" value="Accedi"><td><a href="addUser.htm">Iscriviti!</a></td></tr>
                        </form:form>
            </table>
            <p id="info"></p>
        </article>
        <nav> 
            <%@include file="../../resources/common/menu.jsp" %>            
        </nav>
    </body>
</html>
