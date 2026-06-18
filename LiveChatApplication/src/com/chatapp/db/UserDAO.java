package com.chatapp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(String username,
                                String password) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "INSERT INTO users(username,password) VALUES(?,?)";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean loginUser(String username,
                             String password) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs =
                    pst.executeQuery();

            return rs.next();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}