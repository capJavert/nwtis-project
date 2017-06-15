package org.foi.nwtis.antbaric.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.foi.nwtis.antbaric.beans.UserAuth;

/**
 *
 * @author javert
 */
@ManagedBean(name = "loginController")
public class LoginController {

    @EJB
    private UserAuth userAuth;

    private String username;
    private String password;

    @PostConstruct
    public void init() {
    }

    public String signIn() {
        return this.userAuth.login(this.username, this.password) ? 
                "SIGNIN_SUCCESS" : "SIGNIN_ERROR";
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
