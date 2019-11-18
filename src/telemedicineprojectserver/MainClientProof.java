/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.UserInfo;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanb
 */
public class MainClientProof {

    public static void main(String[] args) {
        /*try {
            System.out.println("Starting Client...");
            Socket socket = new Socket("localhost", 9000);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connection established... sending text");
            printWriter.println("Name");
            printWriter.println("password");
            System.out.println("Sending stop command");
            printWriter.println("Stop");
            releaseResources(printWriter, socket);
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(MainClientProof.class.getName()).log(Level.SEVERE, null, ex);
        }*/

 /*UserPassword userPassword = new UserPassword("jbalsa", "1234");
        ArrayList<UserPassword> userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
        System.out.println("TRasPrimerload");
        System.out.println("Fuera: " + Utils.checkUserNameList(userPassword.getUserName(), userPasswordList));
        PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, userPassword, userPasswordList);
        System.out.println("cargado");*/
        ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        System.out.println("traza");
    }

    private static void releaseResources(PrintWriter printWriter, Socket socket) {

        printWriter.close();

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MainClientProof.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
