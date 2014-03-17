<%-- 
    Document   : admin
    Created on : 19-feb-2014, 9.17.34
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

            $(document).ready(function() {

                $('#idSel').click(function() {

                    var id = this.value;

                    $.ajax({
                        type: "POST",
                        url: id + ".htm",
                        success: function(response) {
                            // we have the response
                            var split = response.split("%");

                            $('#nameP').val(split[0]);
                            $('#description').val(split[1]);
                            $('#price').val(split[2]);
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                });


                $('#aggiungere,#modificare,#cancellare').click(function() {

                    var name = $('#nameP').val();
                    var descr = $('#description').val();
                    var price = $('#price').val();
                    var id_p = $('#idSel').val();
                    var conf = window.confirm("Sei sicuro di volere "+this.id+" la pizza?");

                    if (validateForm(name, descr, price)&&conf===true) {                     

                        var data = $('#formAddP').serialize();
                        var act = this.id;

                        $.ajax({
                            type: "POST",
                            url: act + "/" + id_p + "/modify.htm",
                            data: data,
                            success: function(response) {
                                // we have the response
                                $('.main').replaceWith(response);
                                $('#formAdm').reload();
                                $('#top').html("Ecco il contenuto che hai richiesto!");
                            },
                            error: function(e) {
                                alert("Error: " + e);
                            }
                        });
                    }
                });

                $('#viewO').click(function() {

                    $.ajax({
                        type: "POST",
                        url: "viewO.htm",
                        success: function(response) {
                            // we have the response
                            $('.main').replaceWith(response);
                            $('#top').html("Ecco il contenuto che hai richiesto!");
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                });
            });

            function validateForm(name, descr, price) {
                if (name === "") {
                    $('#name_err').show();
                    return false;
                }
                if (descr === "") {
                    $('#descr_err').show();
                    return false;
                }
                if (price == 0) {
                    $('#price_err').show();
                    return false;
                } else {
                    $('#name_err').hide();
                    $('#descr_err').hide();
                    $('#price_err').hide();
                    return true;
                }
            }
        </script>        
        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>
            
                <%@include file="../../resources/common/formAdmin.jsp" %>

            <section>
                <p hidden="true" class="error" id="name_err">Non hai inserito il nome della pizza!</p>
                <p hidden="true" class="error" id="descr_err">Non hai inserito la ricetta della pizza!</p>
                <p hidden="true" class="error" id="price_err">Non hai inserito il prezzo della pizza!</p>  
                <p id="top"></p>
                <table class="main"></table>                 
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
