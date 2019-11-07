/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inesu
 */
public class ServerUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(9000);
            try {
                while (true) {
                    //Thie executes when we have a client
                    Socket socket = serverSocket.accept();
                    new Thread(new ServerUserThreads(socket)).start();
                }
            } finally {
                releaseResourcesServer(serverSocket);
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
