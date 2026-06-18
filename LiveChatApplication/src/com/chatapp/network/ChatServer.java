package com.chatapp.network;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        try {

            ServerSocket server =
                    new ServerSocket(5000);

            System.out.println(
                    "Server Started...");

            while(true) {

                Socket socket =
                        server.accept();

                System.out.println(
                        "Client Connected");

                ClientHandler client =
                        new ClientHandler(socket);

                client.start();
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}