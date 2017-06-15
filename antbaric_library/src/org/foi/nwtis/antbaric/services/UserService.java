package org.foi.nwtis.antbaric.services;

import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.client.ClientBuilder;
import org.foi.nwtis.antbaric.helpers.JsonHelper;
import org.foi.nwtis.antbaric.models.User;

public class UserService extends Service<User> {

    public UserService() {
        this.GM_BASE_URI = "http://localhost:8084/antbaric_aplikacija_1/api/users/";
        this.client = ClientBuilder.newClient();
    }

    @Override
    public User decodeObject(String response) {
        return JsonHelper.decode(response, User.class);
    }

    @Override
    public ArrayList<User> decodeObjects(String response) {
        return new ArrayList<>(Arrays.asList(JsonHelper.decode(response, User[].class)));
    }
    
}
