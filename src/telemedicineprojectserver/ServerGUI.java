/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telemedicineprojectserver;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author juanb
 */
public class ServerGUI extends javax.swing.JFrame {

    private ServerSocket serverSocket;
    private Socket socket;
    private String password;

    /**
     * Creates new form UserFr
     */
    public ServerGUI() {
        initComponents();
        this.setSize(new Dimension(800, 540));
        this.setLocationRelativeTo(null);
        jPanel1.setSize(this.getSize());
        jPanel1.setBackground(new Color(153, 204, 0));
        /* outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            socketUtils = new SocketUtils(socket,objectOutputStream,outputStream,inputStream,objectInputStream);*/
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        stop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter password to close the server");
        jLabel2.setToolTipText("");

        stop.setBackground(new java.awt.Color(0, 153, 0));
        stop.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        stop.setForeground(new java.awt.Color(255, 255, 255));
        stop.setText("STOP");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        password = jTextField1.getText();
        if (password.equalsIgnoreCase("cierratesesamo")) {
            stopServer();
        } else {
            jTextField1.setBorder(new LineBorder(Color.red, 2));
            JOptionPane.showMessageDialog(new JFrame(), "INCORRECT PASSWORD"
                    + "", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_stopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables

    public void startServer() {

        try {

            serverSocket = new ServerSocket(9000);
            System.out.println("server Created...");
            try {
                while (true) {
                    //Thie executes when we have a client
                    socket = serverSocket.accept();
                    System.out.println("New client..." + socket.isConnected());
                    new Thread(new ServerUserThreads(socket)).start();
                }
            } finally {
                releaseResourcesServer(serverSocket, socket);
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void stopServer() {
        System.out.println("Stoping Server...");
        releaseResourcesServer(serverSocket, socket);
        System.out.println("Server Close...");
        System.exit(0);
    }

    private static void releaseResourcesServer(ServerSocket serverSocket, Socket socket) {
        if (socket != null) {

        } else {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
