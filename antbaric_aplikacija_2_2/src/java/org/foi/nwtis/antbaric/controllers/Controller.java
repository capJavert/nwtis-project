/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.controllers;

import java.util.ArrayList;
import java.util.Map;
import javax.faces.context.FacesContext;
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

    public Integer getPagination() {
        return pagination;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public T getModel() {
        return this.model;
    }

}
