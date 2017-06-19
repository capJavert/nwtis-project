package org.foi.nwtis.antbaric.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.clients.Client;
import org.foi.nwtis.antbaric.components.Izbornik;
import org.foi.nwtis.antbaric.models.User;

/**
 *
 * @author javert
 */
@ManagedBean(name = "socketController")
@RequestScoped
public class SocketController extends Controller<User> implements Serializable {

    private UserAuth userAuth;
    private Client client;
    private String response;
    private String command;
    private String deviceId;
    private String deviceName;
    private String deviceAddress;

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    private final ArrayList<Izbornik> commands = new ArrayList<>();

    public ArrayList<Izbornik> getCommands() {
        return commands;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");

        if (this.userAuth == null) {
            this.toIndex();
        }
        
        this.client = new Client();

        this.commands.clear();
        
        this.commands.add(new Izbornik("IoT Master START", "9"));
        this.commands.add(new Izbornik("IoT Master STOP", "10"));
        this.commands.add(new Izbornik("IoT Master WORK", "11"));
        this.commands.add(new Izbornik("IoT Master WAIT", "12"));
        this.commands.add(new Izbornik("IoT Master STATUS", "13"));
    }

    private String buildCommands(String index) {
        String userPart = "USER " + this.userAuth.getUser().getUsername() + "; PASSWD " + this.userAuth.getUser().getPassword() + "; ";
        //String userPart = "USER " + "javert" + "; PASSWD " + "tvojastara" + "; ";
        String c = "";

        switch (index) {
            case "9":
                c = userPart + "IoT_Master START;";
                break;
            case "10":
                c = userPart + "IoT_Master STOP;";
                break;
            case "11":
                c = userPart + "IoT_Master WORK;";
                break;
            case "12":
                c = userPart + "IoT_Master WAIT;";
                break;
            case "13":
                c = userPart + "IoT_Master STATUS;";
                break;
        }

        return c;
    }

    public void runClient() {
        if (this.deviceId == null) {
            this.deviceId = "";
        }

        if (this.deviceName == null) {
            this.deviceName = "";
        }

        System.out.println(this.buildCommands(this.command));

        this.response = this.client.run(this.buildCommands(this.command));
    }
}
