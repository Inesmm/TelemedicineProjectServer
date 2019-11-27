/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import POJOs.UserInfo;
import POJOs.UserPassword;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/*import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import static org.jfree.chart.ui.UIUtils.centerFrameOnScreen;
import org.jfree.data.xy.DefaultXYDataset;*/
/**
 *
 * @author juanb
 */
public final class Utils extends Object {
    //DIRECTORY AND FILENAME WHERE TO SAVE ALL THE DATA

    public static final String NEWUN = "qwerty";
    public static final String DIRECTORY = "data";
    public static final String FILENAME = "UserInfo.dat";
    public static final String FILENAME_UP = "UserPassword.dat";

    /* public static void GraphPhydata(int[][] dataRec) {
        int row = dataRec.length;
        int col = dataRec[0].length;
        double[][] values = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                values[i][j] = Double.valueOf(dataRec[i][j]);
            }
        }
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("hola", values);
        JFreeChart lineChart = ChartFactory.createXYLineChart("EMG", "Seconds", "Volts", dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartFrame panel = new ChartFrame("", lineChart);
        panel.pack();
        panel.setVisible(true);
        centerFrameOnScreen(panel);

    }*/

 /*public static boolean checkUserInfo(UserInfo userToCheck, ArrayList<UserInfo> userInfoList) {
        //TRUE if it doesn`t exist;
        String userNameStr = userToCheck.getUserName();
        String loaduserName = null;
        boolean check = true;
        Iterator<UserInfo> it = userInfoList.iterator();
        while (it.hasNext()) {
            loaduserName = it.next().getUserName();
            if (loaduserName.compareTo(userNameStr) == 0) {
                check = false;

            }
        }
        return check;
    }*/
    //TRUE if it doesn´t exist
    public static boolean checkUserName(String userName, ArrayList<UserInfo> userInfoList) {
        //TRUE if it doesn`t exist;
        String loaduserName = null;
        boolean check = true;
        Iterator<UserInfo> it = userInfoList.iterator();
        while (it.hasNext()) {
            loaduserName = it.next().getUserName();
            if (loaduserName.compareTo(userName) == 0) {
                check = false;
            }
        }
        return check;
    }

    public static boolean checkUserNameList(String userName, ArrayList<UserPassword> userPasswordList) {
        //TRUE if it doesn`t exist;
        String loaduserName = null;
        boolean check = true;
        Iterator<UserPassword> it = userPasswordList.iterator();
        while (it.hasNext()) {
            loaduserName = it.next().getUserName();
            if (loaduserName.compareTo(userName) == 0) {
                check = false;
            }
        }
        return check;
    }

    /*public static boolean checkUserName(String userName) {
        //TRUE if it doesn`t exist;
        ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(DIRECTORY, FILENAME);
        String loaduserName = null;
        boolean check = true;
        Iterator<UserInfo> it = userInfoList.iterator();
        while (it.hasNext()) {
            loaduserName = it.next().getUserName();
            if (loaduserName.compareTo(userName) == 0) {
                check = false;
            }
        }
        return check;
    }*/
    //return index where a PArticular USerNAme is Saved
    public static int getArrayIndexUserName(String userName, ArrayList<UserInfo> userInfoList) {
        UserInfo useInfo = null;
        Iterator<UserInfo> it = userInfoList.iterator();
        while (it.hasNext()) {
            useInfo = it.next();
            if (useInfo.getUserName().compareTo(userName) == 0) {
                return userInfoList.indexOf(useInfo);
            }
        }
        return -1;
    }

    public static int getArrayIndexUserPassword(String userName, ArrayList<UserPassword> userPassword) {
        UserPassword usePass = null;
        Iterator<UserPassword> it = userPassword.iterator();
        while (it.hasNext()) {
            usePass = it.next();
            if (usePass.getUserName().compareTo(userName) == 0) {
                return userPassword.indexOf(usePass);
            }
        }
        return -1;
    }

    public static UserPassword takeOutCode(UserPassword userPassword) {
        String[] userName = userPassword.getUserName().split(Utils.NEWUN);
        userPassword.setUserName(userName[0]);
        return userPassword;
    }

    public static UserInfo getUserInfo(String userName, ArrayList<UserInfo> userInfoList) {
        UserInfo userInfo = null;
        int index = getArrayIndexUserName(userName, userInfoList);
        return (UserInfo) userInfoList.get(index);
    }

    public static UserPassword getUserPassword(String userName, ArrayList<UserPassword> userPasswordList) {
        UserPassword userPassword = null;
        int index = getArrayIndexUserPassword(userName, userPasswordList);
        return (UserPassword) userPasswordList.get(index);
    }

    public static String getRadioButton(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    //RETURN TRUE IF IT IS CORRECT
    public static boolean checkCorrectPassword(String userNametocheck, String passwordtocheck, ArrayList<UserPassword> userPasswordList) {
        int index = Utils.getArrayIndexUserPassword(userNametocheck, userPasswordList);
        UserPassword userPassword = userPasswordList.get(index);
        if ((userPassword.getUserName().compareTo(userNametocheck) == 0)
                && (userPassword.getPassword().compareTo(passwordtocheck)) == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static String charToString(char[] chain) {
        String password = "";
        for (int i = 0; i < chain.length; i++) {
            password = password + chain[i];
        }
        return password;
    }

    //TRUE if it is only String
    public static boolean checkString(String chain) {
        boolean check = true;
        char prof;
        chain = chain.replace(" ", "");
        for (int i = 0; i < chain.length(); i++) {
            prof = chain.charAt(i);
            if (!Character.isAlphabetic(prof)) {
                check = false;
            }
        }
        return check;
    }

    //TRUE if it is only numbers
    public static boolean checkNum(String numStr) {
        int num;
        try {
            num = Integer.parseInt(numStr);
            return true;

        } catch (NumberFormatException ex) {
            return false;
        }

    }

}
