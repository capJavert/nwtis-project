package org.foi.nwtis.antbaric.helpers;

import com.google.gson.Gson;

/**
 *
 * @author javert
 */
public class JsonHelper {

    public static <T> T decode(String jsonString, Class<T> type) {
        Gson gson = new Gson();

        return gson.fromJson(jsonString, type);
    }

    public static String encode(Object object) {
        Gson gson = new Gson();
        
        return gson.toJson(object);
    }
}
