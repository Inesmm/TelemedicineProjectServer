/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

/**
 *
 * @author juanb
 */
/*public class Proving {

    public static void main(String[] args) {
        ArrayList<UserPassword> userPasswordList = PersistenceOp.loadUserPaswordList(Utils.DIRECTORY, Utils.FILENAME_UP);
        Iterator<UserPassword> it = userPasswordList.iterator();
        while (it.hasNext()) {
            UserPassword us = (UserPassword) it.next();
            System.out.println(us.toString());
        }

      ArrayList<UserInfo> userInfoList = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        Iterator<UserInfo> ite = userInfoList.iterator();
        while (ite.hasNext()) {
            UserInfo info = (UserInfo) ite.next();
            System.out.println(info.toString());
            ArrayList<Phydata> phydataList = info.getPhydataArray();

            Iterator<Phydata> ite_data = phydataList.iterator();
            while (ite_data.hasNext()) {
                Phydata phydata = (Phydata) ite_data.next();
                if (phydata.getAccRec() == null) {
                    System.out.println("There is no accelerometer recorded");
                } else {
                    int[][] data = phydata.getAccRec();
                    for (int j = 0; j < data[0].length; j++) {
                        for (int i = 0; i < data.length; i++) {
                            System.out.println("values: " + data[i][j]);
                        }
                    }
              }
                if (phydata.geteMGRec() == null) {
                    System.out.println("There is no emg recorded");
                } else {
                    int[][] data = phydata.geteMGRec();
                    for (int j = 0; j < data[0].length; j++) {
                        for (int i = 0; i < data.length; i++) {
                            System.out.println("values: " + data[i][j]);
                        }
                    }
                }
            }
    }

}
}*/
