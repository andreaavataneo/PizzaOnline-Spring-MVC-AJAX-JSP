<%-- 
    Document   : regForm
    Created on : 21-feb-2014, 11.41.29
    Author     : Andrea
--%>

<form:form commandName="user">
    <fieldset>
        <legend>Dati Utente</legend>
        <dl>
            <dt><label for=name>Nome: </label></dt>
            <dd><form:input path="name" id="name" type="text" value="Nome" required="on"/></dd>
            <dt><label for=surname>Cognome: </label></dt>
            <dd><form:input path="surname" id="surname" type="text" value="Cognome" required="on"/></dd>
            <dt><label for=pwd>Password: </label></dt>
            <dd><form:input path="pwd" id="pwd" type="password" value="password" required="on"/></dd>
            <dt><label for=phone>Nome: </label></dt>
            <dd><form:input path="phone" id="phone" type="text" value="Telefono" required="on"/></dd>
            <dt><label for=address>Indirizzo: </label></dt>
            <dd><form:input path="address" id="addr" type="text" value="Indirizzo" required="on"/></dd>
            <dt><label for=email>Email: </label></dt>
            <dd><form:input path="email" id="email" type="email" value="Email" required="on"/></dd>
        </dl>
    </fieldset>
    <fieldset>
        <input class="button" type="button" onClick="doAjaxPost();" value="Invia richiesta">
        <p hidden="true" class="error" id="name_err">Non hai inserito il nome!</p>
        <p hidden="true" class="error" id="surname_err">Non hai inserito il cognome!</p>
        <p hidden="true" class="error" id="pwd_err">Non hai inserto la password!</p>
        <p hidden="true" class="error" id="phone_err">Non hai inserito il numero di telefono!</p>
        <p hidden="true" class="error" id="addr_err">Non hai inserito l'indirizzo!</p>
        <p hidden="true" class="error" id="email_err">Non hai inserito l'email!</p>
    </fieldset>
</form:form>
