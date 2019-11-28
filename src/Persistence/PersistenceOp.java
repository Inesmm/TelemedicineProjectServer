/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author juanb
 */
public final class PersistenceOp {
    private static final String algorithm = "DES";
    private static final byte[] array = new byte[8];
    private static final String key = new String(array, Charset.forName("UTF-8"));
    //DIRECTORY AND FILENAME WHERE TO SAVE ALL THE DATA
    public static int saveUserInfo(String directory, String fileName, UserInfo user) {
        //Save user. (If there is no even file, it creates)
        //if the USERNAME already exists returns -1, if not returns 0;
        ArrayList<UserInfo> usersInfoList = null;
        File direct = new File(directory);
        File file = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            file = new File(directory, fileName);
            usersInfoList = loadUserInfo(directory, fileName);
            if (!Utils.checkUserName(user.getUserName(), usersInfoList)) {
                System.out.println("index: " + Utils.getArrayIndexUserName(user.getUserName(), usersInfoList));
                usersInfoList.remove(Utils.getArrayIndexUserName(user.getUserName(), usersInfoList));
                usersInfoList.add(user);
                return 1;
            }
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
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
        return 1;
    }

    public static int saveUserPaswordList(String directory, String fileName,
            UserPassword userPassword, ArrayList<UserPassword> userpasswordList) {
        //Save user. (If there is no even file, it creates)
        //if the USERNAME already exists returns -1, if not returns 0;
        File direct = new File(directory);
        File file = null;
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {

            file = new File(directory, fileName);
            //System.out.println("dentro:" + Utils.checkUserNameList(userPassword.getUserName(), userpasswordList));
            if (!Utils.checkUserNameList(userPassword.getUserName(), userpasswordList)) {
                System.out.println("index: " + Utils.getArrayIndexUserPassword(userPassword.getUserName(), userpasswordList));
                userpasswordList.remove(Utils.getArrayIndexUserPassword(userPassword.getUserName(), userpasswordList));
                userpasswordList.add(userPassword);
                return 1;
            }
            //System.out.println("traza2");
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            userpasswordList.add(userPassword);
            Iterator<UserPassword> it = userpasswordList.iterator();
            objectOutputStream.writeObject(userpasswordList.size());
            while (it.hasNext()) {
                UserPassword us = (UserPassword) it.next();
                objectOutputStream.writeObject(us);
            }
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
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

        return usersInfoList;

    }

    public static ArrayList<UserPassword> loadUserPaswordList(String directory, String fileName) {
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
                    userPasswordList.add((UserPassword) objectInputStream.readObject());
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

        return userPasswordList;

    }
    private static UserPassword encrypt(UserPassword encrypted)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keyspec = new DESKeySpec(key.getBytes());
        SecretKey secretKey = secretKeyFactory.generateSecret(keyspec);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] password = encrypted.getPassword().getBytes("UTF8");
        byte[] username = encrypted.getUserName().getBytes("UTF8");
        byte[] encryptedPassword = cipher.doFinal(password);
        byte[] encryptedUsername = cipher.doFinal(username);
        String passEnc = new BASE64Encoder().encode(encryptedPassword);
        String userEnc = new BASE64Encoder().encode(encryptedUsername);
        UserPassword encryption = new UserPassword(userEnc, passEnc);
        return encryption;
    }
    private static UserPassword decrypt(UserPassword decrypted) throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keyspec = new DESKeySpec(key.getBytes());
        SecretKey secretKey = secretKeyFactory.generateSecret(keyspec);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedPass = new BASE64Decoder().decodeBuffer(decrypted.getPassword());
        byte[] encryptedUser = new BASE64Decoder().decodeBuffer(decrypted.getUserName());
        byte[] decryptedPass = cipher.doFinal(encryptedPass);
        byte[] decryptedUser = cipher.doFinal(encryptedUser);
        String decPass = new String(decryptedPass);
        String decUser = new String(decryptedUser);
        UserPassword decryption = new UserPassword(decUser, decPass);
        return decryption;
    }

}
