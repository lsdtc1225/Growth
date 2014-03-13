package com.pinpointgrowth.viewcourse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.beans.CourseBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;

public class ViewCourse extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7673528038242907485L;
    private String userName;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int courseID = Integer.parseInt(request.getParameter("cID"));

        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();
        try {
            CourseDTO courseDTO = setupCourseDTO(courseID);
            CourseBean courseBean = new CourseBean();
            courseBean.setCourseDTO(courseDTO);
            request.setAttribute("courseBean", courseBean);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/viewCourse.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

    private CourseDTO setupCourseDTO(int courseID) throws SQLException,
            ClassNotFoundException {
        CourseDTO courseDTO = new CourseDTO();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        int teacherID = getTeacherID(statement);
        ResultSet results = statement.executeQuery(Constants
                .GET_COURSE(courseID));
        results.first();
        String courseName = results.getString(results.findColumn("CName"));
        String courseLength = results.getString(results
                .findColumn("CourseLength"));
        String courseTerm = results.getString(results.findColumn("Term"));
        courseDTO.setCourseID(courseID);
        courseDTO.setCourseLength(courseLength);
        courseDTO.setTeacherID(teacherID);
        courseDTO.setTerm(courseTerm);
        courseDTO.setCourseName(courseName);

        statement.close();
        con.close();
        return courseDTO;
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
