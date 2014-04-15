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

    public List<CourseDTO> getCourseNames() throws SQLException, ClassNotFoundException {
        if (this.courseNames == null) {
            courseNames = new ArrayList<CourseDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
            Statement statement = connection.createStatement();
            int teacherID = getTeacherID();
            ResultSet resultSet = statement.executeQuery(Constants.GET_COURSES_FOR_TEACHER(teacherID));
            while (resultSet.next()) {
                CourseDTO courseDTO = new CourseDTO();
                String courseName = resultSet.getString(resultSet.findColumn("CName"));
                String courseLength = resultSet.getString(resultSet.findColumn("CourseLength"));
                String courseTerm = resultSet.getString(resultSet.findColumn("Term"));
                int courseID = resultSet.getInt(resultSet.findColumn("C_ID"));
                courseDTO.setCourseID(courseID);
                courseDTO.setCourseLength(courseLength);
                courseDTO.setTeacherID(teacherID);
                courseDTO.setTerm(courseTerm);
                courseDTO.setCourseName(courseName);
                courseNames.add(courseDTO);
            }
            statement.close();
            connection.close();
            resultSet.close();
        }
        return courseNames;
    }

    public void setCourseNames(List<CourseDTO> courseNames) {
        this.courseNames = courseNames;
    }

    private int getTeacherID() throws SQLException, ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Constants.TEACHER_ID_QUERY(userName));
        resultSet.first();
        int teacherID = resultSet.getInt(resultSet.findColumn("T_ID"));
        connection.close();
        statement.close();
        resultSet.close();
        return teacherID;
    }
}
