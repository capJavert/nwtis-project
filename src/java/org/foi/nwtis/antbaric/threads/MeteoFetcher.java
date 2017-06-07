package org.foi.nwtis.antbaric.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.components.DbManager;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 * Thread for fetching weather forecast data
 *
 * @author javert
 */
public class MeteoFetcher extends Thread {

    private DbManager dbManager;

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public void run() {
        this.dbManager = new DbManager();
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");
        Integer interval = Integer.parseInt(config.dajPostavku("thread.interval"));

        while (true) {
            // fetch meteo data, do other work

            try {
                //System.out.println("...");
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                Logger.getLogger(MeteoFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    /*private void addDevice(Matcher command) throws java.text.ParseException {
        try (Connection c = DriverManager.getConnection(this.dbConfig.getServerDatabase() + this.dbConfig.getUserDatabase(),
                this.dbConfig.getUserUsername(),
                this.dbConfig.getUserPassword());) {

            PreparedStatement statement = c.prepareStatement("insert into uredaji(id, naziv, latitude, longitude, status) values (?,?,?,?,?)");
            statement.setInt(1, Integer.valueOf(command.group(2)));
            statement.setString(2, command.group(3));
            statement.setDouble(3, Double.valueOf(command.group(4)));
            statement.setDouble(4, Double.valueOf(command.group(5)));
            statement.setDouble(5, 1);

            int result = statement.executeUpdate();

            System.out.println("SUCCESS: Device added'");

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }*/
}
