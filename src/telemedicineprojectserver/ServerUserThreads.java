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
import java.io.InputStreamReader;
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
        ArrayList<UserInfo> userInfoList = null;
        UserInfo userInfo = null;
        String userName = null;
        String password;
        int age;
        String Name = null;
        Phydata phydata = null;
        PrintWriter printWriter = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        ObjectInputStream objectInputStream = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            try {
                userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
                System.out.println("Connection client created");
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (bufferedReader.readLine().equalsIgnoreCase(Utils.NEWUN)) {
                    userName = bufferedReader.readLine();
                    password = bufferedReader.readLine();
                    if (!Utils.checkUserName(userName, userInfoList)) {
                        printWriter.println(Utils.ERR);
                    } else {
                        printWriter.println(Utils.VALID_USERNAME);
                        Object tmp = (Object) objectInputStream.readObject();
                        userInfo = (UserInfo) tmp;
                    }
                } else {
                    userName = bufferedReader.readLine();
                    password = bufferedReader.readLine();

                }
                /*objectInputStream = new ObjectInputStream(inputStream);
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
                System.out.println("Correct");*/

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
