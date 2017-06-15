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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.User;

/**
 * REST Web Service
 *
 * @author javert
 */
@Path("/users")
public class UsersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }

    /**
     * Retrieves representation of an instance of User
     * @return an instance of String
     */
    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            ArrayList<User> users = new User().findAll();
            return JsonHelper.encode(users);
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * POST method for creating an instance of UserResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String postJson(String content) {
        User user = JsonHelper.decode(content, User.class);
        
        try {
            return user.create() ? "1" : "0";
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * Sub-resource locator method for {id}
     * @param id
     * @return 
     */
    @Path("{korisnickoIme}")
    public UserResource getUserResource(@PathParam("korisnickoIme") String id) {
        return UserResource.getInstance(id);
    }
}
