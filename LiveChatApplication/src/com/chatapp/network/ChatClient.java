package com.chatapp.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ChatClient {

    Socket socket;

    DataOutputStream dos;

    DataInputStream dis;

    public ChatClient() {

        try {

            socket =
                    new Socket(
                            "localhost",
                            5000);

            dos =
                    new DataOutputStream(
                            socket.getOutputStream());

            dis =
                    new DataInputStream(
                            socket.getInputStream());

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {

        try {

            dos.writeUTF(msg);

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public String receiveMessage() {

        try {

            return dis.readUTF();

        } catch(Exception e) {

            return null;
        }
    }
}