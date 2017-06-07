package org.foi.nwtis.antbaric.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.components.SyntaxValidator;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.models.User;
import org.foi.nwtis.antbaric.socket.Server;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;
import org.foi.nwtis.antbaric.ws.IoTMasterWSClient;
import org.foi.nwtis.dkermek.ws.serveri.StatusKorisnika;
import org.foi.nwtis.dkermek.ws.serveri.StatusUredjaja;
import org.foi.nwtis.dkermek.ws.serveri.Uredjaj;

/**
 *
 * @author javert
 */
public class HandlerThread extends Thread {

    private final Socket socket;
    private final ServletContext context;
    private final Konfiguracija config;

    public HandlerThread(Socket socket) {
        this.socket = socket;
        this.context = (ServletContext) ApplicationListener.getContext();
        this.config = (Konfiguracija) this.context.getAttribute("main-config");
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public void run() {
        StringBuilder command = new StringBuilder();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            while (true) {
                int znak = inputStream.read();
                if (znak == -1) {
                    break;
                } else {
                    command.append((char) znak);
                }
            }

            Matcher matcher = SyntaxValidator.validate(command.toString());
            if (matcher != null && new User().authenticate(matcher.group(1), matcher.group(2)) != null) {
                System.out.println("authenticated");

                IoTMasterWSClient wsClient = new IoTMasterWSClient(
                        this.config.dajPostavku("ws.username"),
                        this.config.dajPostavku("ws.password")
                );

                switch (matcher.group(3)) {
                    case "IoT_Master":
                        switch (matcher.group(4)) {
                            case "START":
                                if (wsClient.registrirajGrupuIoT()) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 20;".getBytes());
                                }
                                break;
                            case "STOP":
                                if (wsClient.deregistrirajGrupuIoT()) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 21;".getBytes());
                                }
                                break;
                            case "WORK":
                                if (wsClient.aktivirajGrupuIoT()) {
                                    outputStream.write("OK 10".getBytes());
                                } else {
                                    outputStream.write("ERR 22;".getBytes());
                                }
                                break;
                            case "WAIT":
                                if (wsClient.blokirajGrupuIoT()) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 23;".getBytes());
                                }
                                break;
                            case "LOAD":
                                wsClient.ucitajSveUredjajeGrupe();
                                outputStream.write("OK 10;".getBytes());
                                break;
                            case "CLEAR":
                                wsClient.obrisiSveUredjajeGrupe();
                                outputStream.write("OK 10;".getBytes());
                                break;
                            case "STATUS":
                                StatusKorisnika groupStatus = wsClient.dajStatusGrupeIoT();

                                switch (groupStatus) {
                                    case AKTIVAN:
                                        outputStream.write("OK 25;".getBytes());
                                        break;
                                    case BLOKIRAN:
                                        outputStream.write("OK 24;".getBytes());
                                        break;
                                    default:
                                        outputStream.write("OK 00;".getBytes());
                                        break;
                                }
                                break;
                            case "LIST":
                                List<Uredjaj> devices = wsClient.dajSveUredjajeGrupe();

                                String output = "OK 10; {";

                                for (Uredjaj device : devices) {
                                    output = output.concat("{IoT " + String.valueOf(device.getId()) + " " + device.getNaziv());
                                }

                                outputStream.write(output.concat("};").getBytes());
                        }
                        break;
                    case "IoT":
                        switch (matcher.group(5)) {
                            case "WORK":
                                if (wsClient.aktivirajUredjajGrupe(Integer.parseInt(matcher.group(4)))) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 31;".getBytes());
                                }
                                break;
                            case "WAIT":
                                if (wsClient.blokirajUredjajGrupe(Integer.parseInt(matcher.group(4)))) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 32;".getBytes());
                                }
                                break;
                            case "REMOVE":
                                if (wsClient.obrisiUredjajGrupe(Integer.parseInt(matcher.group(4)))) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("ERR 33;".getBytes());
                                }
                                break;
                            case "STATUS":
                                StatusUredjaja deviceStatus = wsClient.dajStatusUredjajaGrupe(Integer.parseInt(matcher.group(4)));

                                switch (deviceStatus) {
                                    case AKTIVAN:
                                        outputStream.write("OK 35;".getBytes());
                                        break;
                                    case BLOKIRAN:
                                        outputStream.write("OK 34;".getBytes());
                                        break;
                                    default:
                                        outputStream.write("OK 00;".getBytes());
                                }
                                break;
                            default: 
                                if(wsClient.dodajNoviUredjajGrupi(
                                        Integer.parseInt(matcher.group(4)), 
                                        matcher.group(6),
                                        matcher.group(7)
                                )) {
                                    outputStream.write("OK 10;".getBytes());
                                } else {
                                    outputStream.write("OK 30;".getBytes());
                                }
                        }
                        break;
                    default:
                        switch (matcher.group(4)) {
                            case "PAUSE":
                                if (Server.getStatus().equals("PAUSE")) {
                                    outputStream.write("ERR 10;".getBytes());
                                } else {
                                    Server.setStatus("PAUSE");
                                    outputStream.write("OK 10;".getBytes());
                                }
                                break;
                            case "START":
                                if (Server.getStatus().equals("START")) {
                                    outputStream.write("ERR 11;".getBytes());
                                } else {
                                    Server.setStatus("START");
                                    outputStream.write("OK 10;".getBytes());
                                }
                                break;
                            case "STOP":
                                if (Server.getStatus().equals("STOP")) {
                                    outputStream.write("ERR 12;".getBytes());
                                } else {
                                    Server.setStatus("STOP");
                                    outputStream.write("OK 10;".getBytes());
                                }
                                break;
                            default:
                                switch (Server.getStatus()) {
                                    case "PAUSE":
                                        outputStream.write("OK 13;".getBytes());
                                        break;
                                    case "START":
                                        outputStream.write("OK 14;".getBytes());
                                        break;
                                    case "STOP":
                                        outputStream.write("OK 15;".getBytes());
                                }
                        }
                }
            } else {
                outputStream.write("ERR 10".getBytes());
            }
            outputStream.flush();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(HandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(HandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Uredjaj findDeviceById(Integer id, List<Uredjaj> devices) {
        for (Uredjaj device : devices) {
            if (device.getId() == id) {
                return device;
            }
        }

        return null;
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
