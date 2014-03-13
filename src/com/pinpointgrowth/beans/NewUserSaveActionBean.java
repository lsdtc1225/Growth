package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

import com.pinpointgrowth.constants.Constants;

/*
 * 
 * Bean for save of new user page input
 * */

public class NewUserSaveActionBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4165337510798918353L;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String schoolDistrict;
    private String username;
    private String password;

    public boolean saveNewUserData() throws Exception {
        firstName = capitalize(firstName.trim());
        lastName = capitalize(lastName.trim());
        emailAddress = emailAddress.trim();
        schoolDistrict = capitalize(schoolDistrict.trim());
        username = username.trim();

        // try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/Pinpoint";
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        String insertStatement = "INSERT INTO Pinpoint.Instructor (FName,LName,Email,Login,PW,District) VALUES ("
                + '"'
                + firstName
                + '"'
                + ','
                + '"'
                + lastName
                + '"'
                + ','
                + '"'
                + emailAddress
                + '"'
                + ','
                + '"'
                + username
                + '"'
                + ','
                + '"'
                + password
                + '"'
                + ','
                + '"'
                + schoolDistrict
                + '"'
                + ");";
        statement.executeUpdate(insertStatement);
        // } catch (Exception e) {
        // return false;
        // }

        return true;
    }

    private String capitalize(String input) {
        String[] tokens = input.split("\\s");
        String result = "";
        for (String token : tokens) {
            if (result.length() > 0) {
                result = result + " ";
            }
            String word = "";
            if (token.length() > 1) {
                word = Character.toUpperCase(token.charAt(0))
                        + token.substring(1);
            } else {
                word = Character.toUpperCase(token.charAt(0)) + "";
            }
            result = result + word;
        }
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
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
