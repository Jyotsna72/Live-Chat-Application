package com.chatapp.gui;

import javax.swing.*;

import com.chatapp.db.UserDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    JLabel lblUsername, lblPassword;

    JTextField txtUsername;

    JPasswordField txtPassword;

    JButton btnLogin, btnRegister;

    public LoginFrame() {

        setTitle("Live Chat - Login");

        setSize(450, 350);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 60, 100, 30);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(160, 60, 200, 30);
        add(txtUsername);

        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 120, 100, 30);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 120, 200, 30);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(90, 220, 100, 35);
        add(btnLogin);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(230, 220, 100, 35);
        add(btnRegister);

        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username =
                        txtUsername.getText().trim();

                String password =
                        String.valueOf(
                                txtPassword.getPassword());

                if(username.isEmpty() ||
                   password.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please fill all fields");

                    return;
                }

                UserDAO dao = new UserDAO();

                boolean status =
                        dao.loginUser(
                                username,
                                password);

                if(status) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Login Successful");

                    new ChatFrame(username);

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid Username or Password");
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new RegisterFrame();
            }
        });

        setVisible(true);
    }
}