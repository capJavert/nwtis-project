package org.foi.nwtis.antbaric.services;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.antbaric.components.OWResponse;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.Meteo;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

public class OpenWeatherService {

    private static final String OWM_BASE_URI = "http://api.openweathermap.org/data/2.5/";
    private final String apiKey;
    Client client;

    public static String getBaseUrl() {
        return OWM_BASE_URI;
    }

    public static String getWeatherPath() {
        return "weather";
    }

    public static String ggetForecastPath() {
        return "forecast";
    }

    public static String getForecastDailyPath() {
        return "forecast/daily";
    }

    public static String getNearbyStationsPath() {
        return "station/find";
    }

    public static String getStationPath() {
        return "station";
    }

    public OpenWeatherService() {
        ServletContext context = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) context.getAttribute("main-config");
        this.apiKey = config.dajPostavku("apikey");

        client = ClientBuilder.newClient();
    }

    public Meteo getRealTimeWeather(String latitude, String longitude) {
        WebTarget webResource = client.target(OpenWeatherService.OWM_BASE_URI)
                .path(OpenWeatherService.getWeatherPath());
        webResource = webResource.queryParam("lat", latitude);
        webResource = webResource.queryParam("lon", longitude);
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", this.apiKey);

        try {
            String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            OWResponse responseObject = JsonHelper.decode(response, OWResponse.class);

            Meteo meteo = new Meteo();

            meteo.latitude = Double.parseDouble(latitude);
            meteo.longitude = Double.parseDouble(longitude);
            meteo.preuzeto = new Timestamp(System.currentTimeMillis()).toString();
            meteo.temp = responseObject.getTemp();
            meteo.temp_max = responseObject.getTempMax();
            meteo.temp_min = responseObject.getTempMin();
            meteo.tlak = responseObject.getPreassure();
            meteo.vjetar = responseObject.getWind();
            meteo.vjetar_smjer = responseObject.getWindDirection();
            meteo.vlaga = responseObject.getHumidity();
            meteo.vrijeme = responseObject.getWeather();
            meteo.vrijeme_opis = responseObject.getWeatherDescription();
            
            GoogleMapsService googleMapsService = new GoogleMapsService();
            meteo.adresa_stanice = googleMapsService.getAddress(longitude, latitude);

            return meteo;
        } catch (BadRequestException ex) {
            Logger.getLogger(OpenWeatherService.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new NullPointerException();
        }
    }
}
