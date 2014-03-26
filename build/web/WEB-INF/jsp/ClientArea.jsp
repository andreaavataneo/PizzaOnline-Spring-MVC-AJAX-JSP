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
                    $.ajax({
                        type: 'POST',
                        url: variabili + "/createOrd.htm",
                        success: function(response) {
                            $('#finestra').html(response);
                            $('#finestra').dialog({
                                modal: true,
                                width:350,
                                buttons: {
                                    "Ok": function() {
                                        $(this).dialog("close");
                                        location.reload();
                                    }
                                }
                            });
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                });

                $('.orderDel').click(function(e) {
                    e.preventDefault();
                    var day = $(this).find('#datao').val();
                    var time = $(this).find('#hour_time').val();
                    var questo = $(this);
                    $.ajax({
                        type: 'POST',
                        url: day + "/" + time + "/delOrd.htm",
                        success: function() {
                            $('#finestra3').html("Ordine annulato ...");
                            $('#finestra3').dialog({
                                modal: true,
                                width: 350,
                                buttons: {
                                    "Ok": function() {
                                        $(this).dialog("close");
                                        location.reload();
                                    }
                                }
                            });
                        },
                        error: function(e) {
                            alert('Error: ' + e);
                        }
                    });
                });

                $('.orderCon').click(function(e) {
                    e.preventDefault();
                    var day = $(this).find('#datao').val();
                    var time = $(this).find('#hour_time').val();

                    $.ajax({
                        type: 'POST',
                        url: day + "/" + time + "/conOrd.htm",
                        success: function() {
                            $('#finestra2').html("Consegna effettuata con successo!");
                            $('#finestra2').dialog({
                                modal: true,
                                width: 350,
                                buttons: {
                                    "Ok": function() {
                                        $(this).dialog("close");
                                        location.reload();
                                    }
                                }
                            });
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
        <div id="finestra" title="Ordinazione"></div>
        <div id="finestra2" title="Consegna"></div>
        <div id="finestra3" title="Annullamento"></div>
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
            <section id='todayOrd'>
                ${todayClientOrders}
            </section>
            <section id='nextOrd'>
                ${nextClientOrders}
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

