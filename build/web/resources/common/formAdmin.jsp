<%-- 
    Document   : formAdmin
    Created on : 19-feb-2014, 12.09.17
    Author     : Andrea
--%>
<form:form commandName="pizza" id="add"> 
    <fieldset>
        <legend>Aggiung una nuova pizza</legend>
        <dl>           
            <dt><label>Nome: </label></dt>
            <dd><form:input path="name" id="name" type="text" placeholder="Nome nuova pizza" required="on"></form:input></dd>    
            <dt><label>Ricetta: </label></dt>
            <dd><form:input path="description" id="description" type="text" placeholder="Specifiche ingredienti" required="on"></form:input></dd>
            <dt><label>Prezzo: </label></dt>
            <dd><form:input path="price" id="price" type="number" required="on"></form:input></dd>            
        </dl>
    </fieldset>
    <fieldset>
        <input class="button" id="addP" type="button" value="Aggiungi pizza!"/>
        <input class="button" id="viewO" type="button" value="Mostra ordinazioni."/>
        <p hidden="true" class="error" id="name_err">Non hai inserito il nome della nuova pizza!</p>
        <p hidden="true" class="error" id="descr_err">Non hai inserito la ricetta della nuova pizza!</p>        
    </fieldset>
</form:form>
<form:form commandName="pizza" id="mod"> 
    <fieldset>
        <legend>Modifica una pizza esistente</legend>
        <dl> 
            <dt><label>Seleziona la pizza</label></dt>
            <dd><form:select path="name" id="nameSel" items="${allP}"/></dd> 
            <dt><label>Nome: </label></dt>
            <dd><form:input path="name" id="nameNew" type="text" placeholder="Nuovo nome nuova pizza"></form:input></dd>    
            <dt><label>Ricetta: </label></dt>
            <dd><form:input path="description" id="descriptionNew" type="text" placeholder="Nuove specifiche ingredienti"></form:input></dd>
            <dt><label>Prezzo: </label></dt>
            <dd><form:input path="price" id="price" type="number"></form:input></dd>            
        </dl>
    </fieldset>
    <fieldset>
        <input class="button" id="modP" type="button" value="Modifica pizza!"/>
        <input class="button" id="delP" type="button" value="Cancella pizza!"/>
        <p hidden="true" class="error" id="nameNew_err">Non hai inserito il nome della nuova pizza!</p>
        <p hidden="true" class="error" id="descrNew_err">Non hai inserito la ricetta della nuova pizza!</p>        
    </fieldset>
</form:form>
