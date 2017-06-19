/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.antbaric.services.Service;

/**
 *
 * @author javert
 * @param <T>
 */
public abstract class Controller<T> {

    protected Integer pagination;
    protected ArrayList<T> list;
    protected Service service;
    protected T model;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    protected String message = "";

    public String getMessage() {
        return message;
    }

    public Integer getPagination() {
        return pagination;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public T getModel() {
        return this.model;
    }

    protected void toLogin() {
        try {
            externalContext.redirect("/antbaric_aplikacija_3_2/view/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void toIndex() {
        try {
            externalContext.redirect("/antbaric_aplikacija_3_2/view/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
