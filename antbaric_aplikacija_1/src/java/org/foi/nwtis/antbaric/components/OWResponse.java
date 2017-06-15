package org.foi.nwtis.antbaric.components;

import java.util.List;

/**
 *
 * @author javert
 */
public class OWResponse {

    private Coord coord;
    private List<Weather> weather = null;
    private String base;
    private Main main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private Sys sys;
    private Integer id;
    private String name;
    private Integer cod;

    public Double getTemp() {
        return this.main.getTemp();
    }

    public Double getTempMax() {
        return this.main.getTempMax();
    }

    public Double getTempMin() {
        return this.main.getTempMin();
    }

    public Double getPreassure() {
        return this.main.getPressure();
    }

    public Double getHumidity() {
        return this.main.getHumidity();
    }

    public Double getWind() {
        return this.wind.getSpeed();
    }

    public Double getWindDirection() {
        return this.wind.getDeg();
    }

    public String getWeather() {
        return this.weather.get(0).getMain();
    }

    public String getWeatherDescription() {
        return this.weather.get(0).getDescription();
    }
}

class Clouds {

    private Integer all;

    Integer getAll() {
        return all;
    }

    void setAll(Integer all) {
        this.all = all;
    }

}

class Coord {

    private Double lon;
    private Double lat;

    Double getLon() {
        return lon;
    }

    void setLon(Double lon) {
        this.lon = lon;
    }

    Double getLat() {
        return lat;
    }

    void setLat(Double lat) {
        this.lat = lat;
    }

}

class Main {

    Double temp;
    private Double pressure;
    private Double humidity;
    private Double temp_min;
    private Double temp_max;

    Double getTemp() {
        return temp;
    }

    void setTemp(Double temp) {
        this.temp = temp;
    }

    Double getPressure() {
        return pressure;
    }

    void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    Double getHumidity() {
        return humidity;
    }

    void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    Double getTempMin() {
        return temp_min;
    }

    void setTempMin(Double tempMin) {
        this.temp_min = tempMin;
    }

    Double getTempMax() {
        return temp_max;
    }

    void setTempMax(Double tempMax) {
        this.temp_max = tempMax;
    }

}

class Sys {

    private Integer type;
    private Integer id;
    private Double message;
    private String country;
    private Integer sunrise;
    private Integer sunset;

    Integer getType() {
        return type;
    }

    void setType(Integer type) {
        this.type = type;
    }

    Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    Double getMessage() {
        return message;
    }

    void setMessage(Double message) {
        this.message = message;
    }

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    Integer getSunrise() {
        return sunrise;
    }

    void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    Integer getSunset() {
        return sunset;
    }

    void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

}

class Weather {

    private Integer id;
    private String main;
    private String description;
    private String icon;

    Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    String getMain() {
        return main;
    }

    void setMain(String main) {
        this.main = main;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getIcon() {
        return icon;
    }

    void setIcon(String icon) {
        this.icon = icon;
    }

}

class Wind {

    private Double speed;
    private Double deg;

    Double getSpeed() {
        return speed;
    }

    void setSpeed(Double speed) {
        this.speed = speed;
    }

    Double getDeg() {
        return deg;
    }

    void setDeg(Double deg) {
        this.deg = deg;
    }

}
