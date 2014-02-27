<%-- 
    Document   : formAdmin
    Created on : 19-feb-2014, 12.09.17
    Author     : Andrea
--%>
<section class="formAdm">
    <form:form commandName="pizza" id="formAddP"> 
        <fieldset>
            <legend>Aggiungi, modifica o elimina una pizza</legend>
            <dl> 
                <dt><label>Seleziona la pizza</label></dt>
                <dd><form:select path="id" id="idSel" items="${allP}"/></dd>
                <dt><label>Nome: </label></dt>
                <dd><form:input path="name" id="nameP" type="text" placeholder="Nome nuova pizza" required="on" autofocus="on" ></form:input></dd>    
                <dt><label>Ricetta: </label></dt>
                <dd><form:input path="description" id="description" autofocus="on" type="text" placeholder="Specifiche ingredienti" required="on"></form:input></dd>
                <dt><label>Prezzo: </label></dt>
                <dd><form:input path="price" id="price" type="number" required="on" autofocus="on"></form:input></dd>            
            </dl>
        </fieldset>
        <fieldset>
            <input class="button" id="addP" type="button" value="Aggiungi pizza!"/>
            <input class="button" id="modP" type="button" value="Modifica pizza!"/>
            <input class="button" id="delP" type="button" value="Cancella pizza!"/>     
        </fieldset>
    </form:form>
    <form:form commandName="pizza" id="Orders">
        <fieldset>
            <input class="button" id="viewO" type="button" value="Mostra ordinazioni."/>
        </fieldset>
    </form:form>
</section>
