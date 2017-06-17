package org.foi.nwtis.antbaric.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.components.Izbornik;

@Named(value = "localizationController")
@RequestScoped
public class LocalizationController implements Serializable {

    private UserAuth userAuth;
    static final ArrayList<Izbornik> LANGUAGES = new ArrayList<>();
    private static String selectedLanguage = "hr";
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    static {
        LANGUAGES.add(new Izbornik("Hrvatski", "hr"));
        LANGUAGES.add(new Izbornik("English", "en"));
    }

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");
    }

    public UserAuth getUserAuth() {
        return (UserAuth) request.getSession().getAttribute("user");
    }

    public Object selectLanguage() {
        setSelectedLanguage(selectedLanguage);

        return "SELECT_LANGUAGE";
    }

    public ArrayList<Izbornik> getLANGUAGES() {
        return LANGUAGES;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLang) {
        this.selectedLanguage = selectedLang;
        Locale localLanguage = new Locale(selectedLang);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(localLanguage);
    }
}
