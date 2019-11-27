/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

/**
 *
 * @author juanb
 */
public class AnswerServer {

    public static final String STOP = "stop";
    public static final String VALID = "valid";
    public static final String VALID_USERNAME = "validusername";
    public static final String ERR = "error";

    private String answer = null;

    public AnswerServer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
