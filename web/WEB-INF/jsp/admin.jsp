<%-- 
    Document   : admin
    Created on : 19-feb-2014, 9.17.34
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../../resources/common/head.jsp" %>
    </head>
    <body>
        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>
            <section>
                <p class="hello">${helloMessage}</p>                 
            </section>
            <section>
               
            </section>
        </article>
        <nav id="menu"> 
            ${menuType}            
        </nav>
    </body>
    <footer>
        <%@include file="../../resources/common/footer.jsp" %>
    </footer>
</html>
