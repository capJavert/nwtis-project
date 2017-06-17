package org.foi.nwtis.antbaric.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.beans.Dnevnik;
import org.foi.nwtis.antbaric.beans.DnevnikFacade;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.services.UserService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "logController")
@RequestScoped
public class LogController extends Controller<Dnevnik> implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;

    private UserAuth userAuth;

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");
        
        if(this.userAuth == null) {
            this.toLogin();
        }

        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        this.service = new UserService();
        this.list = new ArrayList<>(dnevnikFacade.findAll());
    }

}
