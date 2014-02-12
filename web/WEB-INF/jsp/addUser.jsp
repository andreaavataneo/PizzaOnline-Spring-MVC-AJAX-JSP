<%-- 
    Document   : addUser
    Created on : 11-feb-2014, 17.43.43
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html">
<html>
    <head>
        <%@include file="../../resources/common/header.jsp" %>
    </head>
    <body>
        <script type="text/javascript">
         function doAjaxPost(){
             // get the form values  
	  var mail = $('#email').val();
          var pwd = $('#pwd').val();
          var name = $('#name');
          var addr = $('#addr');
	  
          if(mail===""){
              $('#email_error').html("Non hai inserito l'email!");
          }else if(name===""){
              $('#name_error').html("Non hai inserito il nome!");
          }else if(pwd===""){
              $('#pwd_error').html("Non hai inserito la password!");
          }else if(addr===""){
              $('#addr_error').html("Non hai inserito l'indirizzo!");
          }else{
        
	  $.ajax({  
	    type: "POST",  
	    url: "registrationConfirm.htm",  
	    data: "email="+mail+"&pwd="+pwd+"&name"+name+"&addr"+addr,  
	    success: function(response){  
	      // we have the response  
	      $('#info').html(response);	      
	    },  
	    error: function(e){  
	      alert('Error: ' + e);  
	    }  
	  });
          }
         }
        </script>
        <h1>Pizzeria</h1>
        <p>${message}</p>
        <article>            
            <table>   
                 <form:form commandName="user">
                    <tr><td><p><label for=name>Nome: </label></p></td><td><form:input path="name" id="name" type="text" value="Nome"/></td><td><p class="error" id="name_err"></p></td></tr>
                    <tr><td><p><label for=adress>Indirizzo: </label></p></td><td><form:input path="address" id="addr" type="text" value="Indirizzo"/><td><p class="error" id="addr_err"></p></td></td></tr>
                    <tr><td><p><label for=email>Email: </label></p></td><td><form:input path="email" id="email" type="email" value="Email"/></td><td><p class="error" id="email_err"></p></td></tr>
                    <tr><td><p><label for=user>Password: </label> </p></td><td><form:input path="pwd" id="pwd" type="password" value="password"/></td><td><p class="error" id="pwd_err"></p></td></tr>
                    <tr><td><input type="button" onClick="doAjaxPost();" value="Invia richiesta"></tr>
                 </form:form>
            </table>
                <p id="info"></p>
        </article>
        <nav> 
            <%@include file="../../resources/common/menu.jsp" %>            
        </nav>
    </body>
</html>
