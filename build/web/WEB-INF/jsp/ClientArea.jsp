<%-- 
    Document   : ClientArea
    Created on : 17-feb-2014, 17.48.58
    Author     : Andrea
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="../../resources/common/head.jsp" %>
    </head>
    <body>
        <script type="text/javascript">

            $(document).ready(function() {
                $('.button').click(function() {

                    var idP = this.id;
                    var qty = $('#qty').val();


                });
            });

            function doAjaxPost() {
                $.ajax({
                    type: "POST",
                    url: 'ClientArea.htm',
                    data: data,
                    success: function(response) {
                        // we have the response                             
                        $('#info').html(response);
                    },
                    error: function(e) {
                        alert('Error: ' + e);
                    }
                });
            }

        </script>
        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>
            <section>
                <p>${message}</p>
            </section>
            <section>
                ${order}
                <p id="info"></p>
            </section>
            <section>
                ${provaOrdini}
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

