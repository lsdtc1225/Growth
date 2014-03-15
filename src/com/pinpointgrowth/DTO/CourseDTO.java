package com.pinpointgrowth.DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pinpointgrowth.constants.Constants;

public class CourseDTO {
    private int teacherID;
    private int courseID;
    private String term;
    private String courseName;
    private String courseLength;
    private String locked;

    public boolean getLocked() throws ClassNotFoundException, SQLException {
        if (locked == null) {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet course = statement.executeQuery(Constants
                    .GET_COURSE(courseID));
            course.first();
            locked = course.getString(course.findColumn("Locked")).trim();
            statement.close();
            con.close();
        }
        if (locked.equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

}
