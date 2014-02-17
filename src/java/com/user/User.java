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
    private String surname;
    private String phone;
    private String address;
    private String email;
    private String pwd;

    public User(String name, String surname, String address, String email, String pwd, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.pwd = pwd;
        this.phone = phone;
    }

    /**
     * Costruttore necessario per istanziare User nel momento in cui si crea la
     * ModelAndView relativa alla pagina di LogIn.
     */
    public User() {
       this.email="ospite";
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

    
}
