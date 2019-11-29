/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.io.Serializable;

/**
 *
 * @author juanb
 */
public class Answer implements Serializable {

    public static final String STOP = "stop";
    public static final String VALID = "valid";
    public static final String VALID_USERNAME = "validusername";
    public static final String ERR = "error";
    public static final String NEWUN = "qwerty";
    public static final String CLOSE = "close";
    public static final String REPEAT = "repeat";
    private String answer = null;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
