/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import java.sql.*;
import com.user.User;
/**
 *
 * @author Andrea
 */
public class DB {

    private static final String ur = "jdbc:derby://localhost:1527/pizzeria";
    private static final String us = "app";
    private static final String pwd = "app";

    public String showMenu() {
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            out = "<table id='pizzalist'><tr><td>Nome</td><td>Ricetta</td><td>Prezzo(€)</td></tr>";
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
                user.setName(rs.getString("NAMEU"));
                user.setSurname(rs.getString("SURNAME"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setPhone(rs.getString("PHONE"));
                user.setRole(rs.getString("ROLE"));                
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
            st.executeUpdate("INSERT INTO users(nameU, surname, password, address, phone, admin, email) VALUES ('" + user.getName() + "', '" + user.getSurname() + "', '" + user.getPwd() + "', '" + user.getAddress() + "', '" + user.getPhone() + "',client, '" + user.getEmail() + "')");
            st.close();
            conn.close();           
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

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
    
    public String menuOrder(){
        String out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PIZZAS");
            out = "<table id='pizzalist'><tr><td>Nome</td><td>Ricetta</td><td>Prezzo(€)</td></tr>";
            while (rs.next()) {

                out = out + "<tr><td>" + rs.getString("NAMEP") + "</td><td>" + rs.getString("RECIPE") + "</td><td>" + rs.getString("PRICE") + "</td><td><input id='"+rs.getString("NAMEP")+"' value='0'/></td></tr>";
            }
            out = out + "</table>";
            out = out +"<button onClicl='doAjaxPost();'>Ordina!</button>";
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;
    }
}
