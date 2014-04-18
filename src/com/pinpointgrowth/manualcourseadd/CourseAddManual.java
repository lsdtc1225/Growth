package com.pinpointgrowth.manualcourseadd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.beans.AddCourseManualBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.excel.StudentDataRecord;

public class CourseAddManual extends HttpServlet {

    private static final long serialVersionUID = -1226906935688817302L;

    private LoginBean loginBean;
    private String userName;
    private int cID;
    private float performanceWeight;
    private float traditionalWeight;
    private List<StudentDataRecord> studentList;
    private AddCourseManualBean addCourseManualBean;

    private int getTeacherID() throws ClassNotFoundException, SQLException {
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

    private void saveStudentList() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement courseIDStatement = connection.createStatement();
        Statement statement = connection.createStatement();

        int t_id = getTeacherID();
        statement.executeUpdate(Constants.COURSE_ADD_SQL(t_id, 
            addCourseManualBean.getCourseTerm().replace("" + '"', "").replace(";", ""), 
            addCourseManualBean.getCourseName().replace("" + '"', "").replace(";", ""), 
            addCourseManualBean.getCourseRoom().replace("" + '"', "").replace(";", "")));
        ResultSet courseIDResult = courseIDStatement.executeQuery(Constants.GET_COURSE_ID_SQL(t_id, 
            addCourseManualBean.getCourseTerm().replace("" + '"', "").replace(";", ""), 
            addCourseManualBean.getCourseName().replace("" + '"', "").replace(";", ""),
            addCourseManualBean.getCourseRoom().replace("" + '"', "").replace(";", "")));
        courseIDResult.last();
        //int coulmnForCID = courseIDResult.findColumn("C_ID");
        int courseID = courseIDResult.getInt(courseIDResult.findColumn("C_ID"));
        cID = courseID;

        for (StudentDataRecord record : studentList) {
            Statement addStudentStatement = connection.createStatement();
            addStudentStatement.executeUpdate(Constants.STUDENT_ADD_SQL(record.getStudentFirstName(), 
                record.getStudentLastName(),
                record.getStudentGrade()));
            Statement studentIDStatement = connection.createStatement();
            ResultSet resultSet = studentIDStatement.executeQuery(Constants.GET_STUDENT_ID_SQL(record.getStudentFirstName(),
                record.getStudentLastName(),
                record.getStudentGrade()));
            resultSet.last();
            //int studentIDColumn = resultSet.findColumn("S_ID");
            int sID = resultSet.getInt(resultSet.findColumn("S_ID"));
            Statement addTakingStatement = connection.createStatement();
            addTakingStatement.executeUpdate(Constants.TAKING_ADD_SQL(sID, courseID));
        }
    }

    private void saveWeight() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Pinpoint.Weight VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, cID);
        preparedStatement.setFloat(2, performanceWeight);
        preparedStatement.setFloat(3, traditionalWeight);
        preparedStatement.setNull(4, Types.NULL);
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();

        performanceWeight = Float.parseFloat(request.getParameter("performanceWeight"));
        traditionalWeight = Float.parseFloat(request.getParameter("traditionalWeight"));

        studentList = new ArrayList<StudentDataRecord>();
        addCourseManualBean = (AddCourseManualBean) request.getSession().getAttribute("sessionAddCourseManualBean");

        int numberOfStudents = Integer.parseInt(addCourseManualBean.getNumberOfStudents());
        for (int i = 1; i <= numberOfStudents; i++) {
            StudentDataRecord record = new StudentDataRecord();
            record.setStudentFirstName(request.getParameter("studentFirstName_" + i).replace("" + '"', "").replace(";", ""));
            record.setStudentLastName(request.getParameter("studentLastName_" + i).replace("" + '"', "").replace(";", ""));
            try {
                record.setStudentGrade(Integer.parseInt(request.getParameter("studentGrade_" + i).replace("" + '"', "").replace(";", "")));
            } catch (NumberFormatException e) {
                record.setStudentGrade(0);
            }
            studentList.add(record);
        }

        try {
            saveStudentList();
            saveWeight();
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/manualSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
