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
    
    /**
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value="/LogIn", method = RequestMethod.POST) 
    @ResponseBody  
    public  String logger(@ModelAttribute(value="user") User user, BindingResult result){
        String returnText;
        DB jdbc = new DB(); //istanzio un oggetto DB
        if(result.hasErrors()){returnText="Errore nell'interazione Ajax.";}
        else if(jdbc.logger(user.getEmail(), user.getPwd())){ returnText=user.getEmail()+" hai effettuato l'acceso con successo!"; }//messaggio di conferma
        else{returnText="Nome utente o password "+user.getPwd()+" errati!";}                    //messaggio di errore
        return returnText;  
    }
    
    @RequestMapping(value="/addUser", method=RequestMethod.GET)
    public ModelAndView addUser(){
        ModelAndView maw = new ModelAndView();
        String s = "Comila tutti i campi per poter effettuare la registrazione al nostro sito.";
        maw.addObject("user", new User());
        return maw.addObject("message", s);
    }
    
    @RequestMapping(value="/addUser", method=RequestMethod.GET)
    @ResponseBody
    public String addUser(@ModelAttribute(value="user") User user, BindingResult result){
      String returnText;
      if(user.getAddress()==null){returnText="Non hai inserito l'indirizzo!";}
      else if(user.getEmail()==null){returnText="Non hai inserito l'email!";}      
      else if(user.getPwd()==null){returnText="Non hai inseito la password";}
      else if(user.getName()==null){returnText="Non hai inserito il nome!";}
      else{
          DB jdbc = new DB();
          if(jdbc.checkMail(user.getEmail())){
          jdbc.addUser(user.getName(), user.getEmail(), user.getAddress(), user.getPwd()); 
          returnText="Inserimento effettuato con successo!";}
          else{returnText="C'è già una registrazione con questa email!";}          
      }
      return returnText;
    }
}

