<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

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
