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
        <script type="text/javascript">
            $(document).ready(function() {

                $('.orderSend').click(function(e) {
                    e.preventDefault();
                    var day = $(this).find('#datao').val();
                    var time = $(this).find('#hour_time').val();

                    $.ajax({
                        type: 'POST',
                        url: day + "/" + time + "/orderSend.htm",
                        success: function() {
                            // we have the response
                            $('#finestra').html("Fattorino in viaggio!");
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
            });
        </script>
    </head>
    <body>
        <div id="finestra" title="Consegna in corso ..."></div>
        
                
        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>
            ${todayTask}
            <section id="middle"></section>
            ${nextTask}
        </article>
        <nav id="menu"> 
            ${menuType}            
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>
        </footer>
    </body>
</html>
