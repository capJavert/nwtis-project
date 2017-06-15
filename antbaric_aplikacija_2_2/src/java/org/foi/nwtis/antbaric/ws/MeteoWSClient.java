/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.ws;

/**
 *
 * @author javert
 */
public class MeteoWSClient {

    private static String getDeviceAddress(java.lang.Integer device) {
        org.foi.nwtis.antbaric.ws.MeteoWS_Service service = new org.foi.nwtis.antbaric.ws.MeteoWS_Service();
        org.foi.nwtis.antbaric.ws.MeteoWS port = service.getMeteoWSPort();
        return port.getDeviceAddress(device);
    }

    private static java.util.List<org.foi.nwtis.antbaric.ws.Meteo> getDeviceMeteoForInterval(java.lang.Integer device, java.lang.Long from, java.lang.Long to) {
        org.foi.nwtis.antbaric.ws.MeteoWS_Service service = new org.foi.nwtis.antbaric.ws.MeteoWS_Service();
        org.foi.nwtis.antbaric.ws.MeteoWS port = service.getMeteoWSPort();
        return port.getDeviceMeteoForInterval(device, from, to);
    }

    private static Meteo getLastDeviceMeteo(java.lang.Integer device) {
        org.foi.nwtis.antbaric.ws.MeteoWS_Service service = new org.foi.nwtis.antbaric.ws.MeteoWS_Service();
        org.foi.nwtis.antbaric.ws.MeteoWS port = service.getMeteoWSPort();
        return port.getLastDeviceMeteo(device);
    }

    private static java.util.List<org.foi.nwtis.antbaric.ws.Meteo> getLatestDeviceMeteo(java.lang.Integer device, java.lang.Integer n) {
        org.foi.nwtis.antbaric.ws.MeteoWS_Service service = new org.foi.nwtis.antbaric.ws.MeteoWS_Service();
        org.foi.nwtis.antbaric.ws.MeteoWS port = service.getMeteoWSPort();
        return port.getLatestDeviceMeteo(device, n);
    }

    private static Meteo getLiveDeviceMeteo(java.lang.Integer device) {
        org.foi.nwtis.antbaric.ws.MeteoWS_Service service = new org.foi.nwtis.antbaric.ws.MeteoWS_Service();
        org.foi.nwtis.antbaric.ws.MeteoWS port = service.getMeteoWSPort();
        return port.getLiveDeviceMeteo(device);
    }
    
}
