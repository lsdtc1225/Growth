package com.pinpointgrowth.prepareFrontEnd;

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

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.DTO.RubricDTO;
import com.pinpointgrowth.beans.CourseListRubricListBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;

public class PrepareCourseRubricListBean extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6211184485208709284L;
    private String userName;
    private CourseListRubricListBean courseListRubricListBean;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseListRubricListBean = new CourseListRubricListBean();

        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();

        try {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            int teacherID = getTeacherID(statement);
            setupCourseList(statement, teacherID);
            setupRubricList(statement, teacherID);
            statement.close();
            con.close();
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        request.setAttribute("courseListRubricListBean",
                courseListRubricListBean);

        String nextJSP = "/addRubricToCourse.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void setupRubricList(Statement statement, int teacherID)
            throws SQLException {
        List<RubricDTO> rubricList = new ArrayList<RubricDTO>();
        ArrayList<Integer> rubricIDList = new ArrayList<Integer>();
        ResultSet rubricIDResultSet = statement.executeQuery(Constants
                .GET_RUBRIC_IDS_FOR_TEACHER(teacherID));
        while (rubricIDResultSet.next()) {
            rubricIDList.add(rubricIDResultSet.getInt(rubricIDResultSet
                    .findColumn("R_ID")));
        }
        for (Integer id : rubricIDList) {
            ResultSet rubricForId = statement.executeQuery(Constants
                    .GET_RUBRIC(id));
            if (rubricForId.first()) {
                RubricDTO rubric = new RubricDTO();
                rubric.setRubricID(id);
                rubric.setRubricName(rubricForId.getString(rubricForId
                        .findColumn("RName")));
                rubricList.add(rubric);
            }
        }
        courseListRubricListBean.setRubricList(rubricList);

    }

    private void setupCourseList(Statement statement, int teacherID)
            throws SQLException {
        List<CourseDTO> courseList = new ArrayList<CourseDTO>();
        ResultSet courseSet = statement.executeQuery(Constants
                .GET_COURSES_FOR_TEACHER(teacherID));
        if (courseSet.first()) {
            CourseDTO firstCourse = new CourseDTO();
            firstCourse.setTeacherID(teacherID);
            firstCourse.setCourseID(courseSet.getInt(courseSet
                    .findColumn("C_ID")));
            firstCourse.setCourseLength(courseSet.getString(courseSet
                    .findColumn("CourseLength")));
            firstCourse.setCourseName(courseSet.getString(courseSet
                    .findColumn("CName")));
            firstCourse.setTerm(courseSet.getString(courseSet
                    .findColumn("Term")));
            courseList.add(firstCourse);
            while (courseSet.next() && (!courseSet.isClosed())) {
                CourseDTO course = new CourseDTO();
                course.setTeacherID(teacherID);
                course.setCourseID(courseSet.getInt(courseSet
                        .findColumn("C_ID")));
                course.setCourseLength(courseSet.getString(courseSet
                        .findColumn("CourseLength")));
                course.setCourseName(courseSet.getString(courseSet
                        .findColumn("CName")));
                course.setTerm(courseSet.getString(courseSet.findColumn("Term")));
                courseList.add(course);
                if (courseSet.isClosed()) {
                    break;
                }
            }
        }
        courseListRubricListBean.setCourseList(courseList);
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
