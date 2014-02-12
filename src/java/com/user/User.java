/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user;

/**
 *
 * @author Andrea
 */
public class User {
    
    private String name;
    private String address;
    private String email;
    private String pwd;
    
    /**
     * Costruttore necessario per istanziare User nel momento in cui si crea la ModelAndView relativa
     * alla pagina di LogIn.
     */
    public User(){
        
    }

    public User(String name, String address, String email, String pwd) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
    }   
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
