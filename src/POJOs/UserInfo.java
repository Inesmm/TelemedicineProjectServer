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

    private String name;
    private String password;
    private String userName;
    private int age;
    private ArrayList phydataArray;

    public UserInfo(String name, String userName, String password, int age, ArrayList phydataArray) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.age = age;
        this.phydataArray = phydataArray;

    }

    public UserInfo(String name, String userName, String password, int age) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.age = age;
        this.phydataArray = null;

    }

    public UserInfo() {
        this.name = null;
        this.password = null;
        this.userName = null;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "name: " + this.name + ", UserName: " + this.userName + ", measures: " + phydataArray.size();
    }

}
