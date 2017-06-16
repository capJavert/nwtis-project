/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.beans;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.services.UserService;

/**
 *
 * @author javert
 */
@Stateful
@LocalBean
public class UserAuth {

    private User user;

    public Boolean isGuest() {
        return user == null;
    }

    public User getUser() {
        return user;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void setUser(User user) {
        this.user = user;
    }
}
