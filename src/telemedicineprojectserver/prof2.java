/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import POJOs.UserInfo;
import Persistence.PersistenceOp;
import Persistence.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author juanb
 */
public class prof2 {

    public static void main(String[] args) {
        ArrayList<UserInfo> userInfoList2 = new ArrayList<UserInfo>();
        UserInfo userinfo;
        Object tmp;
        userInfoList2 = PersistenceOp.loadUserInfo(Utils.DIRECTORY, Utils.FILENAME);
        Iterator it = userInfoList2.iterator();
        while (it.hasNext()) {
            tmp = it.next();
            userinfo = (UserInfo) tmp;
            System.out.println(userinfo.printAll());
        }
    }

}
