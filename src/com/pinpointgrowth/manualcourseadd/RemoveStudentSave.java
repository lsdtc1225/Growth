package com.pinpointgrowth.manualcourseadd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.constants.Constants;

public class RemoveStudentSave extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -4312833008929173310L;
    private int studentID;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        studentID = Integer.parseInt(request.getParameter("sID").trim());
        try {
            removeFromEnrolled();
            removeFromStudObjLookup();
            removeFromStudent();
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/studentRemoveSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void removeFromStudent() throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.execute(Constants.REMOVE_STUDENT_FROM_STUDENT(studentID));

        statement.close();
        con.close();

    }

    private void removeFromEnrolled() throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.execute(Constants.REMOVE_STUDENT_FROM_ENROLLED(studentID));

        statement.close();
        con.close();
    }

    private void removeFromStudObjLookup() throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.execute(Constants
                .REMOVE_STUDENT_FROM_STUDOBJLOOKUP(studentID));

        statement.close();
        con.close();
    }
}
