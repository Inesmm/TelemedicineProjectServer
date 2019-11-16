/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.Phydata;
import POJOs.UserInfo;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
        UserInfo userInfo = null;
        Phydata phydata = null;
        PrintWriter printWriter = null;
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            try {
                System.out.println("Connection client created");
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Object tmp = objectInputStream.readObject();
                userInfo = (UserInfo) tmp;
                if (!userInfo.isChecked()) {
                    ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(PersistenceOp.DIRECTORY,
                            PersistenceOp.FILENAME);
                    while (!Utils.checkCorrectPassword(userInfo.getName(),
                            userInfo.getPassword(), userInfoList)) {
                        System.out.println("Error1: Incorrect UserInfo");
                        printWriter.println(PersistenceOp.ERR1);
                        tmp = objectInputStream.readObject();
                        userInfo = (UserInfo) tmp;
                    }
                    userInfo.setChecked(true);
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            printWriter.close();
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
