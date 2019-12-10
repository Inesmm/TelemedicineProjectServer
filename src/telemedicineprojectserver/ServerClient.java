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
public class ServerClient {

    public static void main(String[] args) {
        ServerClientGUI serverGUI = new ServerClientGUI();
        serverGUI.setVisible(true);
        serverGUI.startServer();
    }

}
