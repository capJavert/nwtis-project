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
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.User;

/**
 * REST Web Service
 *
 * @author javert
 */
public class UserResource {

    private final String id;

    /**
     * Creates a new instance of UserResource
     */
    private UserResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the UserResource
     *
     * @param id
     * @return
     */
    public static UserResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of UserResource class.
        return new UserResource(id);
    }

    /**
     * Retrieves representation of an instance of User
     *
     * @return an instance of String
     */
    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            User user = (User) new User().findOne("username", this.id);

            return user != null ? JsonHelper.encode(user) : JsonHelper.encode(new ErrorREST("Not found"));
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     *
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String putJson(String content) {
        User user = JsonHelper.decode(content, User.class);

        try {
            User check = new User().findOne(user.getPrimaryKey());
            if(check != null && check.username.equals(this.id)) {
                user.update();
                
                return "1";
            }
        } catch (SQLException ex) {
            return JsonHelper.encode(new ErrorREST(ex.getMessage()));
        }
        
        return "0";
    }

    /**
     * DELETE method for resource UserResource
     */
    @DELETE
    public void delete() {
    }
}
