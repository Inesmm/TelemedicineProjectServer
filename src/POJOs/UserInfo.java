/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/*
 *
 * @author juanb
 */
public class UserInfo implements Serializable {
////

    private ArrayList<Phydata> phydataArray;
    private String userName;
    private String name;
    private int age;

    public ArrayList<Phydata> getPhydataArray() {
        return phydataArray;
    }

    public void setPhydataArray(ArrayList<Phydata> phydataArray) {
        this.phydataArray = phydataArray;
    }

    public UserInfo(String userName, String name, int age) {
        this.name = name;
        this.userName = userName;
        this.age = age;
        this.phydataArray = new ArrayList<Phydata>();
    }

    public UserInfo(String userName, String name, int age, ArrayList<Phydata> phydataArray) {
        this.name = name;
        this.userName = userName;
        this.age = age;
        this.phydataArray = phydataArray;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo() {

        this.age = 0;
        this.phydataArray = null;

    }

    public void saveMeasure(Phydata phydata) {
        this.phydataArray.add(phydata);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String printAll() {
        String print = this.userName + "\n";
        Object tmp;
        Phydata phydata = null;
        Iterator it = this.phydataArray.iterator();
        while (it.hasNext()) {
            tmp = it.next();
            phydata = (Phydata) tmp;
            print = print + phydata.printAllData() + "\n";

        }
        return print;

    }

}
