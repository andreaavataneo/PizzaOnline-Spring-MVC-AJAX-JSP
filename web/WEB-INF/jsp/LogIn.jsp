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
        <%@include file="../../resources/common/head.jsp" %>        
    </head>
    <body>
        <script type="text/javascript">
            function doAjaxPost() {
                // get the form values  
                var mail = $('#email').val();
                var pwd = $('#pwd').val();
                var data = $('form').serialize();

                if (validateForm(mail, pwd)) {
                    $.ajax({
                        type: "POST",
                        url: 'LogIn.htm',
                        data: data,
                        success: function(response) {
                            // we have the response  
                            $('#info').html(response);
                            $('#log').remove();
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
        <header>
            <%@include file="../../resources/common/header.html" %> 
        </header> 
        <article>
            <section>
                <p class="hello">${message}</p>
            </section>
            <section>
                <table id="log">   
                    <form:form commandName="user">  
                        <tr><td><p><label for=email>Email: </label></p></td><td><form:input path="email" id="email" type="text" value="email"/></td><td><p hidden="true" class="error" id="mail_err">Non hai inserito la mail!</p></td></tr>
                        <tr><td><p><label for=user>Password: </label> </p></td><td><form:input path="pwd" id="pwd" type="password" value="password"/><td><p hidden="true" class="error" id="pwd_err">Non hai inserito la password!</p></td></td></tr>
                        <tr><td><input class="button" type="button" onclick="doAjaxPost()" value="Accedi"><td><a class="button" href="addUser.htm">Iscriviti!</a></td></tr>
                    </form:form>
                </table>
                <p id="info"></p>
            </section>
        </article>
        <nav> 
            <%@include file="../../resources/common/menu.jsp" %>            
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>            
        </footer>
    </body>

</html>
