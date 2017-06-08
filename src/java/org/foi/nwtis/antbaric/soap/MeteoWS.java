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
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.antbaric.models.Meteo;

/**
 *
 * @author javert
 */
@WebService(serviceName = "MeteoWS")
public class MeteoWS {

    @WebMethod(operationName = "getLastDeviceMeteo")
    public Meteo getLastDeviceMeteo(@WebParam(name = "device") final Integer deviceId) {
        try {
            return new Meteo().findOne("id", deviceId);
        } catch (SQLException ex) {
            Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    @WebMethod(operationName = "getLatestDeviceMeteo")
    public ArrayList<Meteo> getLatestDeviceMeteo(@WebParam(name = "device") final Integer deviceId, @WebParam(name = "N") final Integer N) {
        try {
            return new Meteo().findAll("id", deviceId, N);
        } catch (SQLException ex) {
            Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    @WebMethod(operationName = "getDeviceMeteoForInterval")
    public ArrayList<Meteo> getDeviceMeteoForInterval(@WebParam(name = "device") final Integer deviceId,
            @WebParam(name = "from") final Long fromInterval,
            @WebParam(name = "to") final Long toInterval) {
        
        try {
            return new Meteo().findAll("id", deviceId, new Timestamp(fromInterval), new Timestamp(toInterval));
        } catch (SQLException ex) {
            Logger.getLogger(MeteoWS.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    @WebMethod(operationName = "getLiveDeviceMeteo")
    public ArrayList<Meteo> getLiveDeviceMeteo(@WebParam(name = "device") final Integer deviceId) {
        // TODO: fetch data from openweathermap.org API

        return null;
    }

    @WebMethod(operationName = "getDeviceAddress")
    public ArrayList<Meteo> getDeviceAddress(@WebParam(name = "device") final Integer deviceId) {
        // TODO: fetch address from google.com API

        return null;
    }
}
