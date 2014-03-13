package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pinpointgrowth.constants.Constants;

/*
 * 
 * Bean for validation of new user page input
 * */

public class NewUserValidationBean implements java.io.Serializable {

    private static final long serialVersionUID = 957874234842097700L;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String schoolDistrict;
    private String username;
    private String password;
    private String confirmPassword;
    private String key;

    public boolean keyValid() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet district = statement.executeQuery(Constants
                .GET_DISTRICT_WITH_NAME(schoolDistrict));
        boolean result = false;
        while (district.next()) {
            int realKey = district.getInt(district.findColumn("DistrictKey"));
            if (Integer.parseInt(getKey().trim()) == realKey) {
                result = true;
            }
        }
        statement.close();
        con.close();
        return result;

    }

    public boolean firstNameValid() {
        if (firstName != null) {
            if (firstName.length() == 0) {
                return false;
            }
            return (!firstName
                    .matches("(.*)(\\d|@|~|!|#|\\$|%|\\^|&|\\+|=|;|\\?|<|>|\\(|\\)|\\*|_|\\.|,|:)(.*)"));
        } else {
            return false;
        }
    }

    public boolean lastNameValid() {
        if (lastName != null) {
            if (lastName.length() == 0) {
                return false;
            }
            return (!lastName
                    .matches("(.*)(@|~|!|#|\\$|%|\\^|&|\\+|=|;|\\?|<|>|\\(|\\)|\\*|_|,|:)(.*)"));
        } else {
            return false;
        }
    }

    public boolean emailAddressValid() {
        if (emailAddress != null) {
            if (emailAddress.length() == 0) {
                return false;
            } else if (emailAddress.matches("(.*);(.*)")) {
                return false;
            }
            return emailAddress.matches("(.*)@(.*)");
        } else {
            return false;
        }
    }

    public boolean schoolDistrictValid() {
        if (schoolDistrict != null) {
            if (schoolDistrict.length() > 0) {
                if (schoolDistrict.matches("(.*);(.*)")) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean usernameValid() {
        if (username != null) {
            if (username.length() == 0) {
                return false;
            }
            return (!username.matches("(.*);(.*)"));
        } else {
            return false;
        }
    }

    public boolean emailTaken() throws Exception {
        if (emailAddress != null) {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            String query = Constants.EMAIL_TAKEN_QUERY(emailAddress);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result.first();
        } else {
            return false;
        }
    }

    public boolean usernameTaken() throws Exception {
        if (username != null) {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            String query = "SELECT * FROM Pinpoint.Instructor WHERE Login = "
                    + '"' + username + '"' + ';';
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result.first();
        } else {
            return false;
        }
    }

    public boolean passwordValid() {
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

    public boolean passwordsMatch() {
        if ((password != null) && (confirmPassword != null)) {
            return (password.equals(confirmPassword));
        } else {
            return false;
        }
    }

    public boolean allFieldsValid() throws Exception {
        return (firstNameValid() && lastNameValid() && emailAddressValid()
                && schoolDistrictValid() && (!usernameTaken())
                && usernameValid() && passwordsMatch() && passwordValid()
                && keyValid() && (!emailTaken()));
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
