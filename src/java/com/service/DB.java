/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;
import java.sql.*;
/**
 *
 * @author Andrea
 */
public class DB {
    
    private static final String ur = "jdbc:derby://localhost:1527/sample";
    private static final String us = "app";
    private static final String pwd = "app";
    
    public String showMenu(){
        String out="";
          try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();            
            ResultSet rs= st.executeQuery("SELECT * FROM PIZZE");
            while(rs.next()){
            out=out+"Nome: "+rs.getString("name")+" Descrizione: "+rs.getString("description")+"<br>";
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            out = e.getMessage();
        }
        return out;
    }
    
    public boolean logger(String n,String p){
        boolean out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();            
            ResultSet rs= st.executeQuery("SELECT * FROM USERS WHERE (NAME='"+n+"') AND (PASSWORD='"+p+"')");
            rs.next();
            if(rs.getRow()==1){out=true;}
            else out=false;
            st.close();
            conn.close();
            rs.close();
        } catch (SQLException e) {
            out = false;
        }
        return out;
    }
    
    public void addUser(String name, String email, String addr, String pwd){
        
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();            
            ResultSet rs= st.executeQuery("INSERT INTO USERS");           
            st.close();
            conn.close();
            rs.close();          
        } catch (SQLException e) {     }        
    }
     
    public boolean checkMail(String email){
        boolean out;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            Connection conn = DriverManager.getConnection(ur, us, pwd);
            Statement st = conn.createStatement();            
            ResultSet rs= st.executeQuery("SELECT * FROM USERS WHERE (EMAIL='"+email+"')");
            rs.next();
            if(rs.getRow()==0){out=true;}
            else out=false;
            st.close();
            conn.close();
            rs.close();          
        } catch (SQLException e) { out=false; } 
        return out;      
    }
   
  
   
}
