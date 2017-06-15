package org.foi.nwtis.antbaric.threads;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.components.DbManager;
import org.foi.nwtis.antbaric.components.Location;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.Device;
import org.foi.nwtis.antbaric.models.Meteo;
import org.foi.nwtis.antbaric.services.OpenWeatherService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 * Thread for fetching weather forecast data
 *
 * @author javert
 */
public class MeteoFetcher extends Thread {

    private Boolean run = true;
    private Boolean paused = false;

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    @Override
    public void interrupt() {
        this.run = false;
        super.interrupt();
    }

    @Override
    public void run() {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");
        Integer interval = Integer.parseInt(config.dajPostavku("thread.interval"));

        OpenWeatherService openWeatherService = new OpenWeatherService();

        while (this.run) {
            if (!this.paused) {
                try {
                    ArrayList<Device> devices = new Device().findAll();
                    List<Location> locations = new Device().getUniqueLocations();
                    DbManager.getConnection().setAutoCommit(false);

                    for (Location location : locations) {
                        Meteo meteo = openWeatherService.getRealTimeWeather(location.getLatitude(), location.getLongitude());

                        Predicate<Device> predicate = device
                                -> Objects.equals(device.longitude, Float.valueOf(location.getLongitude()))
                                && Objects.equals(device.latitude, Float.valueOf(location.getLatitude()));

                        List<Device> filteredDevices = devices.stream().filter(predicate).collect(Collectors.toList());

                        for (Device device : filteredDevices) {
                            meteo.id = device.id;
                            meteo.create();
                        }
                    }

                    DbManager.getConnection().commit();
                    DbManager.getConnection().setAutoCommit(true);
                } catch (SQLException | NullPointerException ex) {
                    Logger.getLogger(MeteoFetcher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                //System.out.println("...");
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                Logger.getLogger(MeteoFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            break; //TODO: remove before presentation
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

}
