package org.foi.nwtis.antbaric.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
@ManagedBean(name = "userController")
@RequestScoped
public class UserController extends Controller<User> implements Serializable {

    private String username = null;
    private UserAuth userAuth;

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");
        
        if(this.userAuth == null) {
            this.toLogin();
        }

        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        this.service = new UserService();
        this.list = this.service.get();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");

        if (id != null) {
            if(!id.equals(this.userAuth.getUser().getUsername())) {
                this.toIndex();
            }
            this.getUser(id);
        } else {
            this.model = new User();
        }
    }

    public String getUsername() {
        return username;
    }

    public UserAuth getUserAuth() {
        return userAuth;
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
        System.out.println(this.service.create(this.model).toString());
        
        try {
            externalContext.redirect("/antbaric_aplikacija_2_2/view/users.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        System.out.println(this.service.update(username, this.model).toString());
    }

}
