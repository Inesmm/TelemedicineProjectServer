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

    private UserPassword userPassword;
    private String name;
    private int age;
    private ArrayList phydataArray;

    public UserInfo(String name, UserPassword userPassword, int age, ArrayList phydataArray) {
        this.name = name;
        this.userPassword = userPassword;
        this.age = age;
        this.phydataArray = phydataArray;

    }

    public UserInfo(String name, UserPassword userPassword, int age) {
        this.userPassword = userPassword;
        this.name = name;
        this.age = age;
        this.phydataArray = null;

    }

    public UserInfo() {
        this.userPassword = null;
        this.name = null;
        this.age = 0;
        this.phydataArray = null;

    }

    public void saveMeasure(Phydata phydata) {
        this.phydataArray.add(phydata);

    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
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
        return "name: " + this.name + ", measures: " + phydataArray.size();
    }

}
