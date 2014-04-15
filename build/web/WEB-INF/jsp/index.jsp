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
            <section class="bordGreen">
                <p>${helloMessage}</p>
            </section>
            
                ${menu}
            
        </article>
        <nav id="menu"> 
            <%@include file="../../resources/common/user_box.jsp" %>  
            ${menuType} 
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>
        </footer>
    </body>   
</html>
