package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.constants.Constants;

public class CourseDisplayBean implements java.io.Serializable {

    private String userName;
    private List<CourseDTO> courseNames;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CourseDTO> getCourseNames() throws SQLException,
            ClassNotFoundException {
        if (this.courseNames == null) {
            courseNames = new ArrayList<CourseDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            int teacherID = getTeacherID(statement);
            ResultSet results = statement.executeQuery(Constants
                    .GET_COURSES_FOR_TEACHER(teacherID));
            while (results.next()) {
                CourseDTO courseDTO = new CourseDTO();
                String courseName = results.getString(results
                        .findColumn("CName"));
                String courseLength = results.getString(results
                        .findColumn("CourseLength"));
                String courseTerm = results.getString(results
                        .findColumn("Term"));
                int courseID = results.getInt(results.findColumn("C_ID"));
                courseDTO.setCourseID(courseID);
                courseDTO.setCourseLength(courseLength);
                courseDTO.setTeacherID(teacherID);
                courseDTO.setTerm(courseTerm);
                courseDTO.setCourseName(courseName);
                courseNames.add(courseDTO);
            }
            statement.close();
            con.close();
        }
        return courseNames;
    }

    public void setCourseNames(List<CourseDTO> courseNames) {
        this.courseNames = courseNames;
    }

    private int getTeacherID(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(Constants
                .TEACHER_ID_QUERY(userName));
        resultSet.first();
        int column = resultSet.findColumn("T_ID");
        int teacherID = resultSet.getInt(column);
        return teacherID;
    }
}
