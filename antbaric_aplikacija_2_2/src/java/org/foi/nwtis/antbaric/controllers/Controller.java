/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.controllers;

import java.util.ArrayList;
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
    
    public Integer getPagination() {
        return pagination;
    }

    public ArrayList<T> getList() {
        return list;
    }
    
}
