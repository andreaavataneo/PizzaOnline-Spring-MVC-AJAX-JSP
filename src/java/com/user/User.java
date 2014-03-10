/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user;

import org.springframework.context.annotation.Scope;

/**
 *
 * @author Andrea
 */
@Scope("session")
public class User {
    
    private int id_u;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String email;
    private String pwd;
    private String role;

   
    public User(String name, String surname, String address, String email, String pwd, String phone, String role) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.phone = phone;
        this.role = role;
    }

    /**
     * Costruttore necessario per istanziare User nel momento in cui si crea la
     * ModelAndView relativa alla pagina di LogIn.
     */
    public User() {
       this.email="ospite";
       this.role="guest";
    }
        
    public int getId_u() {
        return this.id_u;
    }
    
    public void setId_u(int id) {
        this.id_u=id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

     public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
