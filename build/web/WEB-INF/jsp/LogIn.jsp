<%-- 
    Document   : LogIn
    Created on : 9-feb-2014, 15.00.31
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../../resources/common/head.jsp" %>   
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
                            if (response.toString() === "failed") {
                                //$('#info').html("Nome utente o password errati!").fadeIn("slow");
                                $('#finestra').html("Nome utente o password errati!");
                                $('#finestra').dialog({
                                    modal: true,
                                    width: 350,
                                    buttons: {
                                        "Ok": function() {
                                            $(this).dialog("close");
                                            location.reload();
                                        }
                                    }
                                });
                            } else if (response.toString() === "admin") {
                                $('#log').fadeOut("slow");
                                $('#menu').fadeOut("slow").load('<c:url value="/resources/common/adminMenu.jsp"/>').fadeIn("slow");
                                $('#info').html("<p>Benvenuto nell'area amministratore! " + mail
                                        + "<br>Utilizzando il men&ugrave; alla tua sinistra potrai accedere alle "
                                        + "funzionalit&agrave; del sito.</p>").fadeIn("slow");
                                $('#info').show();
                            } else {
                                $('#log').fadeOut("slow");
                                $('#menu').fadeOut("slow").load('<c:url value="/resources/common/clientMenu.jsp"/>').fadeIn("slow");
                                $('#info').html("<p>Benvenuto nell'area clienti! " + mail
                                        + "<br>Utilizzando il men&ugrave; alla tua sinistra potrai accedere alle "
                                        + "funzionalit&agrave; del sito.</p>").fadeIn("slow");
                                $('#info').show();
                            }
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                }
            }

            function validateForm(mail, pwd) {
                var i = 0;
                if (mail === "") {
                    $('#mail_err').show();
                    i++;
                } else {
                    $('#mail_err').hide();
                }
                if (pwd === "") {
                    $('#pwd_err').show();
                    i++;
                } else {
                    $('#pwd_err').hide();
                }
                if (i === 0) {
                    return true;
                } else {
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div id="finestra" title="Credenziali errate..."></div>
        <header>
            <%@include file="../../resources/common/header.html" %> 
        </header> 
        <article>
            <section>
                <%@include file="../../resources/common/formLog.html" %>
                <section class="bordGreen" id="info" hidden="true"></section>                
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
