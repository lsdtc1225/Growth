package com.pinpointgrowth.scoreinput;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.beans.StudentDisplayBean;
import com.pinpointgrowth.constants.Constants;

public class StudentEvaluation extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7903510137335782711L;
    private int assignmentID;
    private int courseID;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        assignmentID = Integer.parseInt(request.getParameter("assignmentID")
                .trim());
        courseID = Integer.parseInt(request.getParameter("courseID").trim());

        try {
            List<StudentDTO> studentList = getStudentList();
            StudentDisplayBean studentDisplayBean = new StudentDisplayBean();
            studentDisplayBean.setStudentList(studentList);
            request.setAttribute("studentDisplayBean", studentDisplayBean);
            request.setAttribute("assignmentID", assignmentID);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/studentList.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

    private List<StudentDTO> getStudentList() throws ClassNotFoundException,
            SQLException {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        Statement statement2 = con.createStatement();
        ResultSet studentsForCourse = statement.executeQuery(Constants
                .GET_STUDENTS_FOR_COURSE(courseID));

        while (studentsForCourse.next()) {
            int studentID = studentsForCourse.getInt(studentsForCourse
                    .findColumn("S_ID"));
            ResultSet student = statement2.executeQuery(Constants
                    .GET_STUDENT(studentID));
            student.first();

            String firstName = student.getString(student.findColumn("SFName"));
            String lastName = student.getString(student.findColumn("SLName"));
            int gradeLevel = student.getInt(student.findColumn("GradeLevel"));
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setGradeLevel(gradeLevel);
            studentDTO.setStudentID(studentID);
            Statement myStatement = con.createStatement();
            ResultSet perfScores = myStatement.executeQuery(Constants
                    .GET_STUDENT_FROM_STUOBJLOOKUP(studentID,
                            getObjectiveIDForCourse()));
            if (perfScores.first()) {
                studentDTO.setPerformanceScoresEntered(true);
            } else {
                studentDTO.setPerformanceScoresEntered(false);
            }
            returnList.add(studentDTO);
        }
        statement.close();
        con.close();
        returnList = sortByLastName(returnList);
        return returnList;
    }

    private Integer getObjectiveIDForCourse() throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery(Constants
                .GET_ASSIGNMENT(assignmentID));
        results.first();
        int rubricID = results.getInt(results.findColumn("R_ID"));
        ResultSet rubric = statement.executeQuery(Constants
                .GET_OBJECTIVES_FOR_RUBRIC(rubricID));
        rubric.first();
        int returnInt = rubric.getInt(rubric.findColumn("O_ID"));

        statement.close();
        con.close();
        return returnInt;
    }

    private List<StudentDTO> sortByLastName(List<StudentDTO> studentList) {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        ArrayList<String> tempList = new ArrayList<String>();
        for (StudentDTO studentDTO : studentList) {
            if (!tempList.contains(studentDTO.getLastName())) {
                tempList.add(studentDTO.getLastName());
            }
        }
        Collections.sort(tempList);
        for (String lastName : tempList) {
            ArrayList<String> firstNameList = new ArrayList<String>();
            for (StudentDTO studentDTO : studentList) {
                if (studentDTO.getLastName().equals(lastName)) {
                    firstNameList.add(studentDTO.getFirstName());
                }
            }
            Collections.sort(firstNameList);
            for (String firstName : firstNameList) {
                for (StudentDTO studentDTO : studentList) {
                    if (((studentDTO.getLastName().equals(lastName)))
                            && (studentDTO.getFirstName().equals(firstName))) {
                        returnList.add(studentDTO);
                    }
                }
            }
        }
        return returnList;
    }
}
