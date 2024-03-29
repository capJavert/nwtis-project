/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.Device;
import org.foi.nwtis.antbaric.models.Log;

/**
 * REST Web Service
 *
 * @author javert
 */
@Path("/devices")
public class DevicesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DevicesResource
     */
    public DevicesResource() {
    }

    /**
     * Retrieves representation of an instance of Device
     *
     * @return an instance of String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@Context HttpServletRequest requestContext) {
        try {
            new Log().writeUrlLog("PUBLIC", "GET /devices", requestContext.getRemoteAddr(), null);
        } catch (SQLException ex) {
            Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ArrayList<Device> devices = new Device().findAll();
            return JsonHelper.encode(devices);
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * POST method for creating an instance of DeviceResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postJson(String content, @Context HttpServletRequest requestContext) {
        Device device = JsonHelper.decode(content, Device.class);
        device.status = 0;

        try {
            new Log().writeUrlLog("PUBLIC", "POST /devices", requestContext.getRemoteAddr(), null);
        } catch (SQLException ex) {
            Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return device.create() ? "1" : "0";
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * Sub-resource locator method for {id}
     *
     * @param id
     * @return
     */
    @Path("{id}")
    public DeviceResource getDeviceResource(@PathParam("id") String id) {
        return DeviceResource.getInstance(id);
    }
}
