package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.constants.Constants;

public class CourseBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 312526693132916160L;
    private CourseDTO courseDTO;
    private int courseIDForSetup;

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public boolean createDTO() throws SQLException, ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery(Constants
                .GET_COURSE(courseIDForSetup));
        results.first();
        courseDTO = new CourseDTO();
        String courseName = results.getString(results.findColumn("CName"));
        String courseLength = results.getString(results
                .findColumn("CourseLength"));
        String courseTerm = results.getString(results.findColumn("Term"));
        int courseID = results.getInt(results.findColumn("C_ID"));
        int teacherID = results.getInt(results.findColumn("T_ID"));
        courseDTO.setCourseID(courseID);
        courseDTO.setCourseLength(courseLength);
        courseDTO.setTeacherID(teacherID);
        courseDTO.setTerm(courseTerm);
        courseDTO.setCourseName(courseName);

        statement.close();
        con.close();
        return true;
    }

    public int getCourseIDForSetup() {
        return courseIDForSetup;
    }

    public void setCourseIDForSetup(int courseIDForSetup) {
        this.courseIDForSetup = courseIDForSetup;
    }

}
