/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.AgeName;
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
        AgeName ageName = null;
        Phydata phydata = null;
        //PrintWriter printWriter = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        //Answer answerServer = new Answer("hola");
        Object tmp, tmp2, tmp3, tmp4;
        boolean check = true;
        boolean measure = true;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            try {
                userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
                userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
                System.out.println("Connection client created...");
                while (check) {
                    tmp = objectInputStream.readObject();
                    userPassword = (UserPassword) tmp;
                    if (userPassword.getUserName().contains(Utils.NEWUN)) {
                        userPassword = Utils.takeOutCode(userPassword);
                        if (!Utils.checkUserNameList(userPassword.getUserName(), userPasswordList)) {
                            Answer answerServer = new Answer(Answer.ERR);
                            answerServer.setAnswer(Answer.ERR);
                            objectOutputStream.writeObject(answerServer);
                        } else {
                            check = false;
                            Answer answerServer = new Answer(Answer.VALID_USERNAME);
                            answerServer.setAnswer(Answer.VALID_USERNAME);
                            objectOutputStream.writeObject(answerServer);
                            tmp2 = objectInputStream.readObject();
                            ageName = (AgeName) tmp2;
                            UserInfo userInfo = new UserInfo(userPassword, ageName.getName(), ageName.getAge());
                            PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, userPassword, userPasswordList);
                            PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, userInfoList);
                            System.out.println("User saved...");
                        }
                    } else {
                        if (!Utils.checkCorrectPassword(userPassword.getUserName(),
                                userPassword.getPassword(), userPasswordList)) {
                            Answer answerServer = new Answer("ERROR");

                            answerServer.setAnswer(Answer.ERR);
                            System.out.println(Answer.ERR);
                            System.out.println("le envia al client:" + answerServer.getAnswer());

                            objectOutputStream.writeObject(answerServer);
                        } else {
                            System.out.println("SignIn succeded...");
                            Answer answerServer = new Answer(Answer.VALID);
                            System.out.println(Answer.VALID);
                            objectOutputStream.writeObject(answerServer);
                            check = false;
                        }
                    }
                }
                System.out.println("sale despuesde enviar");
                while (true) {
                    tmp3 = objectInputStream.readObject();
                    phydata = (Phydata) tmp3;
                    System.out.println("PHYDATA RECEIVED");
                    //Utils.GraphPhydata(phydata.getAccRec());
                    PersistenceOp.savePhydataUserInfo(Utils.DIRECTORY, Utils.FILENAME, phydata, userPassword, userInfoList);
                    System.out.println("PHYDATA SAVED");
                    tmp4 = objectInputStream.readObject();
                    Answer response = (Answer) tmp4;
                    if (response.getAnswer().equalsIgnoreCase(Answer.CLOSE)) {
                        measure = false;
                        //TODO close
                    }

                }

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

    /*public static String encodePassword(String passwordToEncode) { //Encryption of password and user
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
    }*/
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
