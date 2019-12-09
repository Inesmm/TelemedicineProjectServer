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
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author juanb
 */
public class prof3 {

    public static void main(String[] args) {
        ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        UserPassword userPassword = new UserPassword("juan", "1234");
        int A[][] = new int[2][2];
        int B[][] = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                A[i][j] = (int) (Math.random() * 10);
                B[i][j] = (int) (Math.random() * 10);
                //System.out.println("A:" + A[i][j] + ",B: " + B[i][j]);
            }
        }
        int edad = 12;
        Date date = new Date();
        String symtpons = "Estoy malito";
        Phydata phydata = new Phydata(date, A, B, symtpons);
        System.out.println(phydata.printAllData());
        //PersistenceOp.savePhydataUserInfo(Utils.DIRECTORY, Utils.FILENAME, phydata, userPassword, userInfoList);
        UserInfo userInfo = Utils.getUserInfo(userPassword.getUserName(), userInfoList);
        System.out.println("EL USER:");
        System.out.println(userInfo.printAll());
        userInfo.getPhydataArray().add(phydata);
        System.out.println("Despues de meterlo");
        System.out.println(userInfo.printAll());
        PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, userInfoList);

    }

}
