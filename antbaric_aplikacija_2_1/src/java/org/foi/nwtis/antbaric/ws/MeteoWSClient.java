package org.foi.nwtis.antbaric.ws;

import org.foi.nwtis.antbaric.soap.Meteo;

/**
 *
 * @author javert
 */
public class MeteoWSClient {

    public static String getDeviceAddress(java.lang.String username, java.lang.String password, java.lang.Integer device) {
        org.foi.nwtis.antbaric.soap.MeteoWS_Service service = new org.foi.nwtis.antbaric.soap.MeteoWS_Service();
        org.foi.nwtis.antbaric.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getDeviceAddress(username, password, device);
    }

    public static java.util.List<org.foi.nwtis.antbaric.soap.Meteo> getDeviceMeteoForInterval(java.lang.String username, java.lang.String password, java.lang.Integer device, java.lang.Long from, java.lang.Long to) {
        org.foi.nwtis.antbaric.soap.MeteoWS_Service service = new org.foi.nwtis.antbaric.soap.MeteoWS_Service();
        org.foi.nwtis.antbaric.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getDeviceMeteoForInterval(username, password, device, from, to);
    }

    public static Meteo getLastDeviceMeteo(java.lang.String username, java.lang.String password, java.lang.Integer device) {
        org.foi.nwtis.antbaric.soap.MeteoWS_Service service = new org.foi.nwtis.antbaric.soap.MeteoWS_Service();
        org.foi.nwtis.antbaric.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getLastDeviceMeteo(username, password, device);
    }

    public static java.util.List<org.foi.nwtis.antbaric.soap.Meteo> getLatestDeviceMeteo(java.lang.String username, java.lang.String password, java.lang.Integer device, java.lang.Integer n) {
        org.foi.nwtis.antbaric.soap.MeteoWS_Service service = new org.foi.nwtis.antbaric.soap.MeteoWS_Service();
        org.foi.nwtis.antbaric.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getLatestDeviceMeteo(username, password, device, n);
    }

    public static Meteo getLiveDeviceMeteo(java.lang.String username, java.lang.String password, java.lang.Integer device) {
        org.foi.nwtis.antbaric.soap.MeteoWS_Service service = new org.foi.nwtis.antbaric.soap.MeteoWS_Service();
        org.foi.nwtis.antbaric.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getLiveDeviceMeteo(username, password, device);
    }


}
