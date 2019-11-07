/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanb
 */
public class ServerUserThreads implements Runnable {

    Socket socket;

    public ServerUserThreads(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            System.out.println("Connection client created");
            bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            System.out.println("Text Received:\n");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.toLowerCase().contains("stop")) {
                    System.out.println("Stopping the thread");
                    releaseResources(bufferedReader, socket);
                    //System.exit(0);
                }
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static void releaseResources(BufferedReader bufferedReader,
            Socket socket) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
