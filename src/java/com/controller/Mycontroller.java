/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.service.DB;
import com.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Andrea
 */
@Controller
@SessionAttributes("user")
public class Mycontroller {

    @ModelAttribute("user")
    public User getUserObject() {
        return new User();

    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView welcome(@ModelAttribute(value = "user") User user) {
        ModelAndView maw = new ModelAndView("index");
        DB jdbc = new DB();
        String menu = jdbc.showMenu();
        String s = "Benvenuto nella Pizzeria Nico&Andre, potrai consultare il menu&grave; che offriamo. Se vorrai effettuare"
                + " un'ordinazione dovrai entrare nell'area clienti. Se non sei cliente, Registrati!";
        maw.addObject("helloMessage", s);
        maw.addObject("menu", menu);
        maw.addObject("session", user.getEmail());
        return maw;
    }    

    @RequestMapping(value = "/LogIn", method = RequestMethod.GET)
    public ModelAndView logIn(@ModelAttribute User user) {
        ModelAndView maw = new ModelAndView("LogIn");        
        if (user.getEmail().equals("ospite")) {
            maw.addObject("message", "Effettua l'accesso inserendo la tua mail e la password, altrimenti vai alla pagina di iscrizione.");
            maw.addObject("session", user.getEmail());
            maw.addObject("user", new User()); //creo il bean necessario al form di LogIn e lo aggiungo al ModelAndView
            return maw;
        } else {
            DB jdbc = new DB();
            maw.addObject("message", "Hai già effettuato il Log In a nome: "+user.getEmail());
            maw.addObject("session", user.getEmail());            
            return maw;
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView addUser(@ModelAttribute User user) {
        ModelAndView maw = new ModelAndView();
        String s = "Comila tutti i campi per poter effettuare la registrazione al nostro sito.";
        maw.addObject("session", user.getEmail());
        maw.addObject("user", new User());
        return maw.addObject("message", s);
    }
    
    @RequestMapping(value = "/LogOut", method = RequestMethod.GET)
    public String logOut(SessionStatus status){
        status.setComplete();
        return "redirect:/index.htm";
    }
    
    @RequestMapping(value="/ClientArea", method = RequestMethod.GET)
    public ModelAndView clientArea(){
        ModelAndView maw = new ModelAndView("ClientArea");
        DB jdbc = new DB();
        maw.addObject("order",jdbc.menuOrder());
        maw.addObject("message","Pagina di ordinazione");
        return maw;
    }
}
