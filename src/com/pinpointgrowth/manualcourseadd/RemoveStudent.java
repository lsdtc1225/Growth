package com.pinpointgrowth.manualcourseadd;

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

public class RemoveStudent extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1246237068828059271L;
    private int courseID;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());
        try {
            StudentDisplayBean studentDisplayBean = new StudentDisplayBean();
            List<StudentDTO> studentList = getStudentList();
            studentList = sortByLastName(studentList);
            studentDisplayBean.setStudentList(studentList);
            request.setAttribute("studentBean", studentDisplayBean);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }
        String nextJSP = "/removeStudent.jsp?cID=" + courseID;
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
