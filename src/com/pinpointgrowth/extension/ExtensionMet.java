package com.pinpointgrowth.extension;

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

public class ExtensionMet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1227241573344951459L;
    private int courseID;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());
        try {
            if (courseHasExtensionActivity()) {
                StudentDisplayBean studentDisplayBean = new StudentDisplayBean();
                List<StudentDTO> studentList = getStudentList();
                studentList = removeLowerTiers(studentList);
                studentList = sortByLastName(studentList);
                studentDisplayBean.setStudentList(studentList);
                request.setAttribute("studentBean", studentDisplayBean);
                String extension = getExtension();
                request.setAttribute("extension", extension);
                String nextJSP = "/evaluateExtension.jsp?cID=" + courseID;
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
            } else {
                // forward to no extension page
                String nextJSP = "/noExtension.jsp?cID=" + courseID;
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

    }

    private String getExtension() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet extension = statement.executeQuery(Constants
                .GET_EXTENSION_ACTIVITY_FOR_COURSE(courseID));
        extension.first();
        return extension
                .getString(extension.findColumn("ExtensionDescription"));
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

    private boolean courseHasExtensionActivity() throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet extension = statement.executeQuery(Constants
                .GET_EXTENSION_ACTIVITY_FOR_COURSE(courseID));
        return extension.first();
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
