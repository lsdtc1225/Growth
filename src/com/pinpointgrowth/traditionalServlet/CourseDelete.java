package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;


@WebServlet(urlPatterns = { "/CourseDelete" })
public class CourseDelete extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginBean loginBean;
    private int cID;
    private String userName;
    private int tID;

    private void getTeacherID() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String getTeacherIDSQL = TraditionalConstants.GET_TEACHER_ID_SQL(userName);
        ResultSet resultSet = statement.executeQuery(getTeacherIDSQL);
        resultSet.last();
        tID = resultSet.getInt(resultSet.findColumn("T_ID"));

        connection.close();
        statement.close();
        resultSet.close();
    }

    private void deleteCourse() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String deleteCourseInClassSQL = TraditionalConstants.DELETE_COURSE_IN_CLASS_SQL(cID);
        String deleteCourseInEnrolledSQL = TraditionalConstants.DELETE_COURSE_IN_ENROLLED_SQL(cID);
        String deleteCourseInAssignmentSQL = TraditionalConstants.DELETE_COURSE_IN_ASSIGNMENT_SQL(cID);
        String deleteCourseInExtensionActivitySQL = TraditionalConstants.DELETE_COURSE_IN_EXTENSIONACTIVITY_SQL(cID);
        String deleteCourseInPreTestSQL = TraditionalConstants.DELETE_COURSE_IN_PRE_TEST_SQL(cID);
        String deleteCourseInPostTestSQL = TraditionalConstants.DELETE_COURSE_IN_POST_TEST_SQL(cID);
        String deleteCourseInStudentScoreSQL = TraditionalConstants.DELETE_COURSE_IN_STUDENTSCORE_SQL(cID);
        String deleteCourseInWeightSQL = TraditionalConstants.DELETE_COURSE_IN_WEIGHT_SQL(cID);
        
        statement.executeUpdate(deleteCourseInWeightSQL);
        statement.executeUpdate(deleteCourseInEnrolledSQL);
        statement.executeUpdate(deleteCourseInAssignmentSQL);
        statement.executeUpdate(deleteCourseInExtensionActivitySQL);
        statement.executeUpdate(deleteCourseInPreTestSQL);
        statement.executeUpdate(deleteCourseInPostTestSQL);
        statement.executeUpdate(deleteCourseInStudentScoreSQL);
        statement.executeUpdate(deleteCourseInClassSQL);

        connection.close();
        statement.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        cID = Integer.parseInt(request.getParameter("cID"));
        userName = loginBean.getUsername();

        try {
            getTeacherID();
            deleteCourse();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String nextJSP = "/mainUserPage.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    } 
}
