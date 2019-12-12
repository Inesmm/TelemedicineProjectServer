/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import POJOs.Phydata;
import POJOs.UserInfo;
import POJOs.UserPassword;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanb
 */
public final class PersistenceOp {

    private static final String algorithm = "DES";
    private static final byte[] array = new byte[8];
    private static final String key = new String(array, Charset.forName("UTF-8"));

    public static int saveUserInfo(String directory, String fileName, UserInfo user, ArrayList<UserInfo> usersInfoList) {
        File direct = new File(directory);
        File file = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            file = new File(directory, fileName);
            if (!Utils.checkUserName(user.getUserPassword().getUserName(), usersInfoList)) {
                usersInfoList.remove(Utils.getArrayIndexUserName(user.getUserPassword().getUserName(), usersInfoList));
                //return 1;
            }

            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //usersInfoList.remove(Utils.getArrayIndexUserName(user.getUserPassword().getUserName(), usersInfoList));
                String hashPass = getMD5(user.getUserPassword().getPassword());
                UserPassword userPassword = new UserPassword(user.getUserPassword().getUserName(), hashPass);
                System.out.println("Hash" + hashPass);
                user = new UserInfo(userPassword, hashPass, user.getAge()); //userpassword, name age
            usersInfoList.add(user);
            Iterator<UserInfo> it = usersInfoList.iterator();
            objectOutputStream.writeObject(usersInfoList.size());
            while (it.hasNext()) {
                UserInfo us = (UserInfo) it.next();
                objectOutputStream.writeObject(us);
            }
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Saving UserInfo...");
        return 1;
    }

    public static void savePhydataUserInfo(String directory, String filename, Phydata phydata,
            UserPassword userPassword, ArrayList<UserInfo> usersInfoList) {
        UserInfo userInfo = null;
        userInfo = Utils.getUserInfo(userPassword.getUserName(), usersInfoList);
        ArrayList<Phydata> phydataList = userInfo.getPhydataArray();
        /*System.out.println("Antes de guardar");
        Iterator it3 = phydataList.iterator();
        Object tmp3;
        Phydata ph2;
        while (it3.hasNext()) {
            tmp3 = it3.next();
            ph2 = (Phydata) tmp3;
            System.out.println("holapesicola" + phydata.getSymptons());

        }
        ArrayList<Phydata> phydataList2 = userInfo.getPhydataArray();
        phydataList.add(phydata);
        userInfo.setPhydataArray(phydataList);
        Object tmp;
        System.out.println("Soy muy fan");
        phydataList2 = userInfo.getPhydataArray();
        Iterator it = phydataList2.iterator();
        Phydata ph;
        while (it.hasNext()) {
            tmp = it.next();
            ph = (Phydata) tmp;
            System.out.println("Dentro bucle" + phydata.getSymptons());
        }*/
        PersistenceOp.saveUserInfo(directory, filename, userInfo, usersInfoList);

    }

    public static int saveUserPaswordList(String directory, String fileName,
            UserPassword userPassword, ArrayList<UserPassword> userpasswordList) {
        try {
            File direct = new File(directory);
            File file = null;
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {

                file = new File(directory, fileName);
                if (!Utils.checkUserNameList(userPassword.getUserName(), userpasswordList)) {
                    userpasswordList.remove(Utils.getArrayIndexUserPassword(userPassword.getUserName(), userpasswordList));
                    userpasswordList.add(userPassword);
                    return 1;
                }
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                String hashPass = getMD5(userPassword.getPassword());
                System.out.println("Hash" + hashPass);
                userPassword = new UserPassword(userPassword.getUserName(), hashPass);
                userpasswordList.add(userPassword);
                Iterator<UserPassword> it = userpasswordList.iterator();
                objectOutputStream.writeObject(userpasswordList.size());
                while (it.hasNext()) {
                    UserPassword us = (UserPassword) it.next();
                    objectOutputStream.writeObject(us);
                }
                objectOutputStream.close();
                fileOutputStream.close();
                System.out.println("Saving UserInfo...");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    public static ArrayList<UserInfo> loadUserInfo(String directory, String fileName) {
        ArrayList<UserInfo> usersInfoList = new ArrayList();
        File file = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        File direct = new File(directory);
        int last = 0;
        if (!direct.exists()) {
            direct.mkdir();
        }
        try {
            file = new File(directory, fileName);
            if (!file.exists()) {
                file.createNewFile();
                return usersInfoList;
            } else {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                last = (int) objectInputStream.readObject();
                for (int i = 0; i < last; i++) {
                    usersInfoList.add((UserInfo) objectInputStream.readObject());
                }
            }
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loading UserInfo...");
        return usersInfoList;
    }

    public static ArrayList<UserPassword> loadUserPaswordList(String directory, String fileName) {
        UserPassword userPassword;
        ArrayList<UserPassword> userPasswordList = new ArrayList();
        File file = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        File direct = new File(directory);
        int last = 0;
        if (!direct.exists()) {
            direct.mkdir();
        }
        try {
            file = new File(directory, fileName);
            if (!file.exists()) {
                file.createNewFile();
                return userPasswordList;
            } else {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                last = (int) objectInputStream.readObject();
                for (int i = 0; i < last; i++) {
                    userPassword = (UserPassword) objectInputStream.readObject();
                    userPasswordList.add(userPassword);
                }
            }
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceOp.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loading Userpassword...");
        return userPasswordList;

    }

    public static String getMD5(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++) {
                sb.append(Integer.toHexString(0xff & messageDigest[i]));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }

    }

}
