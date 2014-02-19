<%-- 
    Document   : formAdmin
    Created on : 19-feb-2014, 12.09.17
    Author     : Andrea
--%>
<form:form commandName="pizza"> 
    <fieldset>
        <legend>Dettagli pizza da inserire:</legend>
        <ul>
            <li><label>Nome: </label><form:input path="name" id="name" type="text" value="Nome nuova pizza"></form:input><p hidden="true" class="error" id="name_err">Non hai inserito il nome della nuova pizza!</p></li>
            <li><label>Ricetta: </label><form:input path="description" id="description" type="text"></form:input><p hidden="true" class="error" id="descr_err">Non hai inserito la ricetta della nuova pizza!</p></li> 
        </ul>
    </fieldset>
    <fieldset>
        <input class="button" type="button" onclick="doAjaxPost();" value="Aggiungi pizza!">
    </fieldset>
</form:form>
