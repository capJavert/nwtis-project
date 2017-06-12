package org.foi.nwtis.antbaric.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.Log;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "wsLogController")
@ViewScoped
public class WSLogController extends Controller<Log> {

    @PostConstruct
    public void init() {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        try {
            this.list = new Log().findAll();
        } catch (SQLException ex) {
            Logger.getLogger(WSLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
