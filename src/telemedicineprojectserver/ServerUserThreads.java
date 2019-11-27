/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.Answer;
import POJOs.Phydata;
import POJOs.UserInfo;
import POJOs.UserPassword;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.*;
import javax.crypto.*;

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
        //PrintWriter printWriter = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        Answer answerServer = new Answer("hola");
        boolean check = true;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            try {
                userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
                System.out.println("Connection client created");
                
                /*while ((tmp = objectInputStream.readObject()) != null) {
                    userPassword = (UserPassword) tmp;
                    System.out.println("Server Recieved:" + userPassword.toString());
                }*/
                while(check){
                Object tmp;
                System.out.println("traza");
                tmp = objectInputStream.readObject();
                userPassword = (UserPassword) tmp;        
                if (userPassword.getUserName().contains(Utils.NEWUN)) {
                    userPassword = Utils.takeOutCode(userPassword);
                    if (!Utils.checkUserNameList(userPassword.getUserName(), userPasswordList)) {
                        answerServer.setAnswer(Answer.ERR);
                        System.out.println("buble:"+Answer.ERR);
                        objectOutputStream.writeObject(answerServer);
                    } else {
                        check = false;
                        answerServer.setAnswer(Answer.VALID_USERNAME);
                        System.out.println("le envia:" + answerServer.getAnswer());
                        objectOutputStream.writeObject(answerServer);
                        //PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, userPassword, userPasswordList);
                        //TO DO recived age and name;
                        //System.out.println("ExitoSignUp");
                        //String encryptedPassword=encodePassword(userPassword.getPassword()); //encryption of user and password
                        //String encryptedUser = encodePassword(userPassword.getUserName());
                        //UserPassword encrypted = null;
                        //encrypted.setPassword(encryptedPassword);
                        //encrypted.setUserName(encryptedUser);
                        //PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, encrypted, userPasswordList); //we save passwprd and username encrypted
                       /* String algorithm = "AES/CBC/PKCS5Padding";
                        Cipher cipher = Cipher.getInstance(algorithm);
                        KeyGenerator key = KeyGenerator.getInstance(algorithm);
                        SecureRandom secureRandom = new SecureRandom();
                        int keyBitSize = 256;
                        key.init(keyBitSize, secureRandom);
                        SecretKey secretKey = key.generateKey();
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        byte[] password = userPassword.getPassword().getBytes("UFT-8");
                        byte[] username = userPassword.getUserName().getBytes("UFT-8");
                        byte[] encryptedPassword = cipher.doFinal(password);
                        byte[] encryptedUsername = cipher.doFinal(username);
                        UserPassword encrypted = null;
                        encrypted.setUserName(encryptedUsername.toString());
                        encrypted.setPassword(encryptedPassword.toString());
                        PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, encrypted, userPasswordList);*/
                         PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP,userPassword, userPasswordList);
                         System.out.println("USER SAVED");
                                
                    }
                } 
                
                else {
                    if (!Utils.checkCorrectPassword(userPassword.getUserName(),
                            userPassword.getPassword(), userPasswordList)) {
                        answerServer.setAnswer(Answer.ERR);
                        System.out.println(Answer.ERR);
                        objectOutputStream.writeObject(answerServer);
                    } else {
                        //TODO
                        //userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
                        //UserInfo userInfo = Utils.getUserInfo(userPassword.getUserName(), userInfoList);
                        System.out.println("ExitoSignIn");
                        answerServer.setAnswer(Answer.VALID);
                        check = false;
                        System.out.println(Answer.VALID);
                        objectOutputStream.writeObject(answerServer);
                    }
                }
                }
                System.out.println("Ha terminado"+check);
            } catch (IOException ex) {
                Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (NoSuchAlgorithmException ex) {
            //    Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (NoSuchPaddingException ex) {
            //    Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (InvalidKeyException ex) {
            //    Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            //} catch (IllegalBlockSizeException ex) {
           //     Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
           // } catch (BadPaddingException ex) {
           //     Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUserThreads.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // printWriter.close();
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
