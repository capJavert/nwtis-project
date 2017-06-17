/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.rest;

import java.sql.SQLException;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.Device;

/**
 * REST Web Service
 *
 * @author javert
 */
public class DeviceResource {
    
    private final String id;

    /**
     * Creates a new instance of DeviceResource
     */
    private DeviceResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the DeviceResource
     *
     * @param id
     * @return
     */
    public static DeviceResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of DeviceResource class.
        return new DeviceResource(id);
    }

    /**
     * Retrieves representation of an instance of Device
     *
     * @return an instance of String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            Device device = (Device) new Device().findOne(Integer.parseInt(this.id));
            
            return device != null ? JsonHelper.encode(device) : JsonHelper.encode(new ErrorREST("Not found"));
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * PUT method for updating or creating an instance of DeviceResource
     *
     * @param content representation for the resource
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String putJson(String content) {
        Device device = JsonHelper.decode(content, Device.class);
        device.status = 0;
        
        try {
            Device check = new Device().findOne(Integer.parseInt(this.id));
            if (check != null) {
                device.setOldId(check.getPrimaryKey());
                device.update();
                
                return "1";
            }
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
        
        return "0";
    }

    /**
     * DELETE method for resource DeviceResource
     */
    @DELETE
    public void delete() {
    }
}
