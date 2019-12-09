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
public class prof {

    public static void main(String[] args) {
        System.out.println("\t\tDATA:");
        UserPassword userPassword = new UserPassword("juan", "1234");
        ArrayList<Phydata> phydataList = new ArrayList<Phydata>();
        ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
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
        String nombre = "juan";
        Date date = new Date();
        String symtpons = "none";
        Phydata phydata = new Phydata(date, A, B, symtpons);
        Phydata phydata2 = new Phydata();
        phydataList.add(phydata);
        phydataList.add(phydata2);
        UserInfo userInfo = new UserInfo(userPassword, nombre, edad);
        System.out.println(userInfo.printAll());
        System.out.println("CHECKING SAVING");
        userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        PersistenceOp.saveUserInfo(Utils.DIRECTORY, Utils.FILENAME, userInfo, userInfoList);
    }

}

//Date date = new Date(); // this object contains the current date value
//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    //System.out.println(formatter.format(date));
