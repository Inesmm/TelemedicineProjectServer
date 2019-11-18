/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.Phydata;
import POJOs.UserInfo;
import POJOs.UserPassword;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        ArrayList<UserPassword> userPasswordList = null;
        //UserInfo userInfo = null;
        UserPassword userPassword = null;
        Phydata phydata = null;
        PrintWriter printWriter = null;
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            try {
                userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
                System.out.println("Connection client created");
                Object tmp;
                /*while ((tmp = objectInputStream.readObject()) != null) {
                    userPassword = (UserPassword) tmp;
                    System.out.println("Server Recieved:" + userPassword.toString());
                }*/

                tmp = objectInputStream.readObject();
                userPassword = (UserPassword) tmp;
                if (userPassword.getUserName().contains(Utils.NEWUN)) {
                    userPassword = Utils.takeOutCode(userPassword);
                    if (!Utils.checkUserNameList(userPassword.getUserName(), userPasswordList)) {
                        System.out.println(Utils.ERR);
                        printWriter.println(Utils.ERR);
                    } else {
                        System.out.println(Utils.VALID_USERNAME);
                        printWriter.println(Utils.VALID_USERNAME);
                        //PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, userPassword, userPasswordList);
                        //TO DO recived age and name;
                        //System.out.println("ExitoSignUp");
                        String encryptedPassword=encodePassword(userPassword.getPassword()); //encryption of user and password
                        String encryptedUser = encodePassword(userPassword.getUserName());
                        UserPassword encrypted = null; 
                        encrypted.setPassword(encryptedPassword);
                        encrypted.setUserName(encryptedUser);
                        PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, encrypted, userPasswordList); //we save passwprd and username encrypted
                    }
                } else {
                    if (!Utils.checkCorrectPassword(userPassword.getUserName(),
                            userPassword.getPassword(), userPasswordList)) {
                        printWriter.println(Utils.ERR);
                    } else {
                        //TODO
                        //userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
                        //UserInfo userInfo = Utils.getUserInfo(userPassword.getUserName(), userInfoList);
                        System.out.println("ExitoSignIn");
                        printWriter.println(Utils.VALID);
                    }
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
    
    public static String encodePassword(String passwordToEncode) { //Encryption of password and user
        //This method is use to encode the pasword of the Client or Doctor.
        String passwordToHash = passwordToEncode;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
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
