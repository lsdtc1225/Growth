package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	int cID;
	int tID;
	String userName;

	private void getTeacherID() throws ClassNotFoundException, SQLException{
		Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        String getTeacherIDSQL = TraditionalConstants.GET_TEACHER_ID_SQL(userName);
        System.out.print(getTeacherIDSQL);
        ResultSet resultSet = statement.executeQuery(getTeacherIDSQL);
        resultSet.last();
        tID = resultSet.getInt(resultSet.findColumn("T_ID"));
	}

	private void deleteCourse() throws ClassNotFoundException, SQLException{
		Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        String deleteCourseInClassSQL = TraditionalConstants.DELETE_COURSE_IN_CLASS_SQL(cID);
        String deleteCourseInEnrolledSQL = TraditionalConstants.DELETE_COURSE_IN_ENROLLED_SQL(cID);
        String deleteCourseInAssignmentSQL = TraditionalConstants.DELETE_COURSE_IN_ASSIGNMENT_SQL(cID);
        String deleteCourseInExtensionActivitySQL = TraditionalConstants.DELETE_COURSE_IN_EXTENSIONACTIVITY_SQL(cID);
        String deleteCourseInPreTestSQL = TraditionalConstants.DELETE_COURSE_IN_PRE_TEST_SQL(cID);
        String deleteCourseInPostTestSQL = TraditionalConstants.DELETE_COURSE_IN_POST_TEST_SQL(cID);
        String deleteCourseInStudentScoreSQL = TraditionalConstants.DELETE_COURSE_IN_STUDENTSCORE_SQL(cID);

        System.out.println(deleteCourseInClassSQL);
        System.out.println(deleteCourseInEnrolledSQL);
        System.out.println(deleteCourseInAssignmentSQL);
        System.out.println(deleteCourseInExtensionActivitySQL);
        System.out.println(deleteCourseInPreTestSQL);
        System.out.println(deleteCourseInPreTestSQL);
        System.out.println(deleteCourseInPostTestSQL);
        System.out.println(deleteCourseInStudentScoreSQL);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		cID = Integer.parseInt(request.getParameter("cID"));
		LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();

        try {
            getTeacherID();
            deleteCourse();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        System.out.println(tID);
	}

}
