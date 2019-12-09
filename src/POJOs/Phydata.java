/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author juanb
 */
public class Phydata implements Serializable {

    private Date date;
    private int[][] eMGRec;
    private int[][] accRec;
    private String symptons;

    public Phydata(Date date, int[][] eMGRec, int[][] accRec, String symptons) {
        this.date = date;
        this.eMGRec = eMGRec;
        this.accRec = accRec;
        this.symptons = symptons;
    }

    public Phydata(Date date) {
        this.date = date;
        this.eMGRec = null;
        this.accRec = null;
        this.symptons = null;
    }

    public Phydata(Date date, int[][] eMGRec, int[][] accRec) {
        this.date = date;
        this.eMGRec = eMGRec;
        this.accRec = accRec;
        this.symptons = null;
    }

    public Phydata() {
        this.date = new Date();
        this.eMGRec = null;
        this.accRec = null;
        this.symptons = null;
    }

    public String getSymptons() {
        return symptons;
    }

    public void setSymptons(String symptons) {
        this.symptons = symptons;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    //Date date = new Date(); // this object contains the current date value
    //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    //System.out.println(formatter.format(date));

    public int[][] geteMGRec() {
        return eMGRec;
    }

    public void seteMGRec(int[][] eMGRec) {
        this.eMGRec = eMGRec;
    }

    public int[][] getAccRec() {
        return accRec;
    }

    public void setAccRec(int[][] accRec) {
        this.accRec = accRec;
    }

    public String printAllData() {
        String data = ""; // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        data = data + "Date: " + formatter.format(date) + "\n";
        if (this.symptons != null) {
            data = data + "\tSym: " + this.symptons + "\n";
        } else {
            data = data + "\tSym: NO\n";
        }
        if (this.accRec != null) {
            data = data + "\tAcc: " + "There is value for Acc\n";
        } else {
            data = data + "\tAcc:NO\n";
        }
        if (this.eMGRec != null) {
            data = data + "\tEMG: " + "There is value for EMG";
        } else {
            data = data + "\tEMG:NO";
        }
        return data;
    }

}
