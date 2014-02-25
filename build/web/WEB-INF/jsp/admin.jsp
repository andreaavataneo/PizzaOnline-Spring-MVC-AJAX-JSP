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
                $('#addP,#viewO').click(function() {

                    var name = $('#name').val();
                    var descr = $('#description').val();
                    var price = $('#price').val();

                    //if (validateForm(name, descr, price)) {

                    var data = $('#add').serialize();
                    var act = this.id;

                    $.ajax({
                        type: "POST",
                        url: act + "/action.htm",
                        data: data,
                        //data: data,
                        success: function(response) {
                            // we have the response
                            $('.main').replaceWith(response);
                            $('#top').html("Ecco il contenuto che hai richiesto!");
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                    // }
                });

                $('#delP,#modP').click(function() {


                    //var name = $('#nameNew').val();
                    //var descr = $('#descriptionNew').val();
                    //var price = $('#priceNew').val();

                    //if (validateForm(name, descr, price)) {

                    var id = $('#nameSel').val();
                    $.ajax({
                        type: "POST",
                        url: id + "/modify.htm",                        

                        success: function(response) {
                            // we have the response
                            $('.main').replaceWith(response);
                            $('#top').html("Ecco il contenuto che hai richiesto!");
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                    //  }
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
            <section>
                <p class="hello">${helloMessage}</p>                 
            </section>
            <section>
                <%@include file="../../resources/common/formAdmin.jsp" %>
            </section>
            <section>
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
