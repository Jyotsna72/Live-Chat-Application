package com.chatapp.gui;

import javax.swing.*;

import com.chatapp.network.ChatClient;
import com.chatapp.security.AESUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame extends JFrame {

    JTextArea chatArea;

    JTextField txtMessage;

    JButton btnSend, btnAttach;

    ChatClient client;

    String username;

    public ChatFrame(String username) {

        this.username = username;

        setTitle("Live Chat - " + username);

        setSize(700, 550);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();

        chatArea.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(chatArea);

        scroll.setBounds(20, 20, 640, 320);

        add(scroll);

        txtMessage = new JTextField();

        txtMessage.setBounds(20, 370, 500, 35);

        add(txtMessage);

        btnSend = new JButton("Send");

        btnSend.setBounds(540, 370, 120, 35);

        add(btnSend);

        btnAttach = new JButton("Attach");

        btnAttach.setBounds(540, 415, 120, 35);

        add(btnAttach);

        client = new ChatClient();

        btnSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String msg =
                        txtMessage.getText().trim();

                if(msg.isEmpty()) {

                    return;
                }

                String encryptedMessage =
                        AESUtil.encrypt(
                                username + " : " + msg);

                client.sendMessage(
                        encryptedMessage);

                txtMessage.setText("");
            }
        });

        btnAttach.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser =
                        new JFileChooser();

                int result =
                        chooser.showOpenDialog(null);

                if(result ==
                        JFileChooser.APPROVE_OPTION) {

                    String fileName =
                            chooser.getSelectedFile()
                                   .getName();

                    String encryptedMessage =
                            AESUtil.encrypt(
                                    "[FILE] " +
                                    fileName);

                    client.sendMessage(
                            encryptedMessage);
                }
            }
        });

        Thread receiveThread =
                new Thread(() -> {

                    while(true) {

                        String msg =
                                client.receiveMessage();

                        if(msg != null) {

                            String decryptedMessage =
                                    AESUtil.decrypt(msg);

                            chatArea.append(
                                    decryptedMessage + "\n");
                        }
                    }
                });

        receiveThread.start();

        setVisible(true);
    }
}