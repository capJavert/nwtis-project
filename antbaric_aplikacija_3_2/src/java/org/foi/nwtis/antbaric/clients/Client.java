package org.foi.nwtis.antbaric.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final String SERVER_NAME = "localhost";
    private static final Integer SERVER_PORT = 9966;

    public String run(String command) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            socket = new Socket(SERVER_NAME, SERVER_PORT);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            outputStream.write(command.getBytes());
            outputStream.flush();

            socket.shutdownOutput();

            final StringBuffer buffer = new StringBuffer();

            while (true) {
                int character = inputStream.read();
                if (character == -1) {
                    break;
                } else {
                    buffer.append((char) character);
                }
            }
            
            return buffer.toString();
        } catch (final IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (final IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return "";
    }
}
