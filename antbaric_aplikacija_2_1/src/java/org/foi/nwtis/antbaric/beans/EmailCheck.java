/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.antbaric.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.antbaric.threads.EmailFetcher;

/**
 *
 * @author javert
 */
@Startup
@Singleton
@LocalBean
public class EmailCheck {

    private static Konfiguracija config;
    private EmailFetcher emailFetcher;

    public static Konfiguracija getConfig() {
        return config;
    }
    
    @PostConstruct
    void init() {
        try {
            config = KonfiguracijaApstraktna.preuzmiKonfiguraciju("/Users/javert/Documents/fax/nwtis/projekt/nwtis-project/antbaric_aplikacija_2_1/src/conf/module-config.xml");

            // TODO: uncomment before presentation
            //this.emailFetcher = new EmailFetcher();
            //this.emailFetcher.start(); 
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(EmailCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    void end() {
        if (this.emailFetcher != null) {
            this.emailFetcher.interrupt();
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
