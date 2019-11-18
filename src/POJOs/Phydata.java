/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.util.Date;

/**
 *
 * @author juanb
 */
public class Phydata {

    private Date date;
    private int[][] eMGRec;
    private int[][] accRec;

    public Phydata(Date date, int[][] eMGRec, int[][] accRec) {
        this.date = date;
        this.eMGRec = eMGRec;
        this.accRec = accRec;
    }

    public Phydata() {
        this.date = null;
        this.eMGRec = null;
        this.accRec = null;

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

}
