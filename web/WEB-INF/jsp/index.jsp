<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">

<html>
    <head>
       <%@include file="../../resources/common/header.jsp" %>
    </head>

    <body>
        <h1>Pizzeria</h1>
        <p>${helloMessage}</p>
        <article>            
            <form action="" method="POST"  onsubmit="return check(this);">
                <p>pagina di accoglienza</p>
        </article>
        <nav> 
            <%@include file="../../resources/common/menu.jsp" %>            
        </nav>
    </body>
</html>
