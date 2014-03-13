package com.pinpointgrowth.lockScores;

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

public class LockScores extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1474361069225118297L;
    private int courseID;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());
        try {
            setScoresLocked();
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }
        String nextJSP = "/mainUserPage.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void setScoresLocked() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.executeUpdate(Constants.Lock_SCORES(courseID));
        statement.close();
        con.close();
    }

}
