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

    private final UserService service;
    private User user;

    public Boolean isGuest() {
        return user == null;
    }

    public UserAuth() {
        this.service = new UserService();
    }

    public Boolean login(final String username, final String password) {
        User model = this.service.get(username);

        if (model != null) {
            if (model.getPassword().equals(password)) {
                this.user = model;

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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
