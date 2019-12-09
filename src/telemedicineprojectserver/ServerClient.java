/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.UserInfo;
import POJOs.UserPassword;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author juanb
 */
public class ServerClient {

    public static void main(String[] args) {
        //printAllUserList();
        //printAllUserInfo();

        /*UserPassword userPassword = new UserPassword("juan", "1234");
        UserPassword userPassword2 = new UserPassword("raul", "1234");
        UserInfo userInfo = new UserInfo(userPassword, "juan", 12);
        UserInfo userInfo2 = new UserInfo(userPassword2, "raul", 12);
        ArrayList<UserInfo> usList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, usList);*/
        //Phydata phydata = new Phydata("hola");
        /*Phydata phydata;
        Object tmp, tmp2;
        ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        Iterator it = userInfoList.iterator();
        while (it.hasNext()) {
            tmp = it.next();
            userInfo = (UserInfo) tmp;
            System.out.println("UserInfoPassword: " + userInfo.getUserPassword().toString());
            ArrayList<Phydata> phydataList = userInfo.getPhydataArray();
            Iterator it2 = phydataList.iterator();
            while (it2.hasNext()) {
                tmp2 = it2.hasNext();
                phydata = (Phydata) tmp2;
                System.out.println("Symptons: " + phydata.getSymptons());
            }
            System.out.println("No hay un cagao");
        }*/
        ServerClientGUI serverGUI = new ServerClientGUI();
        serverGUI.setVisible(true);
        serverGUI.startServer();
    }

    public static void printAllUserList() {
        System.out.println("\t\tUSER PASSWORD LIST");
        Object tmp;
        UserPassword userPassword;
        ArrayList<UserPassword> userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
        Iterator it = userPasswordList.iterator();
        while (it.hasNext()) {
            tmp = it.next();
            userPassword = (UserPassword) tmp;
            System.out.println(userPassword.toString());
        }
    }

    public static void printAllUserInfo() {
        System.out.println("\t\tUSER INFO LIST");
        Object tmp;
        UserInfo userInfo;
        ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        Iterator it = userInfoList.iterator();
        while (it.hasNext()) {
            tmp = it.next();
            userInfo = (UserInfo) tmp;
            System.out.println(userInfo.toString());
        }

    }
}
