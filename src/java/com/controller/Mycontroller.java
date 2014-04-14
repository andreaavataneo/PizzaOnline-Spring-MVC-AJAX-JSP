/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.pizze.Pizza;
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

    private String guestMenu = "<a class='button' href=\"index.htm\">Home</a>\n"
            + "<a class='button' href=\"LogIn.htm\">Log In</a>\n"
            + "<a class='button' href=\"addUser.htm\">Iscriviti</a>";
    private String clientMenu = "<a class='button' href=\"index.htm\">Home</a>\n"
            + "<a class='button' href=\"ClientArea.htm\">Area Clienti</a>\n"
            + "<a class='button' href=\"LogOut.htm\">Log Out</a>";
    private String adminMenu = "<a class='button' href=\"index.htm\">Home</a>    \n"
            + "<a class='button' href=\"ClientArea.htm\">Area Clienti</a>\n"
            + "<a class='button' href=\"admin.htm\">Area Admin</a>\n"
            + "<a class='button' href=\"orders.htm\">Area Ordini</a>\n"
            + "<a class='button' href=\"LogOut.htm\">Log Out</a>";

    @ModelAttribute("user")
    public User getUserObject() {
        return new User();
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView welcome(@ModelAttribute(value = "user") User user) {
        ModelAndView maw = new ModelAndView("index");
        DB jdbc = new DB();
        String menu = jdbc.showMenu();
        String s = "Benvenuto nella Pizzeria Nico&amp;Andre, potrai consultare qui sotto il men&ugrave; che offriamo.<br>Se vorrai effettuare"
                + " un'ordinazione dovrai entrare nell'area clienti. Se non sei cliente, Registrati!"
                + "<br><br>I nostri fattorini consegnano dalle 19 alle 23 e siamo aperti tutti i giorni.";
        maw.addObject("helloMessage", s);
        maw.addObject("menu", menu);
        maw.addObject("session", user);
        if (user.getTypeRole().equals("admin")) {
            maw.addObject("menuType", adminMenu);
        } else if (user.getTypeRole().equals("client")) {
            maw.addObject("menuType", clientMenu);
        } else {
            maw.addObject("menuType", guestMenu);
        }
        return maw;
    }

    @RequestMapping(value = "/LogIn", method = RequestMethod.GET)
    public ModelAndView logIn(@ModelAttribute(value = "user") User user) {
        ModelAndView maw = new ModelAndView("LogIn");
        if (user.getEmail().equals("ospite")) {
            maw.addObject("menuType", guestMenu);
            maw.addObject("session", user);
            maw.addObject("user", new User()); //creo il bean necessario al form di LogIn e lo aggiungo al ModelAndView            
        } else {
            maw = welcome(user);
        }
        return maw;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView addUser(@ModelAttribute(value = "user") User user, @ModelAttribute(value = "s_user") User s_user) {
        ModelAndView maw = new ModelAndView();
        if (user.getEmail().equals("ospite")) {
            maw.addObject("session", user);
            maw.addObject("user", new User());
            maw.addObject("menuType", guestMenu);
        } else {
            maw = welcome(user);
        }
        return maw;
    }

    @RequestMapping(value = "/LogOut", method = RequestMethod.GET)
    public String logOut(SessionStatus status) {
        status.setComplete();
        return "redirect:/index.htm";
    }

    @RequestMapping(value = "/ClientArea", method = RequestMethod.GET)
    public ModelAndView clientArea(@ModelAttribute(value = "user") User user) {
        ModelAndView maw = new ModelAndView("ClientArea");
        if (user.getEmail().equals("ospite")) {
            maw = welcome(user);
        } else {
            DB jdbc = new DB();
            maw.addObject("order", jdbc.menuOrder());
            maw.addObject("message", "Pagina di ordinazione");
            maw.addObject("user", user);
            maw.addObject("todayClientOrders", jdbc.todayClientOrders(user.getId_u()));
            maw.addObject("nextClientOrders", jdbc.nextClientOrders(user.getId_u()));
            if (user.getTypeRole().equals("client")) {
                maw.addObject("menuType", clientMenu);
            } else {
                maw.addObject("menuType", adminMenu);
            }
        }
        return maw;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminArea(@ModelAttribute(value = "user") User user, @ModelAttribute(value = "pizza") Pizza pizza) {
        ModelAndView maw = new ModelAndView("admin");
        if (user.getTypeRole().equals("admin")) {
            DB jdbc = new DB();
            maw.addObject("allP", jdbc.listPizzas());
            maw.addObject("pizza", new Pizza());
            //maw.addObject("helloMessage", "Area riservata agli amministratori, aggiunta/modifica/rimozione pizze");
            maw.addObject("menuType", adminMenu);
        } else {
            maw = welcome(user);
        }
        return maw;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ModelAndView orders(@ModelAttribute(value = "user") User user) {
        ModelAndView maw = new ModelAndView("orders");
        if (user.getTypeRole().equals("admin")) {
            DB jdbc = new DB();
            maw.addObject("todayTask", jdbc.todayTask());
            maw.addObject("nextTask", jdbc.nextTask());
            maw.addObject("menuType", adminMenu);
        } else {
            maw = welcome(user);
        }
        return maw;
    }
}
