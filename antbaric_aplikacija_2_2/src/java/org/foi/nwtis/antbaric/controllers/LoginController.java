package org.foi.nwtis.antbaric.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.services.UserService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "loginController")
public class LoginController {

    private UserService service;
    private String username;
    private String password;
    private ServletContext context;

    @PostConstruct
    public void init() {
        this.context = (ServletContext) ApplicationListener.getContext();
        
        this.service = new UserService();
    }

    public Boolean login(final String username, final String password) {
        User model = this.service.get(username);
        UserAuth userAuth = (UserAuth) this.context.getAttribute("user");

        if (model != null) {
            if (model.getPassword().equals(password)) {
                userAuth.setUser(model);

                return true;
            }
        }

        return false;
    }

    public Boolean register(final String username, final String password, String name) {
        User model = new User();
        model.setUsername(username);
        model.setPassword(password);
        model.setName(name);

        return this.service.create(model);
    }

    public String signIn() {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        UserAuth userAuth = (UserAuth) servletContext.getAttribute("user");

        return this.login(this.username, this.password)
                ? "SIGNIN_SUCCESS" : "SIGNIN_ERROR";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
