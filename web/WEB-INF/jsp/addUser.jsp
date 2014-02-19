<%-- 
    Document   : addUser
    Created on : 11-feb-2014, 17.43.43
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
                var name = $('#name').val();
                var addr = $('#addr').val();
                var surname = $('#surname').val();
                var phone = $('#phone').val();
                var data = $('form').serialize();

                if (validateForm(mail, pwd, name, surname, phone, addr)) {
                    $.ajax({
                        type: "POST",
                        url: "addUser.htm",
                        data: data,
                        success: function(response) {
                            // we have the response  
                            $('#info').html(response);
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                }
            }

            function validateForm(mail, pwd, name, surname, phone, addr) {
                if (name === "") {
                    $('#name_err').show();
                    return false;
                } if (phone === "") {
                    $('#phone_err').show();
                    return false;
                } if (surname === "") {
                    $('#surname_err').show();
                    return false;
                } if (addr === "") {
                    $('#addr_err').show();
                    return false;
                } else if (mail === "") {
                    $('#email_err').show();
                    return false;
                } if (pwd === "") {
                    $('#pwd_err').show();
                    return false;
                } else {
                    $('#email_err').hide();
                    $('#pwd_err').hide();
                    $('#name_err').hide();
                    $('#addr_err').hide();
                    $('#phone_err').hide();
                    $('surname_err').hide();
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
                <table>   
                    <form:form commandName="user">
                        <tr><td><p><label for=name>Nome: </label></p></td><td><form:input path="name" id="name" type="text" value="Nome"/></td><td><p hidden="true" class="error" id="name_err">Non hai inserito il nome!</p></td></tr>
                        <tr><td><p><label for=surname>Cognome: </label></p></td><td><form:input path="surname" id="surname" type="text" value="Cognome"/></td><td><p hidden="true" class="error" id="surname_err">Non hai inserito il cognome!</p></td></tr>
                        <tr><td><p><label for=pwd>Password: </label> </p></td><td><form:input path="pwd" id="pwd" type="password" value="password"/></td><td><p hidden="true" class="error" id="pwd_err">Non hai inserto la password!</p></td></tr>
                        <tr><td><p><label for=phone>Nome: </label></p></td><td><form:input path="phone" id="phone" type="text" value="Telefono"/></td><td><p hidden="true" class="error" id="phone_err">Non hai inserito il numero di telefono!</p></td></tr>
                        <tr><td><p><label for=address>Indirizzo: </label></p></td><td><form:input path="address" id="addr" type="text" value="Indirizzo"/><td><p hidden="true" class="error" id="addr_err">Non hai inserito l'indirizzo!</p></td></td></tr>
                        <tr><td><p><label for=email>Email: </label></p></td><td><form:input path="email" id="email" type="email" value="Email"/></td><td><p hidden="true" class="error" id="email_err">Non hai inserito l'email!</p></td></tr>
                        <tr><td><input class="button" type="button" onClick="doAjaxPost();" value="Invia richiesta"></tr>
                            </form:form>
                </table>
                <p id="info"></p>
            </section>
        </article>
        <nav id="menu"> 
            ${menuType}            
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>
        </footer>
    </body>

</html>
