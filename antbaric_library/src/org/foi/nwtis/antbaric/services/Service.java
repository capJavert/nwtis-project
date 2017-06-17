package org.foi.nwtis.antbaric.services;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.antbaric.helpers.JsonHelper;

/**
 *
 * @author javert
 * @param <T>
 */
public abstract class Service<T> {

    protected String GM_BASE_URI;
    protected Client client;

    public String getBaseUrl() {
        return GM_BASE_URI;
    }

    public Service() {
        client = ClientBuilder.newClient();
    }

    public T get(Object id) {
        WebTarget webResource = client.target(this.GM_BASE_URI)
                .path(id.toString());

        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

        try {
            return this.decodeObject(response);
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<T> get() {
        WebTarget webResource = client.target(this.GM_BASE_URI);

        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

        try {
            return this.decodeObjects(response);
        } catch (Exception ex) {
            return null;
        }
    }

    public Boolean create(T model) {
        WebTarget webResource = client.target(this.GM_BASE_URI);

        String payload = this.encodeObject(model);

        String response = webResource.request(MediaType.APPLICATION_JSON).post(Entity.entity(payload, MediaType.APPLICATION_JSON)).readEntity(String.class);;
        
        return response.equals("1");
    }

    public Boolean update(String identifier, T model) {
        WebTarget webResource = client.target(this.GM_BASE_URI).path(identifier);

        String payload = this.encodeObject(model);

        String response = webResource.request(MediaType.APPLICATION_JSON).put(Entity.entity(payload, MediaType.APPLICATION_JSON)).readEntity(String.class);
        
        return response.equals("1");
    }

    public Boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected String encodeObject(T model) {
        String data = JsonHelper.encode(model);
        
        System.out.println(data);
        
        return data;
    }

    public abstract T decodeObject(String response);

    public abstract ArrayList<T> decodeObjects(String response);
}
