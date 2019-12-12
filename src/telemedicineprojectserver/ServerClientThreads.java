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
import java.util.ArrayList;

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
        UserInfo userInfo = null;
        AgeName ageName = null;
        Phydata phydata = null;
        Object tmp, tmp2, tmp3, tmp4;
        boolean check = true;
        boolean measure = true;
        userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
        while (check) {
            tmp = socketUtils.readObject();
            userPassword = (UserPassword) tmp;
            if (userPassword.getUserName().contains(Utils.NEWUN)) {
                userPassword = Utils.takeOutCode(userPassword);
                if (!Utils.checkUserNameList(userPassword.getUserName(), userPasswordList)) {
                    Answer answerServer = new Answer(Answer.ERR);
                    socketUtils.writeObject(answerServer);
                } else {
                    check = false;
                    Answer answerServer = new Answer(Answer.VALID_USERNAME);
                    socketUtils.writeObject(answerServer);
                    tmp2 = socketUtils.readObject();
                    ageName = (AgeName) tmp2;
                    userInfo = new UserInfo(userPassword.getUserName(), ageName.getName(), ageName.getAge());
                    PersistenceOp.saveUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP, userPassword, userPasswordList);
                    PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, userInfoList);
                }
            } else {
                if (!Utils.checkCorrectPassword(userPassword.getUserName(),
                        userPassword.getPassword(), userPasswordList)) {
                    Answer answerServer = new Answer(Answer.ERR);
                    socketUtils.writeObject(answerServer);
                } else {
                    Answer answerServer = new Answer(Answer.VALID);
                    socketUtils.writeObject(answerServer);
                    check = false;
                }
            }
        }
        while (measure) {
            userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
            if (userInfo == null) {
                userInfo = Utils.getUserInfo(userPassword.getUserName(), userInfoList);
            }
            tmp3 = socketUtils.readObject();
            phydata = (Phydata) tmp3;
            userInfo.getPhydataArray().add(phydata);
            PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, userInfoList);
            tmp4 = socketUtils.readObject();
            Answer response = (Answer) tmp4;
            if (response.getAnswer().equalsIgnoreCase(Answer.CLOSE)) {
                measure = false;
            }
        }
        socketUtils.releaseResources();
    }

}
