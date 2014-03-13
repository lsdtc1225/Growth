package com.pinpointgrowth.tier;

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

public class GrowthTargets extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6132655130271169523L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        int numberOfStudents = Integer.parseInt(request.getParameter(
                "numberOfStudents").trim());
        int courseID = Integer.parseInt(request.getParameter("cID").trim());
        for (int i = 0; i < numberOfStudents; i++) {
            int studentID = Integer.parseInt(request.getParameter("sID_" + i)
                    .trim());
            Double target = Double.parseDouble(request.getParameter(
                    "targetForStudent_" + i).trim());
            try {
                saveTarget(studentID, target, courseID);
            } catch (Exception e) {
                ExceptionUtils.printRootCauseStackTrace(e);
            }

        }

        String nextJSP = "/lockScores.jsp?cID=" + courseID;
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void saveTarget(int studentID, Double target, int courseID)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        statement.executeUpdate(Constants.SAVE_GROWTH_TARGET(studentID,
                courseID, target));

        statement.close();
        con.close();

    }
}
