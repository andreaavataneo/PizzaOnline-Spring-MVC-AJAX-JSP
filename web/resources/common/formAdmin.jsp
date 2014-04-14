<%-- 
    Document   : formAdmin
    Created on : 19-feb-2014, 12.09.17
    Author     : Andrea
--%>
<section id="formAdm">
    <form:form commandName="pizza" id="formAddP"> 
        <fieldset id="moveD">
            <legend>Aggiungi, modifica o elimina una pizza</legend>
            
                <dt><label>Seleziona la pizza</label></dt>
                <dd><form:select path="id" id="idSel" items="${allP}"/></dd>
                <dt><label>Nome: </label></dt>
                <dd><form:input path="name" id="nameP" type="text" placeholder="Nome nuova pizza" required="on" autofocus="on" ></form:input></dd>    
                <dt><label>Ricetta: </label></dt>
                <dd><form:input path="description" id="description" autofocus="on" type="text" placeholder="Specifiche ingredienti" required="on"></form:input></dd>
                <dt><label>Prezzo: </label></dt>
                <dd><form:input path="price" id="price" type="number" required="on" autofocus="on"></form:input></dd>            
            
        </fieldset>
        <fieldset>
            <input class="button" id="aggiungere" type="button" value="Aggiungi pizza!"/>
            <input class="button" id="modificare" type="button" value="Modifica pizza!"/>
            <input class="button" id="cancellare" type="button" value="Cancella pizza!"/>     
        </fieldset>
    </form:form>
    
</section>
