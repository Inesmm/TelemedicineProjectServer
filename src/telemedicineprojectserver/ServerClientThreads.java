/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.AgeName;
import POJOs.Answer;
import POJOs.Phydata;
import POJOs.SocketUtils;
import POJOs.UserInfo;
import POJOs.UserPassword;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanb
 */
public class ServerClientThreads implements Runnable {

    SocketUtils socketUtils;

    public ServerClientThreads(SocketUtils socketUtils) {
        this.socketUtils = socketUtils;
    }

    @Override
    public void run() {
        ArrayList<UserInfo> userInfoList = null;
        ArrayList<UserPassword> userPasswordList = null;
        UserPassword userPassword = null;
        AgeName ageName = null;
        Phydata phydata = null;
        Object tmp, tmp2, tmp3, tmp4;
        boolean check = true;
        boolean measure = true;
        userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
        System.out.println("Connection client created...");
        while (check) {
            tmp = socketUtils.readObject();
            userPassword = (UserPassword) tmp;
            if (userPassword.getUserName().contains(Utils.NEWUN)) {
                userPassword = Utils.takeOutCode(userPassword);
                if (!Utils.checkUserNameList(userPassword.getUserName(), userPasswordList)) {
                    Answer answerServer = new Answer(Answer.ERR);
                    answerServer.setAnswer(Answer.ERR);
                    socketUtils.writeObject(answerServer);
                } else {
                    check = false;
                    Answer answerServer = new Answer(Answer.VALID_USERNAME);
                    answerServer.setAnswer(Answer.VALID_USERNAME);
                    socketUtils.writeObject(answerServer);
                    tmp2 = socketUtils.readObject();
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
                    System.out.println(userPassword);
                    socketUtils.writeObject(answerServer);
                } else {
                    System.out.println("SignIn succeded...");
                    Answer answerServer = new Answer(Answer.VALID);
                    System.out.println(Answer.VALID);
                    socketUtils.writeObject(answerServer);
                    check = false;
                }
            }
        }
        System.out.println("sale despuesde enviar");
        while (true) {
            tmp3 = socketUtils.readObject();
            phydata = (Phydata) tmp3;

            System.out.println("PHYDATA RECEIVED");
            PersistenceOp.savePhydataUserInfo(Utils.DIRECTORY, Utils.FILENAME, phydata, userPassword, userInfoList);
            System.out.println("PHYDATA SAVED");
            tmp4 = socketUtils.readObject();
            Answer response = (Answer) tmp4;
            if (response.getAnswer().equalsIgnoreCase(Answer.CLOSE)) {
                measure = false;
                releaseResources(socketUtils);
            }

        }
    }

    private static void releaseResources(SocketUtils socketUtils) {
        try {

            socketUtils.getObjectInputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socketUtils.getObjectOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socketUtils.getInputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try {
                socketUtils.getOutputStream().close();
            } catch (IOException ex) {
                Logger.getLogger(ServerClientThreads.class.getName()).log(Level.SEVERE, null, ex);
            }
            socketUtils.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
