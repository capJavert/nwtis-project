package org.foi.nwtis.antbaric.web.listeners;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.antbaric.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.antbaric.konfiguracije.bp.BP_Konfiguracija;

/**
 * Application listener
 *
 * @author javert
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    public static ServletContext context = null;
    Konfiguracija config;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        String datoteka = context.getRealPath("/WEB-INF")
                + File.separator
                + context.getInitParameter("configuration");

        BP_Konfiguracija dbConfig = new BP_Konfiguracija(datoteka);
        context.setAttribute("db-config", dbConfig);
        System.out.println("Učitana konfiguacija");
        
        try {
            config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            sce.getServletContext().setAttribute("main-config", config);

        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    public static ServletContext getContext() {
        return context;
    }

    private UserAuth lookupUserAuthBean() {
        try {
            Context c = new InitialContext();
            return (UserAuth) c.lookup("java:global/antbaric_aplikacija_2/antbaric_aplikacija_2_1/UserAuth!org.foi.nwtis.antbaric.beans.UserAuth");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
