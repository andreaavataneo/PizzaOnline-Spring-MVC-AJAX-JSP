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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea
 */
public class DB {

    private static final String ur = "jdbc:derby://localhost:1527/pizzeria";
    private static final String us = "app";
    private static final String pwd = "app";

    /**
     * Converte la data dal formato americano a quello europeo (aggiungendo gli
     * zeri dove necesario)
     *
     * @param data (USA)
     * @return data convertita (EUR)
     */
    public String convData(String data) {
        String conv = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(sdf.parse(data));
        } catch (ParseException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        int month = cal.get(cal.MONTH) + 1;
        int day = cal.get(cal.DAY_OF_MONTH);
        String sm = Integer.toString(month);
        String sd = Integer.toString(day);
        if (month < 10) {
            sm = "0" + sm;
        }
        if (day < 10) {
            sd = "0" + sd;
        }

        conv = sd + "-" + sm + "-" + cal.get(cal.YEAR);
        return conv;
    }

    /**
     * Restituisce il catalogo delle pizze Ogni pizza è contenuta in un
     * <section>
     *
     * @return out
     */
    public String showMenu() {
        String out = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");

            while (rs.next()) {
                out += "<section class='ordini'><form><fieldset>"
                        + "<legend>" + rs.getString("namep") + "</legend>"
                        + "<table><tr><td>" + rs.getString("recipe") + "</td></tr><tr><td>" + rs.getDouble("price") + " &euro;</td></tr></table>"
                        + "</fieldset></form></section>";
            }
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
     * @return Map<Integer, String> allP
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
        allP.put(0, "- Nuova Pizza -");
        return allP;
    }

    /**
     * Dato un id_p di una pizza, restituisce una stringa contente i dati di
     * quest'ultima separati da un '%'
     *
     * @param id_p
     * @return out (stringa con i dati della pizza)
     */
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
     * Aggiunge una pizza assandogli i 3 campi passati come parametro
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
            st.executeUpdate("INSERT INTO pizzas (nameP, recipe, price) VALUES ('" + nameP + "', '" + recipe + "', " + price + ")");
            st.close();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Rimuove la pizza con id_p pasato come parametro (dalle pizze e da gli ordini)
     *
     * @param id_p
     */
    public boolean delPizza(Integer id_p) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM pizzas WHERE ID_P=" + id_p);
            st.executeUpdate("DELETE FROM orders WHERE ID_P=" + id_p);
            st.close();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Modifica la pizza identificata dal id_p e ci assegna i nuovi campi
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

    /**
     * Cerca all'interno del DB l'user passato come parametro. In caso di
     * successo, carica i relativi dati.
     *
     * @param user
     * @return true/false
     */
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

    /**
     * Aggiunge l'user passato come parametro al DB
     *
     * @param user
     * @return true/false
     */
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

    /**
     * Verfica che la mail inserita non sia già presente nel DB
     *
     * @param email
     * @return true/false
     */
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

