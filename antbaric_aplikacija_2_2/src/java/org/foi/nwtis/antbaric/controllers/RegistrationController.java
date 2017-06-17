package org.foi.nwtis.antbaric.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.services.UserService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "registrationController")
@RequestScoped
public class RegistrationController extends Controller<User> implements Serializable {

    private String username = null;
    private UserAuth userAuth;
        
    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");
        
        if(this.userAuth != null) {
            this.toIndex();
        }

        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        this.service = new UserService();
        this.list = this.service.get();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");

        if (id != null) {
            this.getUser(id);
        } else {
            this.model = new User();
        }
    }

    public String getUsername() {
        return username;
    }

    private void getUser(String id) {
        for (User user : this.list) {
            if (user.getUsername().equals(id)) {
                this.model = user;
                this.username = user.getUsername();
            }
        }
    }

    public void create() {
        if(this.service.create(this.model)) {
            this.message = null;
            this.toIndex();
        } else {
            this.message = "Došlo je do pogreške kod registracija";
        }
        
    }

    public void update() {
        System.out.println(this.service.update(username, this.model).toString());
    }

}
