<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                ${menu}
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
