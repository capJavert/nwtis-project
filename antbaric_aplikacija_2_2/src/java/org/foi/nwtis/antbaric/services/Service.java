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

    public T get(Integer id) {
        WebTarget webResource = client.target(this.GM_BASE_URI)
                .path(id.toString());

        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

        return this.decodeObject(response);
    }

    public ArrayList<T> get() {
        WebTarget webResource = client.target(this.GM_BASE_URI);

        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

        return this.decodeObjects(response);
    }

    public Boolean create(T model) {
        WebTarget webResource = client.target(this.GM_BASE_URI);

        String payload = this.encodeObject(model);

        String response = webResource.request(MediaType.APPLICATION_JSON).post(Entity.entity(payload, MediaType.APPLICATION_JSON)).toString();

        return response.equals("1");
    }

    public Boolean update(T model) {
        WebTarget webResource = client.target(this.GM_BASE_URI);

        String payload = this.encodeObject(model);

        String response = webResource.request(MediaType.APPLICATION_JSON).put(Entity.entity(payload, MediaType.APPLICATION_JSON)).toString();

        return response.equals("1");
    }
    
    public Boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected String encodeObject(T model) {
        return JsonHelper.encode(model);
    }

    public abstract T decodeObject(String response);

    public abstract ArrayList<T> decodeObjects(String response);
}
