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
        <div id="finestra" title="Conferma?"></div>
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
                            alert(response);
                            window.location.href = "index.htm";
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                }
            }

            function validateForm(mail, pwd, name, surname, phone, addr) {

                var i=0;
                if (name === "") {
                    $('#name_err').show();
                    i++;
                } else {
                    $('#name_err').hide();
                }
                if (phone === "") {
                    $('#phone_err').show();
                    i++;
                } else {
                    $('#phone_err').hide();
                }
                if (surname === "") {
                    $('#surname_err').show();
                    i++;
                } else {
                    $('#surname_err').hide();                    
                }
                if (addr === "") {
                    $('#addr_err').show();
                    i++;
                }
                else {
                    $('#addr_err').hide();
                }
                if (mail === "") {
                    $('#email_err').show();
                    i++;
                } else {
                    $('#email_err').hide();                   
                }
                if (pwd === "") {
                    $('#pwd_err').show();
                    i++;
                } else {
                    $('#pwd_err').hide();                    
                }
                if(i===0){
                    return true;
                }else{
                    return false;
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
                <%@include file="../../resources/common/formReg.jsp" %>
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