    /**
     * Restituisce una stringa il cui contenuto è il form per la creazione di un
     * nuovo ordine
     *
     * @return form 'newOrder'
     */
    public String menuOrder() {
        String out = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            out = "<form id='newOrder'>"
                    + "<fieldset><legend>Crea nuovo ordine</legend>"
                    + "<table id='pizzalist'><tr><th>Pizza</th><th>Ricetta</th><th>Prezzo(€)</th><th>Ordine</th></tr>";

            while (rs.next()) {
                out = out + "<tr><td>" + rs.getString("NAMEP") + "</td><td>" + rs.getString("RECIPE") + "</td><td>" + rs.getString("PRICE") + "</td><td><input type='number' name='q" + rs.getString("id_p") + "' id='q" + rs.getString("id_p") + "' value='0' min='0' max='99'/></td></tr>";
            }

            GregorianCalendar gc = new GregorianCalendar();

            out = out + "</table>"
                    + "A che ora ?" + orariConsegne()
                    + "Quando ? <input type='date' id='dateo' name='dateo' "
                    + "min='" + gc.get(Calendar.YEAR) + "-" + gc.get(Calendar.MONTH) + "-" + gc.get(Calendar.DAY_OF_MONTH) + "'"
                    + "/>"
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
     * Restituisce tutti gli ordini futuri del cliente avente id_u passato nel
     * parametro. Ogni ordine è contenuto in un <section> e contiene un pulsante
     * 'Elimina' o 'Conferma' in base allo stato dell'ordine.
     *
     * @param id_u
     * @return Stringa contenente ordini di id_u
     */
    public String nextClientOrders(int id_u) {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "(select price from pizzas where pizzas.ID_P=orders.ID_P) as price,"
                    + "numberOf, datao, hour_time, shipped, received, id_o  from orders "
                    + "where id_u=" + id_u + " AND datao > current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return result;
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            double tot = 0;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
            } else {
                result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";

            tot += rs.getInt("numberOf") * rs.getDouble("price");
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "<tr><th>Totale</th><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
                    tot = 0;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
                    } else {
                        result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                tot += rs.getInt("numberOf") * rs.getDouble("price");
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result += "<tr><th>Totale</th><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
            tot = 0;
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
     * Restituisce tutti gli ordini odierni del cliente avente id_u passato nel
     * parametro. Ogni ordine è contenuto in un <section> e contiene un pulsante
     * 'Elimina' o 'Conferma' in base allo stato dell'ordine.
     *
     * @param id_u
     * @return Stringa contenente ordini di id_u
     */
    public String todayClientOrders(int id_u) {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "(select price from pizzas where pizzas.ID_P=orders.ID_P) as price,"
                    + "numberOf, datao, hour_time, shipped, received, id_o  from orders "
                    + "where id_u=" + id_u + " AND datao = current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return result;
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;
            double tot = 0;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

            if (rs.getBoolean("shipped") == false) {
                result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
            } else {
                result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
            }

            result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                    + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                    + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";

            tot += rs.getInt("numberOf") * rs.getDouble("price");
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time")))) {
                    c++;
                    result += "<tr><th>Totale</th><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
                    tot = 0;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderDel' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderCon' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

                    if (rs.getBoolean("shipped") == false) {
                        result += "<input class='button' id='elimina' type='submit' value='Elimina Ordine'/>";
                    } else {
                        result += "<input class='button' id='conferma' type='submit' value='Conferma Consegna'/>";
                    }
                    result += "<input type='hidden' id='datao' name='datao' value='" + rs.getString("datao") + "'/>"
                            + "<input type='hidden' id='hour_time' name='hour_time' value='" + rs.getString("hour_time") + "'/>"
                            + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th></tr>";
                }

                result = result + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td></tr>";
                tot += rs.getInt("numberOf") * rs.getDouble("price");
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result += "<tr><th>Totale</th><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
            tot = 0;
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
     * Registra la spedizione di un ordine identificato da i tre parametri
     *
     * @param id_u
     * @param datao
     * @param hour_time
     */
    public void sendOrder(int id_u, String datao, String hour_time) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE orders SET shipped=true where "
                    + "id_u=" + id_u + " AND "
                    + "datao='" + datao + "' AND "
                    + "hour_time='" + hour_time + "'");
            st.close();
            conn.close();
        } catch (SQLException e) {
        }
    }

    /**
     * Conferma la ricezione di un ordine identificato da i tre parametri
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

    /**
     * Inserisce l'ordine di una tipologia di pizza all'interno del DB. I
     * parametri richiesti sono i dati da inserire.
     *
     * @param id_u
     * @param id_p
     * @param numberOf
     * @param datao
     * @param hour_time
     * @return true/flase
     * @throws ParseException
     */
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

    /**
     * Cancella l'ordine identificato da i tre parametri
     *
     * @param id_u
     * @param datao
     * @param hour_time
     */
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

    /**
     * Restituisci una stringa contenente tutti gli ordini da evadere oggi. Ogni
     * ordine è conenuto in un <section> e contiene un bottone 'Spedisci' o
     * 'Aspetta' in base allo stato dell'ordine.
     *
     * @return Ordini odierni da evadere
     */
    public String todayTask() {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "(select price from pizzas where pizzas.ID_P=orders.ID_P) as price,"
                    + "numberOf, datao, hour_time, shipped, received, id_o, nameu, surname, address, phone, email from orders, users "
                    + "where users.id_u=orders.id_u AND datao = current_date AND received=false order by datao,hour_time");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return ""
                        + "<form><fieldset><legend>"
                        + "Ordini per oggi"
                        + "</legend>Attenzione! Non ci sono ordinazioni!</fieldset></form>"
                        + "";
            }
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            String emailOld = rs.getString("email");
            int c = 1;
            double tot = 0;
            result += "<section class='ordini'>";
            if (rs.getBoolean("shipped") == false) {
                result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
            } else {
                result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
            }

            result += "<fieldset>"
                    + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

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
            tot += rs.getInt("numberOf") * rs.getDouble("price");
            while (rs.next()) {

                if ((!dataOld.equals(rs.getString("datao"))) || (!hourOld.equals(rs.getString("hour_time"))) || (!emailOld.equals(rs.getString("email")))) {
                    c++;
                    result += "<tr><td>Totale</td><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
                    tot = 0;
                    result += "</table></fieldset></form></section>";

                    result += "<section class='ordini'>";
                    if (rs.getBoolean("shipped") == false) {
                        result += "<form class='orderSend' name='ord" + c + "' id='ord" + c + "' >";
                    } else {
                        result += "<form class='orderShipped' name='ord" + c + "' id='ord" + c + "' >";
                    }

                    result += "<fieldset>"
                            + "<legend>Ordine per il: " + convData(rs.getString("datao")) + "<br>(h: " + rs.getString("hour_time") + ")</legend>";

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
                tot += rs.getInt("numberOf") * rs.getDouble("price");
                dataOld = rs.getString("datao");
                hourOld = rs.getString("hour_time");
            }
            result += "<tr><th>Totale</th><td>" + String.format("%.2f", tot) + " &euro;</td></tr>";
            tot = 0;
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
     * Restituisci una stringa contenente tutti gli ordini futuri da evadere.
     * Ogni giorno successivo è contenuto in un <section> e gli ordini sono
     * riportati i tabelle
     *
     * @return Ordini odierni da evadere
     */
    public String nextTask() {
        String result = "";
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select (select nameP from pizzas where pizzas.ID_P=orders.ID_P) as nameP,"
                    + "(select price from pizzas where pizzas.ID_P=orders.ID_P) as price,"
                    + "numberOf, datao, hour_time, shipped, received, id_o, nameu, surname, address, phone, email from orders, users "
                    + "where users.id_u=orders.id_u AND datao > current_date AND received=false order by datao,hour_time,email");

            rs.next();
            if (rs.getRow() != 1) {
                rs.close();
                st.close();
                conn.close();
                return "<section class='ordiniFut'></section>";
            }



            String emailOld = rs.getString("email");
            String dataOld = rs.getString("datao");
            String hourOld = rs.getString("hour_time");
            int c = 1;


            result += "<section class='ordiniFut'>";
            result += "<form class='futureOrder' name='ord" + c + "' id='ord" + c + "' >"
                    + "<fieldset><legend>Ordini per il " + convData(dataOld) + "</legend>"
                    + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th><th>Orario</th><th>Cliente</th></tr>"
                    + "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td><td>" + rs.getString("hour_time") + "</td><td>" + rs.getString("email") + "</td></tr>";

            while (rs.next()) {
                if ((!dataOld.equals(rs.getString("datao")))) {
                    result += "</table></fieldset></form></section>";
                    c++;
                    emailOld = rs.getString("email");
                    hourOld = rs.getString("hour_time");
                    result += "<section class='ordiniFut'>"
                            + "<form class='futureOrder' name='ord" + c + "' id='ord" + c + "' ><fieldset>"
                            + "<legend>Ordini per il " + convData(rs.getString("datao")) + "</legend>"
                            + "<table><tr><th>Pizza</th><th>Quantit&agrave;</th><th>Orario</th><th>Cliente</th></tr>";
                }
                if (!emailOld.equals(rs.getString("email")) || (!hourOld.equals(rs.getString("hour_time")))) {
                    result += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
                }
                result += "<tr><td>" + rs.getString("nameP") + "</td><td>" + rs.getInt("numberOf") + "</td><td>" + rs.getString("hour_time") + "</td><td>" + rs.getString("email") + "</td></tr>";


                dataOld = rs.getString("datao");
                emailOld = rs.getString("email");
                hourOld = rs.getString("hour_time");
            }
            result += "</table></fieldset></form></section>";
            rs.close();
            st.close();
            conn.close();

        } catch (SQLException e) {
            result += e.getMessage();
        }
        return result;
    }

    /**
     * Restituisce la lista dei possibili orari di consegna.
     * @return inout type select 
     */
    private String orariConsegne() {
        return "<select id='hour_time' name='hour_time'>"
                + "<option value='19:00'>19:00</option>"
                + "<option value='19:15'>19:15</option>"
                + "<option value='19:30'>19:30</option>"
                + "<option value='19:45'>19:45</option>"
                + "<option value='20:00'>20:00</option>"
                + "<option value='20:15'>20:15</option>"
                + "<option value='20:30'>20:30</option>"
                + "<option value='20:45'>20:45</option>"
                + "<option value='21:00'>21:00</option>"
                + "<option value='21:15'>21:15</option>"
                + "<option value='21:30'>21:30</option>"
                + "<option value='21:45'>21:45</option>"
                + "<option value='22:00'>22:00</option>"
                + "<option value='22:15'>22:15</option>"
                + "<option value='22:30'>22:30</option>"
                + "<option value='22:45'>22:45</option>"
                + "<option value='23:00'>23:00</option>"
                + "</select>";
    }
}
