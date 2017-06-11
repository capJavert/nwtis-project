package org.foi.nwtis.antbaric.components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 * Managing database connection across the application
 *
 * @author javert
 */
public class DbManager {

    private static Connection connection;

    public DbManager() {
        if (DbManager.getConnection() == null) {
            ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
            BP_Konfiguracija config = (BP_Konfiguracija) servletContext.getAttribute("db-config");

            try {
                Class.forName(config.getDriverDatabase()).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                DbManager.connection = DriverManager.getConnection(config.getServerDatabase() + config.getUserDatabase() + "?characterEncoding=utf8",
                        config.getUserUsername(),
                        config.getUserPassword());
            } catch (SQLException ex) {
                Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
