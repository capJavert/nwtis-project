/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.soap;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.antbaric.models.Device;
import org.foi.nwtis.antbaric.models.Log;
import org.foi.nwtis.antbaric.models.Meteo;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.rest.DeviceResource;
import org.foi.nwtis.antbaric.services.GoogleMapsService;
import org.foi.nwtis.antbaric.services.OpenWeatherService;

/**
 *
 * @author javert
 */
@WebService(serviceName = "MeteoWS")
public class MeteoWS {

    @Resource
    WebServiceContext wsContext;

    @WebMethod(operationName = "getLastDeviceMeteo")
    public Meteo getLastDeviceMeteo(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
            @WebParam(name = "device") final Integer deviceId) {

        if (new User().authenticate(username, password) != null) {
            this.log("getLastDeviceMeteo", username);
            
            try {
                return new Meteo().findOne("id", deviceId);
            } catch (SQLException ex) {
                Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.log("getLastDeviceMeteo", "PUBLIC");
        }

        return null;
    }

    @WebMethod(operationName = "getLatestDeviceMeteo")
    public ArrayList<Meteo> getLatestDeviceMeteo(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
            @WebParam(name = "device") final Integer deviceId, @WebParam(name = "N") final Integer N) {

        if (new User().authenticate(username, password) != null) {
            this.log("getLatestDeviceMeteo", username);
            
            try {
                return new Meteo().findAll("id", deviceId, N);
            } catch (SQLException ex) {
                Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.log("getLatestDeviceMeteo", "PUBLIC");
        }

        return null;
    }

    @WebMethod(operationName = "getDeviceMeteoForInterval")
    public ArrayList<Meteo> getDeviceMeteoForInterval(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
            @WebParam(name = "device") final Integer deviceId,
            @WebParam(name = "from") final Long fromInterval,
            @WebParam(name = "to") final Long toInterval) {

        if (new User().authenticate(username, password) != null) {
            this.log("getDeviceMeteoForInterval", username);
            
            try {
                return new Meteo().findAll("id", deviceId, new Timestamp(fromInterval), new Timestamp(toInterval));
            } catch (SQLException ex) {
                Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.log("getDeviceMeteoForInterval", "PUBLIC");
        }

        return null;
    }

    @WebMethod(operationName = "getLiveDeviceMeteo")
    public Meteo getLiveDeviceMeteo(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
            @WebParam(name = "device") final Integer deviceId) {

        if (new User().authenticate(username, password) != null) {
            this.log("getLiveDeviceMeteo", username);
            
            try {
                Device device = new Device().findOne(deviceId);

                OpenWeatherService openWeatherService = new OpenWeatherService();

                Meteo meteo = openWeatherService.getRealTimeWeather(device.latitude.toString(), device.longitude.toString());
                meteo.id = deviceId;

                return meteo;
            } catch (SQLException ex) {
                Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.log("getLiveDeviceMeteo", "PUBLIC");
        }

        return null;
    }

    @WebMethod(operationName = "getDeviceAddress")
    public String getDeviceAddress(@WebParam(name = "username") final String username, @WebParam(name = "password") final String password,
            @WebParam(name = "device") final Integer deviceId) {

        if (new User().authenticate(username, password) != null) {
            this.log("getDeviceAddress", username);
            
            try {
                Device device = new Device().findOne(deviceId);

                GoogleMapsService googleMapsService = new GoogleMapsService();

                return googleMapsService.getAddress(device.longitude.toString(), device.latitude.toString());
            } catch (SQLException ex) {
                Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.log("getDeviceAddress", "PUBLIC");
        }

        return null;
    }

    private void log(String operationName, String username) {
        try {
            MessageContext mc = wsContext.getMessageContext();
            HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
            
            new Log().writeUrlLog(username, operationName, req.getRemoteAddr(), null);
        } catch (SQLException ex) {
            Logger.getLogger(DeviceResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
