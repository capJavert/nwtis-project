package org.foi.nwtis.antbaric.web.listeners;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.antbaric.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.antbaric.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.antbaric.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.antbaric.socket.Server;
import org.foi.nwtis.antbaric.threads.MeteoFetcher;

/**
 * Application listener
 *
 * @author javert
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    public static ServletContext context = null;
    private static MeteoFetcher meteoFetcher = null;
    private static Server socketServer = null;
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

        meteoFetcher = new MeteoFetcher();
        meteoFetcher.start();

        socketServer = new Server();
        socketServer.start(); 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (meteoFetcher != null) {
            meteoFetcher.interrupt();
        }
        
        if (socketServer != null) {
            socketServer.interrupt();
        }
    }

    public static ServletContext getContext() {
        return context;
    }

    public static void killMeteoFetcher() {
        meteoFetcher.interrupt();
    }

    public static void setPause(Boolean paused) {
        meteoFetcher.setPaused(paused);
    }
}
