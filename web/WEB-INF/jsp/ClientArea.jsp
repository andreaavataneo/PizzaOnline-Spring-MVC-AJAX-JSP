<%-- 
    Document   : ClientArea
    Created on : 17-feb-2014, 17.48.58
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

            $(document).ready(function() {
                $('#newOrder').submit(function(e) {
                    e.preventDefault();
                    var variabili = $('#newOrder').serialize();
                    //alert(variabili);

                    $.ajax({
                        type: 'POST',
                        url: variabili + "/createOrd.htm",
                        success: function(response) {
                            // we have the response          
                            alert(response);
                            location.reload();
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                });

                $('.orderM').click(function(e) {
                    e.preventDefault();
                    var day = $(this).find('#datao').val();
                    var time = $(this).find('#hour_time').val();

                    $.ajax({
                        type: 'POST',
                        url: day + "/" + time + "/delOrd.htm",
                        success: function() {
                            // we have the response
                            alert("Ordine eliminato con successo!");
                            location.reload();
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>
            <!--
            <section>
              <p>${message}</p>
            </section>
            !-->
            <section>
                ${order}
                <p id="info"></p>
            </section>
            <section id='ordiniC'>
                ${ClientOrders}
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

