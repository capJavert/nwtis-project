package org.foi.nwtis.antbaric.controllers;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.antbaric.beans.UserAuth;

/**
 *
 * @author javert
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UserAuth userAuth;

    private String username;
    private String password;

    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    @PostConstruct
    public void init() {
    }

    public String signIn() {
        if (this.userAuth.login(this.username, this.password)) {
            request.getSession().setAttribute("user", this.userAuth);
            
            return "SIGNIN_SUCCESS";
        }
        
        return "SIGNIN_ERROR";
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
