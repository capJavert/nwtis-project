package org.foi.nwtis.antbaric.services;

import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.client.ClientBuilder;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.Device;

public class DeviceService extends Service<Device> {

    public DeviceService() {
        this.GM_BASE_URI = "http://localhost:8084/antbaric_aplikacija_1/api/devices/";
        this.client = ClientBuilder.newClient();
    }

    @Override
    public Device decodeObject(String response) {
        return JsonHelper.decode(response, Device.class);
    }

    @Override
    public ArrayList<Device> decodeObjects(String response) {
        return new ArrayList<>(Arrays.asList(JsonHelper.decode(response, Device[].class)));
    }
    
}
