package com.chatapp.gui;

import javax.swing.*;

import com.chatapp.db.UserDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {

    JLabel lblUsername, lblPassword;

    JTextField txtUsername;

    JPasswordField txtPassword;

    JButton btnRegister;

    public RegisterFrame() {

        setTitle("Live Chat - Register");

        setSize(400, 300);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 50, 100, 30);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 50, 180, 30);
        add(txtUsername);

        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 100, 100, 30);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 180, 30);
        add(txtPassword);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(130, 180, 120, 35);
        add(btnRegister);

        btnRegister.addActionListener(new ActionListener() {

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
                        dao.registerUser(
                                username,
                                password);

                if(status) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Registration Successful");

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Registration Failed");
                }
            }
        });

        setVisible(true);
    }
}