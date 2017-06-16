package org.foi.nwtis.antbaric.controllers;

import java.util.Map;
import javax.annotation.PostConstruct;
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
public class UserController extends Controller<User> {

    private String username;
    
    @PostConstruct
    public void init() {
        if(this.getUserAuth().isGuest()) {
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
            this.getUser(id);
        }
    }

    public UserAuth getUserAuth() {
        return null;
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
        this.service.create(this.model);
    }

    public void update() {
        System.out.println(this.service.update(username, this.model).toString());
    }

}
