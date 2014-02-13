<%-- 
    Document   : Catalog
    Created on : 9-feb-2014, 12.21.06
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
       <%@include file="../../resources/common/header.jsp" %>
    </head>
    <body>
        <h1>Pizzeria</h1>      
        <article>            
            ${menu}
        </article>
        <nav> 
            <%@include file="../../resources/common/menu.jsp" %>            
        </nav>
    </body>
</html>
