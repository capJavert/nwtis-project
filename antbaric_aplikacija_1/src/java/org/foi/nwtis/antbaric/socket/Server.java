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

public class Server extends Thread {

    public static final List<HandlerThread> THREADS = new ArrayList<>();
    public static Status status;
    private ServerSocket socket;

    public static String getStatus() {
        return status.get();
    }

    public static void setStatus(String name) {
        status.set(name);
    }

    @Override
    public void interrupt() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        ServletContext servletContext = (ServletContext) ApplicationListener.getContext();
        Konfiguracija config = (Konfiguracija) servletContext.getAttribute("main-config");
        int port = Integer.parseInt(config.dajPostavku("socket.port"));

        status = new Status();

        try {
            this.socket = new ServerSocket(port);

            while (true) {
                Socket newSocket = this.socket.accept();

                this.addHandler(newSocket);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
