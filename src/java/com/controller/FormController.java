/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.pizze.Pizza;
import com.service.DB;
import com.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Andrea
 */
@Controller
@SessionAttributes("user")
public class FormController {

    @RequestMapping(value = "/LogIn", method = RequestMethod.POST)
    @ResponseBody
    public String logger(@ModelAttribute(value = "user") User user, BindingResult result) {
        String out;
        DB jdbc = new DB(); //istanzio un oggetto DB
        if (result.hasErrors()) {
            out = "Errore nell'interazione Ajax!";
        } else if (jdbc.logger(user)) {
            out = "Hai effettuato l'accesso con successo! " + user.getEmail(); //successo 
            if (user.getRole().equals("admin")) {
                out = "admin";
            }
        } else {
            out = "failed"; //errore
            user.setEmail("ospite");
            user.setPwd("password");
        }
        return out;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public String registration(@ModelAttribute(value = "user") User user, BindingResult result) {
        String returnText;
        DB jdbc = new DB();
        if (result.hasErrors()) {
            returnText = "Errore nell'interazione Ajax.";
        } else if (jdbc.checkMail(user.getEmail())) {
            jdbc.addUser(user);
            returnText = "Inserimento effettuato con successo!";
        } else {
            returnText = "C'è già una registrazione con questa email!";
        }

        return returnText;
    }

    @RequestMapping(value = "/ClientArea", method = RequestMethod.POST)
    @ResponseBody
    public String order() {
        return null;
    }

    @RequestMapping(value = "/{act}/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public String orderList(@PathVariable String act, @PathVariable Integer id, @ModelAttribute(value = "pizza") Pizza pizza) {
        String returnText = "<p>Errore nell'interazione Ajax!</p>";
        if (act.equals("modificare")) {
            DB jdbc = new DB();
            jdbc.modPizza(id, pizza.getName(), pizza.getDescription(), pizza.getPrice());
            returnText = "Pizza modificata con successo!\n"+jdbc.showMenu();
        }
        if (act.equals("aggiungere")) {
            DB jdbc = new DB();
            if (jdbc.addPizza(pizza.getName(), pizza.getDescription(), pizza.getPrice())) {
                returnText = "<p>Pizza aggiunta con successo!</p>\n" + jdbc.showMenu();
            } else {
                returnText = "<p>Errore nell'interazione con il DB. La pizza potrebbe essere gi&agrave; presente.</p>";
            }
        }
        if(act.equals("cancellare")){
            DB jdbc = new DB();
            if (jdbc.delPizza(id)) {
                returnText = "<p>Pizza eliminata con successo!</p>\n" + jdbc.showMenu();
            } else {
                returnText = "<p>Errore nell'interazione con il DB.</p>";
            }
        }
        return returnText;
    }

    @RequestMapping(value = "/viewO", method = RequestMethod.POST)
    @ResponseBody
    public String modify(@ModelAttribute(value = "pizza") Pizza pizza, BindingResult result) {
        String returnText = "<p>Errore nell'interazione con il DB</p>";
        DB jdbc = new DB();
        if (!result.hasErrors()) {
            returnText = jdbc.allOrders();
        };
        return returnText;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String loadP(@ModelAttribute(value = "pizza") Pizza pizza, @PathVariable Integer id, BindingResult result) {
        String returnText = "<p>Errore nell'interazione con il DB</p>";    
        //if (!result.hasErrors()) {
            DB jdbc = new DB();            
            returnText=jdbc.getPizzaData(id);        
        //}
        return returnText;
    }
}
