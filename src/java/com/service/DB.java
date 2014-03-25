/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import java.sql.*;
import com.user.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Andrea
 */
public class DB {

    private static final String ur = "jdbc:derby://localhost:1527/pizzeria";
    private static final String us = "app";
    private static final String pwd = "app";

    /**
     * Restituisce una tabella chiamata 'pizzalist' con le pizze appunto
     *
     * @return out
     */
    public String showMenu() {
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            out = "<table class=\"main\" id=\"pizzalist\"><tr><td>Nome</td><td>Ricetta</td><td>Prezzo(€)</td></tr>";
            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("NAMEP") + "</td><td>" + rs.getString("RECIPE") + "</td><td>" + rs.getString("PRICE") + "</td></tr>";
            }
            out = out + "</table>";
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;
    }

    /**
     * Restituisce una linkedlist con i nomi di tutte le pizze nel DB
     *
     * @return
     */
    public Map listPizzas() {
        Map<Integer, String> allP = new HashMap<>();
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            while (rs.next()) {
                allP.put(rs.getInt("ID_P"), rs.getString("NAMEP"));
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
        allP.put(0, "Nuova Pizza");
        return allP;
    }

    public String getPizzaData(Integer id_p) {
        String out = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS WHERE ID_P=" + id_p);
            rs.next();
            out = rs.getString("NAMEP") + "%" + rs.getString("RECIPE") + "%" + rs.getString("PRICE");
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
        return out;
    }

    /**
     * aggiunge una pizza assandogli i 3 campi
     *
     * @param nameP
     * @param recipe
     * @param price
     */
    public boolean addPizza(String nameP, String recipe, double price) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();

            /*ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS WHERE nameP=" + nameP);
             if (rs.next()) {
             st.close();
             conn.close();
             return false;
             } else {*/
            st.executeUpdate("INSERT INTO pizzas (nameP, recipe, price) VALUES ('" + nameP + "', '" + recipe + "', " + price + ")");
            st.close();
            conn.close();
            //}
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Rimuove la pizza con id_p pasato come parametro
     *
     * @param id_p
     */
    public boolean delPizza(Integer id_p) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM pizzas WHERE ID_P=" + id_p);
            st.close();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * modifica la pizza identificata dal id_p e ci assegna i nuovi campi
     *
     * @param id_p
     * @param nameP
     * @param recipe
     * @param price
     */
    public void modPizza(Integer id_p, String nameP, String recipe, double price) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE pizzas SET nameP='" + nameP + "', recipe='" + recipe + "', price=" + price
                    + " where id_p=" + id_p);
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
    }

    public boolean logger(User user) {
        boolean out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM USERS WHERE (EMAIL='" + user.getEmail() + "') AND (PASSWORD='" + user.getPwd() + "')");
            rs.next();
            if (rs.getRow() == 1) {
                out = true;
                user.setId_u(rs.getInt("id_u"));
                user.setName(rs.getString("NAMEU"));
                user.setSurname(rs.getString("SURNAME"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setPhone(rs.getString("PHONE"));
                user.setTypeRole(rs.getString("TYPEROLE"));
            } else {
                out = false;
            }
            st.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            out = false;
        }
        return out;
    }

    public boolean addUser(User user) {

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO users(nameU, surname, password, address, phone, typeRole, email) VALUES ('" + user.getName() + "', '" + user.getSurname() + "', '" + user.getPwd() + "', '" + user.getAddress() + "', '" + user.getPhone() + "', 'client', '" + user.getEmail() + "')");
            user.setTypeRole("client");
            st.close();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //verfica che la mail inserita non sia già presente nel DB
    public boolean checkMail(String email) {
        boolean out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM USERS WHERE (EMAIL='" + email + "')");
            rs.next();
            if (rs.getRow() == 0) {
                out = true;
            } else {
                out = false;
            }
            st.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            out = false;
        }
        return out;
    }

    //restituisce un form con il quale è possiblile effettuare un'ordinazione
    public String menuOrder() {
        String out = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            /*<form:form commandName="pizza" id="formAddP"> 
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
             </fieldset>*/
            out = "<form id='newOrder'>"
                    + "<fieldset><legend>Crea nuovo ordine</legend>"
                    + "<table id='pizzalist'><tr><td>Nome</td><td>Ricetta</td><td>Prezzo(€)</td><td>Ordine</td></tr>";

            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("NAMEP") + "</td><td>" + rs.getString("RECIPE") + "</td><td>" + rs.getString("PRICE") + "</td><td><input type='number' name='q" + rs.getString("id_p") + "' id='q" + rs.getString("id_p") + "' value='0' min='0' max='99'/></td></tr>";
            }
            GregorianCalendar gc = new GregorianCalendar();

            out = out + "</table>"
                    + "A che ora ? <input type='time' id='hour_time' name='hour_time' min='19:00' max='23:00'/>"
                    + "Quando ? <input type='date' id='dateo' name='dateo' min='" + gc.get(Calendar.YEAR) + "-" + gc.get(Calendar.MONTH) + "-" + gc.get(Calendar.DAY_OF_MONTH) + "'/>"
                    + "<input type='submit' class='button' id='Ordina' value='Ordina'/>"
                    + "</fieldset></form>";

            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;
    }

    /**
     * restituisce una tabella 'orderlist' con tutti gli ordini esistenti e
     * tutte le possibili informazioni
     *
     * @return out
     */
    public String allOrders() {
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select email from users where users.ID_U=orders.ID_U) as cliente,"
                    + "(select address from users where users.ID_U=orders.ID_U) as destinazione,"
                    + "(select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received  from orders "
                    + "order by datao,hour_time,id_u");

            out = "<table class=\"main\" id=\"orderlist\"><tr><td>Cliente</td><td>Destinazione</td><td>Pizza</td><td>Quante?</td><td>Data</td><td>Fascia Oraria</td><td>In viaggio?</td><td>Arrivata?</td></tr>";
            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("cliente") + "</td><td>" + rs.getString("destinazione") + "</td><td>" + rs.getString("nameP") + "</td><td>" + rs.getString("numberOf") + "</td><td>" + rs.getString("datao") + "</td><td>" + rs.getString("hour_time") + "</td><td>" + rs.getString("shipped") + "</td><td>" + rs.getString("received") + "</td></tr>";
            }
            out = out + "</table>";
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;

    }

    /**
     * restituisce una tabella id='orderlist' con tutti gli ordini ancora da
     * evadere
     *
     * @return out
     */
    public String allWaitingOrders() {
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select email from users where users.ID_U=orders.ID_U) as cliente,"
                    + "(select address from users where users.ID_U=orders.ID_U) as destinazione,"
                    + "(select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received  from orders "
                    + "where shipped=false AND datao > current_date order by datao,hour_time,id_u");
            out = "<table id=\"orderlist\"><tr><td>Cliente</td><td>Destinazione</td><td>Pizza</td><td>Quante?</td><td>Data</td><td>Fascia Oraria</td><td>In viaggio?</td><td>Arrivata?</td></tr>";
            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("cliente") + "</td><td>" + rs.getString("destinazione") + "</td><td>" + rs.getString("nameP") + "</td><td>" + rs.getString("numberOf") + "</td><td>" + rs.getString("datao") + "</td><td>" + rs.getString("hour_time") + "</td><td>" + rs.getString("shipped") + "</td><td>" + rs.getString("received") + "</td></tr>";
            }
            out = out + "</table>";
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;

    }

    /**
     * restituisce una tabella id='clientOrders' con gli ordini di un
     * determinato cliente (data >= a oggi)
     *
     */
    //Aggiungere controllo per mettere dinamicamente elimina ordine o conferma ricezione !!!
    public String nextClientOrders(int id_u) {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received, id_o  from orders "
                    + "where id_u=" + id_u + " AND datao > current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return "<section class='ordini'>Nessun ordine futuro ...</section>";
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
            } else {
                result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><td>Pizza</td><td>Quantit&agrave;</td></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
                    } else {
                        result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><td>Pizza</td><td>Quantit&agrave;</td></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result = result + "</table></fieldset></form></section>";
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            result += e.getMessage();
        }
        return result;

    }

    public String todayClientOrders(int id_u) {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received, id_o  from orders "
                    + "where id_u=" + id_u + " AND datao = current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return "<section class='ordini'>Nessun ordine per oggi ...</section>";
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
            } else {
                result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><td>Pizza</td><td>Quantit&agrave;</td></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
                    } else {
                        result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><td>Pizza</td><td>Quantit&agrave;</td></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result = result + "</table></fieldset></form></section>";
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            result += e.getMessage();
        }
        return result;
    }

    /**
     * restituisce una tabella id='orderlist' con tutti gli ordini evasi ma
     * ancora non confermati
     *
     * @return out
     */
    public String allShippedOrders() {
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select email from users where users.ID_U=orders.ID_U) as cliente,"
                    + "(select address from users where users.ID_U=orders.ID_U) as destinazione,"
                    + "(select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received  from orders "
                    + "where shipped=true AND datao > current_date order by datao,hour_time,id_u");
            out = "<table id=\"orderlist\"><tr><td>Cliente</td><td>Destinazione</td><td>Pizza</td><td>Quante?</td><td>Data</td><td>Fascia Oraria</td><td>In viaggio?</td><td>Arrivata?</td></tr>";
            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("cliente") + "</td><td>" + rs.getString("destinazione") + "</td><td>" + rs.getString("nameP") + "</td><td>" + rs.getString("numberOf") + "</td><td>" + rs.getString("datao") + "</td><td>" + rs.getString("hour_time") + "</td><td>" + rs.getString("shipped") + "</td><td>" + rs.getString("received") + "</td></tr>";
            }
            out = out + "</table>";
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;

    }

    /**
     * imposta il valore di shipped a VERO in base ai 3 parametri id_u
     *
     * @param id_u
     * @param datao
     * @param hour_time
     */
    public void sendOrder(String datao, String hour_time) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE orders SET shipped=true where "
                    + "datao='" + datao + "' AND "
                    + "hour_time='" + hour_time + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
    }

    /**
     * conferma la ricezione di un ordine
     *
     * @param id_u
     * @param datao
     * @param hour_time
     */
    public void confirmOrder(int id_u, String datao, String hour_time) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE orders SET received=true where "
                    + "id_u=" + id_u + " AND "
                    + "datao='" + datao + "' AND "
                    + "hour_time='" + hour_time + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
    }

    public boolean addOrder(int id_u, int id_p, int numberOf, String datao, String hour_time) throws ParseException {

        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        cal.setTime(sdf.parse(datao + " " + hour_time));
        if (cal.after(today)) {
            try {
                DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
                Connection conn = DriverManager.getConnection(ur, us, pwd);
                Statement st = conn.createStatement();
                st.executeUpdate("INSERT INTO orders(id_u, id_p, numberOf, datao, hour_time, shipped, received) "
                        + "VALUES (" + id_u + ", " + id_p + ", " + numberOf + ", '" + datao + "', '" + hour_time + "', false, false)");
                st.close();
                conn.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    public void delOrder(int id_u, String datao, String hour_time) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM orders WHERE id_u=" + id_u + " AND datao='" + datao + "' AND hour_time='" + hour_time + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
    }

    public String todayTask() {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received, id_o, nameu, surname, address, phone from orders, users "
                    + "where users.id_u=orders.id_u AND datao = current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return "<section class='ordini'>Nessun ordine per oggi ...</section>";
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='spedisci' type='submit' value='Spedisci Ordine'/>";
            } else {
                result += "<input class='button' id='aspetta' type='button' value='Aspetta Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><td>" + rs.getString("nameu") + "</td><td>" + rs.getString("surname") + "</td></tr>"
                    + "<tr><td>" + rs.getString("address") + "</td><td>" + rs.getString("phone") + "</td></tr>"
                    + "<tr><th>Pizza</th><th>Quantit&agrave;</th></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='spedisci' type='submit' value='Spedisci Ordine'/>";
                    } else {
                        result += "<input class='button' id='aspetta' type='button' value='Attendi Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><td>" + rs.getString("nameu") + "</td><td>" + rs.getString("surname") + "</td></tr>"
                            + "<tr><td>" + rs.getString("address") + "</td><td>" + rs.getString("phone") + "</td></tr>"
                            + "<tr><th>Pizza</th><th>Quantit&agrave;</th></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result = result + "</table></fieldset></form></section>";
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            result += e.getMessage();
        }
        return result;
    }

    public String nextTask() {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "numberOf, datao, hour_time, shipped, received, id_o, nameu, surname, address, phone from orders, users "
                    + "where users.id_u=orders.id_u AND datao > current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return "<section class='ordini'>Nessun ordine per oggi ...</section>";
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='spedisci' type='submit' value='Spedisci Ordine'/>";
            } else {
                result += "<input class='button' id='aspetta' type='button' value='Aspetta Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><td>" + rs.getString("nameu") + "</td><td>" + rs.getString("surname") + "</td></tr>"
                    + "<tr><td>" + rs.getString("address") + "</td><td>" + rs.getString("phone") + "</td></tr>"
                    + "<tr><th>Pizza</th><th>Quantit&agrave;</th></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + rs.getDate("datao") + "  (h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='spedisci' type='submit' value='Spedisci Ordine'/>";
                    } else {
                        result += "<input class='button' id='aspetta' type='button' value='Attendi Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><td>" + rs.getString("nameu") + "</td><td>" + rs.getString("surname") + "</td></tr>"
                            + "<tr><td>" + rs.getString("address") + "</td><td>" + rs.getString("phone") + "</td></tr>"
                            + "<tr><th>Pizza</th><th>Quantit&agrave;</th></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result = result + "</table></fieldset></form></section>";
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            result += e.getMessage();
        }
        return result;
    }
}
