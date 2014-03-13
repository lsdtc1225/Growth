package com.pinpointgrowth.extension;

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

import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.constants.Constants;

public class ExtensionMetSave extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8035897138691534707L;
    private int courseID;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());

        try {
            List<StudentDTO> studentList = getStudentList();
            studentList = removeLowerTiers(studentList);
            saveExtensionMet(studentList, request);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }
        String nextJSP = "/extensionSavedSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void saveExtensionMet(List<StudentDTO> studentList,
            HttpServletRequest request) throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (StudentDTO student : studentList) {
            Statement statement = con.createStatement();
            if (request.getParameter("student_" + student.getStudentID()) != null) {
                statement.executeUpdate(Constants.UPDATE_EXTENSION_MET_TRUE(
                        student.getStudentID(), courseID));
            } else {
                statement.executeUpdate(Constants.UPDATE_EXTENSION_MET_FALSE(
                        student.getStudentID(), courseID));
            }
        }
    }

    private List<StudentDTO> getStudentList() throws ClassNotFoundException,
            SQLException {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet students = statement.executeQuery(Constants
                .GET_STUDENTS_FOR_COURSE(courseID));
        while (students.next()) {
            int studentID = students.getInt(students.findColumn("S_ID"));
            Statement statement2 = con.createStatement();
            ResultSet studentInfo = statement2.executeQuery(Constants
                    .GET_STUDENT(studentID));
            studentInfo.first();
            String firstName = studentInfo.getString(studentInfo
                    .findColumn("SFName"));
            String lastName = studentInfo.getString(studentInfo
                    .findColumn("SLName"));
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setStudentID(studentID);
            returnList.add(studentDTO);
        }
        return returnList;
    }

    private List<StudentDTO> removeLowerTiers(List<StudentDTO> studentList)
            throws ClassNotFoundException, SQLException {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (StudentDTO student : studentList) {
            Statement statement = con.createStatement();
            int studentID = student.getStudentID();
            ResultSet enrolled = statement.executeQuery(Constants.GET_ENROLLED(
                    studentID, courseID));
            enrolled.first();
            int tier = enrolled.getInt(enrolled.findColumn("Tier"));
            if (tier == 1) {
                returnList.add(student);
            }
        }
        return returnList;
    }
}
