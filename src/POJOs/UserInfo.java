/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author juanb
 */
public class UserInfo implements Serializable {
////

    private String userName;
    private String name;
    private int age;
    private ArrayList phydataArray;

    public UserInfo(String name, String userName, int age, ArrayList phydataArray) {
        this.name = name;
        this.userName = userName;
        this.age = age;
        this.phydataArray = phydataArray;

    }

    public UserInfo(String name, String userName, int age) {
        this.userName= userName;
        this.name = name;
        this.age = age;
        this.phydataArray = null;

    }

    public UserInfo() {
        this.userName = null;
        this.name = null;
        this.age = 0;
        this.phydataArray = null;

    }

    public void saveMeasure(Phydata phydata) {
        this.phydataArray.add(phydata);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public ArrayList getPhydataArray() {
        return phydataArray;
    }

    public void setPhydataArray(ArrayList phydataArray) {
        this.phydataArray = phydataArray;
    }

    public String toString() {
        return "name: " + this.name + "userName: "+ this.userName +", measures: " + phydataArray.size();
    }

}
