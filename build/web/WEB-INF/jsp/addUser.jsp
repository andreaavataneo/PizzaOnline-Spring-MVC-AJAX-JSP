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
                }
                if (phone === "") {
                    $('#phone_err').show();
                    return false;
                }
                if (surname === "") {
                    $('#surname_err').show();
                    return false;
                }
                if (addr === "") {
                    $('#addr_err').show();
                    return false;
                } else if (mail === "") {
                    $('#email_err').show();
                    return false;
                }
                if (pwd === "") {
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
                <form:form commandName="user">
                    <fieldset>
                        <legend>Dati Utente</legend>
                        <ul type="circle">
                            <li><label for=name>Nome: </label><form:input path="name" id="name" type="text" value="Nome"/><p hidden="true" class="error" id="name_err">Non hai inserito il nome!</p></li>
                            <li><label for=surname>Cognome: </label><form:input path="surname" id="surname" type="text" value="Cognome"/><p hidden="true" class="error" id="surname_err">Non hai inserito il cognome!</p></li>
                            <li><label for=pwd>Password: </label><form:input path="pwd" id="pwd" type="password" value="password"/><p hidden="true" class="error" id="pwd_err">Non hai inserto la password!</p></li>
                            <li><label for=phone>Nome: </label><form:input path="phone" id="phone" type="text" value="Telefono"/><p hidden="true" class="error" id="phone_err">Non hai inserito il numero di telefono!</p></li>
                            <li><label for=address>Indirizzo: </label><form:input path="address" id="addr" type="text" value="Indirizzo"/><p hidden="true" class="error" id="addr_err">Non hai inserito l'indirizzo!</p></li>
                            <li><label for=email>Email: </label><form:input path="email" id="email" type="email" value="Email"/><p hidden="true" class="error" id="email_err">Non hai inserito l'email!</p></li>
                        </ul>
                    </fieldset>
                    <fieldset>
                        <input class="button" type="button" onClick="doAjaxPost();" value="Invia richiesta">
                    </fieldset>
                </form:form>
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
