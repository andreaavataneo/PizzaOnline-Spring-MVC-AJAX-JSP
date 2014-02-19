<%-- 
    Document   : footer
    Created on : 14-feb-2014, 21.17.09
    Author     : Andrea
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p>footer con informazioni di sessione. Utente loggato: <p id="session">${user.getEmail()}</p></p>
<p><a href="<c:url value="LogOut.htm"/>">LogOut</a></p>

