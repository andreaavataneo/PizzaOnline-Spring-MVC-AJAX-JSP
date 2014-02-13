/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.service.DB;
import com.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Andrea
 */
@Controller
public class Mycontroller {
    
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public ModelAndView welcome(){
        ModelAndView mav = new ModelAndView("index");
        String s = "Benvenuto nella Pizzeria Nico&Andre, potrai consultare il menu&grave che offriamo. Se vorrai effettuare"
                + "un'ordinazione dovrai entrare nell'area clienti. Se non sei cliente, Registrati!";
        mav.addObject("helloMessage", s);
        return mav;
    }
    
    @RequestMapping(value="/Catalog", method = RequestMethod.GET)
    public ModelAndView catalog(){
        DB jdbc = new DB();
        ModelAndView maw =new ModelAndView("Catalog");
        String menu = jdbc.showMenu();
        return maw.addObject("menu", menu);
    } 
    
    @RequestMapping(value="/LogIn", method = RequestMethod.GET)
    public ModelAndView logIn(){
        ModelAndView maw = new ModelAndView("LogIn");        
        maw.addObject("user", new User()); //creo il bean necessario al form di LogIn e lo aggiungo al ModelAndView
        return maw;
    }
    
    
    
    @RequestMapping(value="/addUser", method=RequestMethod.GET)
    public ModelAndView addUser(){
        ModelAndView maw = new ModelAndView();
        String s = "Comila tutti i campi per poter effettuare la registrazione al nostro sito.";
        maw.addObject("user", new User());
        return maw.addObject("message", s);
    }
    
   
}

