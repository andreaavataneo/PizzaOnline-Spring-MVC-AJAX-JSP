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

                var now = new Date();
                var month = (now.getMonth() + 1);
                var day = now.getDate();
                if (month < 10)
                    month = "0" + month;
                if (day < 10)
                    day = "0" + day;
                var today = now.getFullYear() + '-' + month + '-' + day;
                $('#dateo').val(today);

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

                $('.orderDel').submit(function(e) {
                    e.preventDefault();
                    var day = $(this).find('#datao').val();
                    var time = $(this).find('#hour_time').val();
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

                $('.orderCon').submit(function(e) {
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
            <section>
                ${order}
            </section>
            ${todayClientOrders}
            <section id="middle"></section>
            ${nextClientOrders}
        </article>
        <nav id="menu"> 
            ${menuType}            
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>
        </footer>
    </body>    
</html>

