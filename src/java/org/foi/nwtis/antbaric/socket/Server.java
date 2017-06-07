package org.foi.nwtis.antbaric.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.components.Status;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.threads.HandlerThread;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

public class Server {

    public static final List<HandlerThread> THREADS = new ArrayList<>();
    ;
    public static Status status;

    public static String getStatus() {
        return status.get();
    }

    public static void setStatus(String name) {
        status.set(name);
    }

    /*public static void main(String[] args) {
        Server server = new Server();
        server.start(false);
    }*/
    
    /**
     * Start socket server
     *
     * @param load
     */
    public void start(boolean load) {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");
        int port = Integer.parseInt(config.dajPostavku("socket.port"));

        if (load) {
            // TODO: load data from serialized state
        }

        status = new Status();

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("krenulo");
                Socket socket = serverSocket.accept();

                this.addHandler(socket);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                System.out.println("NO OPEN");
            }
        }
    }

    /**
     * Assign socket client to handler
     *
     * @param socket
     */
    private void addHandler(Socket socket) {
        System.out.println("konekcija");
        HandlerThread handler = new HandlerThread(socket);

        synchronized (THREADS) {
            THREADS.add(handler);
        }

        handler.start();
    }
}
