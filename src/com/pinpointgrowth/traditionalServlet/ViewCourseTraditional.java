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

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.beans.CourseBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;

/**
 * Servlet implementation class ViewCourseTraditional
 */
@WebServlet(urlPatterns = { "/ViewCourseTraditional" })
public class ViewCourseTraditional extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String userName;
    private int cID;
    private LoginBean loginBean;

    
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

    private CourseDTO setupCourseDTO(int cID) throws SQLException, ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        int teacherID = getTeacherID();
        ResultSet resultSet = statement.executeQuery(Constants.GET_COURSE(cID));
        resultSet.first();
        String courseName = resultSet.getString(resultSet.findColumn("CName"));
        String courseLength = resultSet.getString(resultSet.findColumn("CourseLength"));
        String courseTerm = resultSet.getString(resultSet.findColumn("Term"));
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseID(cID);
        courseDTO.setCourseLength(courseLength);
        courseDTO.setTeacherID(teacherID);
        courseDTO.setTerm(courseTerm);
        courseDTO.setCourseName(courseName);

        connection.close();
        statement.close();
        resultSet.close();
        return courseDTO;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        cID = Integer.parseInt(request.getParameter("cID"));
        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();
        try {

            CourseDTO courseDTO = setupCourseDTO(cID);
            CourseBean courseBean = new CourseBean();
            courseBean.setCourseDTO(courseDTO);
            request.setAttribute("courseBean", courseBean);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }
        
        String nextJSP = "/traditionalHomePage.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
	    
	}



}
