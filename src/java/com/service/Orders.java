/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import java.util.LinkedList;
/**
 *
 * @author Andrea
 */
public class Orders {
    
    private String email;
    private LinkedList<String> order = new LinkedList<>();

    public Orders(String email) {
        this.email = email;
    }
    
}
