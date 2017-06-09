package org.foi.nwtis.antbaric.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.antbaric.components.GResponse;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.components.Location;

public class GoogleMapsService {

    private static final String GM_BASE_URI = "http://maps.google.com/";
    Client client;

    public static String getBaseUrl() {
        return GM_BASE_URI;
    }

    public GoogleMapsService() {
        client = ClientBuilder.newClient();
    }

    public Location getGeoLocation(String adresa) {
        try {
            WebTarget webResource = client.target(GoogleMapsService.GM_BASE_URI)
                    .path("maps/api/geocode/json");
            webResource = webResource.queryParam("address",
                    URLEncoder.encode(adresa, "UTF-8"));
            webResource = webResource.queryParam("sensor", "false");

            String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            GResponse responseObject = JsonHelper.decode(response, GResponse.class);

            Location location = new Location(
                    String.valueOf(responseObject.getLatitude()),
                    String.valueOf(responseObject.getLongitude())
            );

            return location;

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GoogleMapsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Get address for coordinate
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public String getAddress(String longitude, String latitude) {
        try {
            WebTarget webResource = client.target(GoogleMapsService.GM_BASE_URI)
                    .path("maps/api/geocode/json");
            webResource = webResource.queryParam("latlng",
                    URLEncoder.encode(latitude + "," + longitude, "UTF-8"));
            webResource = webResource.queryParam("sensor", "false");

            String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            GResponse responseObject = JsonHelper.decode(response, GResponse.class);

            return responseObject.getFormattedAddress();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GoogleMapsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
