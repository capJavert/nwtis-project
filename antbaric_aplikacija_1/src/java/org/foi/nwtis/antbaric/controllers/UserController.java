package org.foi.nwtis.antbaric.controllers;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "userController")
@RequestScoped
public class UserController extends Controller<User> {

    @PostConstruct
    public void init() {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        try {
            this.list = new User().findAll();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
