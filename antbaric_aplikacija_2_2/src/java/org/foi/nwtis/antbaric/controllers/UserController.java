package org.foi.nwtis.antbaric.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.services.UserService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "userController")
public class UserController extends Controller<User> {

    @PostConstruct
    public void init() {
       ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        this.service = new UserService();
        this.list = this.service.get();
    }

}
