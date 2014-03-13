package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.pinpointgrowth.constants.Constants;

/**
 * bean used to validate username and password from frontPage.jsp
 */
public class LoginBean implements java.io.Serializable {

    private static final long serialVersionUID = -5178067725637479743L;
    private String username;
    private String password;

    public boolean usernamePasswordSuccess() throws Exception {
        if ((!usernameValid()) || (!passwordValid())) {
            return false;
        } else {
            return userNamePasswordMatchInDB();
        }
    }

    private boolean userNamePasswordMatchInDB() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/Pinpoint";
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        String query = "SELECT * FROM Pinpoint.Instructor WHERE Login = " + '"'
                + username + '"' + " AND PW = " + '"' + password + '"' + ';';
        ResultSet result = statement.executeQuery(query);
        return result.first();
    }

    private boolean passwordValid() {
        if (password != null) {
            if (password.length() < 8) {
                return false;
            } else if (!password.matches("(.*)(\\d)(.*)")) {
                return false;
            } else if (!password.matches("(.*)([a-z]|[A-Z])(.*)")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean usernameValid() {
        if (username != null) {
            if (username.length() == 0) {
                return false;
            }
            return (!username.matches("(.*);(.*)"));
        } else {
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
