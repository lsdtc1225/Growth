package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.pinpointgrowth.constants.Constants;

public class NewAssignmentSaveActionBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8685326053815203440L;
    private String assignmentName;
    private int rubricID;
    private int courseID;

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getRubricID() {
        return rubricID;
    }

    public void setRubricID(int rubricID) {
        this.rubricID = rubricID;
    }

    public boolean saveNewAssignmentData() throws SQLException,
            ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.executeUpdate(Constants.ADD_ASSIGNMENT(assignmentName,
                rubricID, courseID));

        statement.close();
        con.close();
        return true;

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

}
