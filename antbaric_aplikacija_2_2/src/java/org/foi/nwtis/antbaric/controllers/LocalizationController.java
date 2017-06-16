package org.foi.nwtis.antbaric.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.foi.nwtis.antbaric.components.Izbornik;

@Named(value = "localizationController")
@SessionScoped
public class LocalizationController implements Serializable
{
    static final ArrayList<Izbornik> LANGUAGES = new ArrayList<>();
    private String selectedLanguage;
    
    static 
    {
        LANGUAGES.add(new Izbornik("Hrvatski", "hr"));
        LANGUAGES.add(new Izbornik("English", "en"));
    }
    
    public LocalizationController()
    {
    }

    public Object selectLanguage()
    {
        setSelectedLanguage(selectedLanguage);
        
        return "SELECT_LANGUAGE";
    }

    public ArrayList<Izbornik> getLANGUAGES() {
        return LANGUAGES;
    }

    public String getSelectedLanguage()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if(viewRoot != null)
        {
            Locale localLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            selectedLanguage = localLanguage.getLanguage();
        }
        
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLang)
    {
        this.selectedLanguage = selectedLang;
        Locale localLanguage = new Locale(selectedLang);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(localLanguage);
    }
}
