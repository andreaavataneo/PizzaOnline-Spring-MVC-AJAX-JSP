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
                            if (response.toString() === "failed") {
                                $('#info').html("Nome utente o password errati!").fadeIn("slow");
                            } else if (response.toString() === "admin") {
                                $('#log').fadeOut("slow");
                                $('#menu').fadeOut("slow").load('<c:url value="/resources/common/adminMenu.jsp"/>').fadeIn("slow");
                                $('#info').html("Benvenuto nell'area amministratore! "+mail).fadeIn("slow");
                            } else {
                                $('#log').fadeOut("slow");
                                $('#menu').fadeOut("slow").load('<c:url value="/resources/common/clientMenu.jsp"/>').fadeIn("slow");
                                $('#info').html("Benvenuto nell'area clienti! "+mail).fadeIn("slow");
                            }
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
                }
                if (pwd === "") {
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
                <%@include file="../../resources/common/formLog.html" %>
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
