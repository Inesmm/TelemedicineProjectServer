/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOs;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inesu
 */
public class SocketUtils {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;

    public SocketUtils(Socket socket, ObjectOutputStream objectOutputStream, OutputStream outputStream, InputStream inputStream, ObjectInputStream objectInputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.objectInputStream = objectInputStream;
    }

    public SocketUtils(Socket socket) {

        try {
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);
            this.inputStream = socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public Object readObject() {
        System.out.println("LEYENDO OBJETO");

        Object tmp = null;
        try {
            tmp = this.objectInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }

    public void writeObject(Object tmp) {
        System.out.println("ESCRIBIENDO OBJETO");
        try {
            this.objectOutputStream.writeObject(tmp);
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void releaseResources() {
        System.out.println("Releasing Resources...");
        try {
            this.objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.inputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


    
