package com.pinpointgrowth.manualcourseadd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    /**
     * 
     */
    private static final long serialVersionUID = -1226906935688817302L;
    private List<StudentDataRecord> studentList = new ArrayList<StudentDataRecord>();
    private AddCourseManualBean addCourseManualBean;
    private String userName;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();

        addCourseManualBean = (AddCourseManualBean) request.getSession()
                .getAttribute("sessionAddCourseManualBean");
        for (int i = 1; i < ((Integer.parseInt(addCourseManualBean
                .getNumberOfStudents()) + 1)); i++) {
            StudentDataRecord record = new StudentDataRecord();
            record.setStudentFirstName(request
                    .getParameter("studentFirstName_" + i)
                    .replace("" + '"', "").replace(";", ""));
            record.setStudentLastName(request
                    .getParameter("studentLastName_" + i).replace("" + '"', "")
                    .replace(";", ""));
            try {
                record.setStudentGrade(Integer.parseInt(request
                        .getParameter("studentGrade_" + i)
                        .replace("" + '"', "").replace(";", "")));
            } catch (NumberFormatException e) {
                record.setStudentGrade(0);
            }
            studentList.add(record);
        }

        try {
            saveStudentList();

        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/manualSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void saveStudentList() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement courseIDStatement = con.createStatement();
        Statement statement = con.createStatement();

        int t_id = getTeacherID(statement);
        statement.executeUpdate(Constants.COURSE_ADD_SQL(t_id,
                addCourseManualBean.getCourseTerm().replace("" + '"', "")
                        .replace(";", ""), addCourseManualBean.getCourseName()
                        .replace("" + '"', "").replace(";", ""),
                addCourseManualBean.getCourseRoom().replace("" + '"', "")
                        .replace(";", "")));
        ResultSet courseIDResult = courseIDStatement.executeQuery(Constants
                .GET_COURSE_ID_SQL(
                        t_id,
                        addCourseManualBean.getCourseTerm()
                                .replace("" + '"', "").replace(";", ""),
                        addCourseManualBean.getCourseName()
                                .replace("" + '"', "").replace(";", ""),
                        addCourseManualBean.getCourseRoom()
                                .replace("" + '"', "").replace(";", "")));
        courseIDResult.last();
        int coulmnForCID = courseIDResult.findColumn("C_ID");
        int courseID = courseIDResult.getInt(coulmnForCID);

        for (StudentDataRecord record : studentList) {
            Statement addStudentStatement = con.createStatement();
            addStudentStatement.executeUpdate(Constants.STUDENT_ADD_SQL(
                    record.getStudentFirstName(), record.getStudentLastName(),
                    record.getStudentGrade()));
            Statement studentIDStatement = con.createStatement();
            ResultSet result = studentIDStatement.executeQuery(Constants
                    .GET_STUDENT_ID_SQL(record.getStudentFirstName(),
                            record.getStudentLastName(),
                            record.getStudentGrade()));
            result.last();
            int studentIDColumn = result.findColumn("S_ID");
            int studentID = result.getInt(studentIDColumn);
            Statement addTakingStatement = con.createStatement();
            addTakingStatement.executeUpdate(Constants.TAKING_ADD_SQL(
                    studentID, courseID));
        }

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
