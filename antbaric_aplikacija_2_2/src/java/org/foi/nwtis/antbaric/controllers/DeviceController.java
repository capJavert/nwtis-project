package org.foi.nwtis.antbaric.controllers;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.Device;
import org.foi.nwtis.antbaric.services.DeviceService;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
@ManagedBean(name = "deviceController")
@RequestScoped
public class DeviceController extends Controller<Device> implements Serializable {

    private Boolean editing = false;
    private Boolean isNew;
    private UserAuth userAuth;
    private Integer deviceId;
    private String deviceName;
    private String longitude;
    private String latitude;
    private Integer identifier;

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");

        /*if (this.userAuth == null) {
            this.toLogin();
        }*/
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");

        this.pagination = Integer.parseInt(config.dajPostavku("ui.perPage"));

        this.service = new DeviceService();
        this.list = this.service.get();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    private Device getDevice(Integer id) {
        this.isNew = true;

        for (Device device : this.list) {
            if (Objects.equals(device.getPrimaryKey(), id)) {
                this.isNew = false;

                return device;
            }
        }

        return new Device();
    }

    public void create() {
        this.model = new Device();
        this.model.setPrimaryKey(this.deviceId);
        this.model.setNaziv(this.deviceName);
        this.model.setLatitude(Float.parseFloat(this.latitude));
        this.model.setLongitude(Float.parseFloat(this.longitude));

        if (this.service.create(this.model)) {
            this.editing = false;
            this.list = this.service.get();
        }
    }

    public void update() {
        this.model = this.getDevice(this.identifier);
        this.model.setPrimaryKey(this.deviceId);
        this.model.setNaziv(this.deviceName);
        this.model.setLatitude(Float.parseFloat(this.latitude));
        this.model.setLongitude(Float.parseFloat(this.longitude));

        if (this.service.update(this.model.getPrimaryKey().toString(), this.model)) {
            this.editing = false;
            this.list = this.service.get();
        }
    }

    public Boolean getEditing() {
        return editing;
    }

    public void setEditing(Integer deviceId) {
        this.editing = !editing;

        if (this.editing) {
            this.model = this.getDevice(deviceId);
            this.deviceId = this.model.getPrimaryKey();
            this.deviceName = this.model.getNaziv();
            this.longitude = this.model.getLongitude().toString();
            this.latitude = this.model.getLatitude().toString();
            this.identifier = this.model.getPrimaryKey();
        } else {
            this.model = null;
        }
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
