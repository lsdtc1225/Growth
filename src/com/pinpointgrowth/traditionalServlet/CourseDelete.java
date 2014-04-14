package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;
import com.pinpointgrowth.traditionalDTO.EvaluationDTO;
import com.pinpointgrowth.traditionalBeans.PreTestSetupBean;
import com.pinpointgrowth.traditionalBeans.StudentEvaluationBean;

/**
 * Servlet implementation class CourseDelete
 */

@WebServlet(urlPatterns = { "/CourseDelete" })
public class CourseDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int cID;
    private int tID;
    private String userName;
    private ArrayList<Integer> studentIDList;

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
    
    // private void getStudentIDList() throws ClassNotFoundException, SQLException{
    //     Class.forName(Constants.JDBC_DRIVER_CLASS);
    //     Connection con = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
    //     Statement statement = con.createStatement();
        
    //     String getStudentIDListSQL = TraditionalConstants.GET_STUDENT_ID_SQL(cID);
    //     ResultSet resultSet = statement.executeQuery(getStudentIDListSQL);
    //     while(resultSet.next()){
    //         int studentID = Integer.parseInt(resultSet.getString(resultSet.findColumn("S_ID")));
    //         studentIDList.add(studentID);
    //     }
    // }

    // private void deleteRelatedStudent() throws ClassNotFoundException, SQLException{
    //     Class.forName(Constants.JDBC_DRIVER_CLASS);
    //     Connection con = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
    //     PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Pinpoint.Enrolled WHERE S_ID = ?");
    //     for(Integer i : studentIDList){
    //         preparedStatement.setInt(1, i);
    //         preparedStatement.executeUpdate();
    //     }

    //     preparedStatement = con.prepareStatement("DELETE FROM Pinpoint.StudObjLookup WHERE S_ID = ?");
    //     for(Integer i : studentIDList){
    //         preparedStatement.setInt(1, i);
    //         preparedStatement.executeUpdate();
    //     }

    //     preparedStatement = con.prepareStatement("DELETE FROM Pinpoint.Student WHERE S_ID = ?");
    //     for(Integer i : studentIDList){
    //         preparedStatement.setInt(1, i);
    //         preparedStatement.executeUpdate();
    //     }
    // }

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

        cID = Integer.parseInt(request.getParameter("cID"));
        LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();

        try {
            getTeacherID();
            // getStudentIDList();
            // if(studentIDList.size>0){
            //     deleteRelatedStudent();
            // }
            deleteCourse();

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String nextJSP = "/mainUserPage.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

}
