/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.rest;

/**
 *
 * @author javert
 */
public class ErrorREST {

    public ErrorREST(String message) {
        this.message = message;
    }
    
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
