package com.chatapp.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    Socket socket;

    DataInputStream dis;

    DataOutputStream dos;

    static ArrayList<ClientHandler> clients =
            new ArrayList<>();

    public ClientHandler(Socket socket) {

        try {

            this.socket = socket;

            dis = new DataInputStream(
                    socket.getInputStream());

            dos = new DataOutputStream(
                    socket.getOutputStream());

            clients.add(this);

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            while(true) {

                String encryptedMsg =
                        dis.readUTF();

                System.out.println(
                        "Encrypted : " +
                        encryptedMsg);

                broadcast(encryptedMsg);
            }

        } catch(Exception e) {

            System.out.println(
                    "Client Disconnected");

            clients.remove(this);

            try {

                socket.close();

            } catch(Exception ex) {

                ex.printStackTrace();
            }
        }
    }

    public void broadcast(String msg) {

        try {

            for(ClientHandler client : clients) {

                client.dos.writeUTF(msg);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}