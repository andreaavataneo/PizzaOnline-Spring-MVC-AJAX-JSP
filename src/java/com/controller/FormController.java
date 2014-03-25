/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.pizze.Pizza;
import com.service.DB;
import com.user.User;
import java.text.ParseException;
import java.util.StringTokenizer;
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

            if (user.getTypeRole().equals("admin")) {
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
            returnText = "Pizza modificata con successo!\n" + jdbc.showMenu();

        }
        if (act.equals("aggiungere")) {
            DB jdbc = new DB();
            if (jdbc.addPizza(pizza.getName(), pizza.getDescription(), pizza.getPrice())) {
                returnText = "<p>Pizza aggiunta con successo!</p>\n" + jdbc.showMenu();
            } else {
                returnText = "<p>Errore nell'interazione con il DB. La pizza potrebbe essere gi&agrave; presente.</p>";
            }
        }
        if (act.equals("cancellare")) {
            DB jdbc = new DB();
            if (jdbc.delPizza(id)) {
                returnText = "<p>Pizza eliminata con successo!</p>\n" + jdbc.showMenu();
            } else {
                returnText = "<p>Errore nell'interazione con il DB.</p>";
            }
        }
        return returnText;
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String loadP(@ModelAttribute(value = "pizza") Pizza pizza, @PathVariable Integer id, BindingResult result) {
        String returnText = "<p>Errore nell'interazione con il DB</p>";
        if (!result.hasErrors()) {
        DB jdbc = new DB();
        returnText = jdbc.getPizzaData(id);
        }
        return returnText;
    }

    @RequestMapping(value = "/{datiURL}/createOrd", method = RequestMethod.POST)
    @ResponseBody
    public String createOrder(@PathVariable String datiURL, @ModelAttribute(value = "user") User user) throws ParseException {
        String res = "";
        StringTokenizer st = new StringTokenizer(datiURL, "&");
        StringTokenizer st2 = new StringTokenizer(datiURL, "&");
        String temp = "";
        String stringVal = "";
        String stringId = "";
        String orario = "";
        String giorno = "";
        int numberVal = 0;
        int numberId = 0;
        int posU = 1;
        DB jdbc = new DB();

        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            posU = temp.indexOf('=');
            stringId = temp.substring(0, posU);
            stringVal = temp.substring(posU + 1, temp.length());
            if (stringId.equals("hour_time")) {
                orario = stringVal;
            }
            if (stringId.equals("dateo")) {
                giorno = stringVal;
            }
        }
        res += orario + " " + giorno;

        while (st2.hasMoreTokens()) {
            temp = st2.nextToken();
            posU = temp.indexOf('=');

            if (temp.charAt(0) == 'q') {
                stringId = temp.substring(1, posU);
                stringVal = temp.substring(posU + 1, temp.length());
                numberId = Integer.parseInt(stringId);
                numberVal = Integer.parseInt(stringVal);
                res = res + " : " + stringId + " : " + stringVal;
                if (numberVal > 0) {
                    if(!jdbc.addOrder(user.getId_u(), numberId, numberVal, giorno, orario)){
                    return "Hai inserito una data non valida.";
                    }
                }
            }
        }
        return "Ordine effettuato con successo per il giorno: \n"+giorno+" alle ore: "+orario;
    }

    @RequestMapping(value = "/{data}/{ora}/delOrd", method = RequestMethod.POST)
    @ResponseBody
    public void delOrder(@PathVariable String data, @PathVariable String ora, @ModelAttribute(value = "user") User user) {
        DB jdbc = new DB();
        jdbc.delOrder(user.getId_u(), data, ora);
    }
    
    @RequestMapping(value = "/{data}/{ora}/conOrd", method = RequestMethod.POST)
    @ResponseBody
    public void conOrder(@PathVariable String data, @PathVariable String ora, @ModelAttribute(value = "user") User user) {
        DB jdbc = new DB();
        jdbc.confirmOrder(user.getId_u(), data, ora);
    }
    
    @RequestMapping(value = "/{data}/{ora}/orderSend", method = RequestMethod.POST)
    @ResponseBody
    public void orderSend(@PathVariable String data, @PathVariable String ora, @ModelAttribute(value = "user") User user) {
        DB jdbc = new DB();
        jdbc.sendOrder(data, ora);
    }
}
