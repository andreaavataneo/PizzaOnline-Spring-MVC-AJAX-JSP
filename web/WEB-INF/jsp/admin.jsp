<%-- 
    Document   : admin
    Created on : 19-feb-2014, 9.17.34
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../../resources/common/head.jsp" %>
        <script type="text/javascript">

            $(document).ready(function() {
                $('#idSel').click(function() {
                    var id = this.value;
                    $.ajax({
                        type: "POST",
                        url: id + ".htm",
                        success: function(response) {
                            // we have the response
                            var split = response.split("%");

                            $('#nameP').val(split[0]);
                            $('#description').val(split[1]);
                            $('#price').val(split[2]);
                        },
                        error: function(e) {
                            alert("Error: " + e);
                        }
                    });
                });


                $('#aggiungere,#modificare,#cancellare').click(function() {

                    var name = $('#nameP').val();
                    var descr = $('#description').val();
                    var price = $('#price').val();
                    var id_p = $('#idSel').val();
                    var act = this.id;
                    $('#finestra').html("Sei sicuro di volere " + this.id + " la pizza?");
                    $('#finestra').dialog({
                        modal: true,
                        width: 350,
                        buttons: {
                            "Conferma": function() {
                                if (validateForm(name, descr, price)) {
                                    var data = $('#formAddP').serialize();
                                    $.ajax({
                                        type: "POST",
                                        url: act + "/" + id_p + "/modify.htm",
                                        data: data,
                                        success: function(response) {
                                            location.reload();
                                        },
                                        error: function(e) {
                                            alert("Error: " + e);
                                        }
                                    });
                                }
                                $(this).dialog("close");
                            },
                            "Annulla": function() {
                                $(this).dialog("close");
                            }
                        }
                    });
                });

            });

            function validateForm(name, descr, price) {
                if (name === "") {
                    $('#name_err').show();
                    return false;
                }
                if (descr === "") {
                    $('#descr_err').show();
                    return false;
                }
                if (price == 0) {
                    $('#price_err').show();
                    return false;
                } else {
                    $('#name_err').hide();
                    $('#descr_err').hide();
                    $('#price_err').hide();
                    return true;
                }
            }
        </script>
    </head>
    <body>
        <div id="finestra" title="Conferma?"></div>

        <header>
            <%@include file="../../resources/common/header.html" %>           
        </header>    
        <article>


            <%@include file="../../resources/common/formAdmin.jsp" %>

            <section>
                <p hidden="true" class="error" id="name_err">Non hai inserito il nome della pizza!</p>
                <p hidden="true" class="error" id="descr_err">Non hai inserito la ricetta della pizza!</p>
                <p hidden="true" class="error" id="price_err">Non hai inserito il prezzo della pizza!</p>                   
            </section>
        </article>
        <nav id="menu"> 
            <%@include file="../../resources/common/user_box.jsp" %>   
            ${menuType}
        </nav>
        <footer>
            <%@include file="../../resources/common/footer.jsp" %>
        </footer>
    </body>
</html>
