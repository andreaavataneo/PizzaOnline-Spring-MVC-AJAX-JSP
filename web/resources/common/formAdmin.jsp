<%-- 
    Document   : formAdmin
    Created on : 19-feb-2014, 12.09.17
    Author     : Andrea
--%>
<form:form commandName="pizza"> 
    <fieldset>
        <legend>Dettagli pizza da inserire:</legend>
        <dl>
            <dt><label>Nome: </label></dt>
            <dd><form:input path="name" id="name" type="text" value="Nome nuova pizza"></form:input></dd>    
            <dt><label>Ricetta: </label></dt>
            <dd><form:input path="description" id="description" type="text"></form:input></dd>
        </dl>
    </fieldset>
    <fieldset>
        <input class="button" id="addP" type="button" value="Aggiungi pizza!"/>
        <input class="button" id="viewO" type="button" value="Mostra ordinazioni."/>
        <p hidden="true" class="error" id="name_err">Non hai inserito il nome della nuova pizza!</p>
        <p hidden="true" class="error" id="descr_err">Non hai inserito la ricetta della nuova pizza!</p>        
    </fieldset>
</form:form>
