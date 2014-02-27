/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pizze;

/**
 *
 * @author Andrea
 */
public class Pizza {

    private int id;    
    private String name;
    private String description;
    private double price;

    public Pizza(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Pizza() {
        this.name = "Nuova pizza";

    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
