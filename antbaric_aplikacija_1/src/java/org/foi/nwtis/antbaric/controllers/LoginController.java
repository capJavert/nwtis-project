package org.foi.nwtis.antbaric.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author javert
 */
@ManagedBean(name = "loginController")
public class LoginController {

    private String username;
    private String password;

    @PostConstruct
    public void init() {
    }

    public String signIn() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(this.username, this.password);
        } catch (ServletException ex) {
            return "SIGNIN_ERROR";
        }

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        try {
            request.authenticate(response);
        } catch (IOException | ServletException ex) {
            return "SIGNIN_ERROR";
        }

        return "SIGNIN_SUCCESS";
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
