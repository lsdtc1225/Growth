package com.pinpointgrowth.extension;

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

public class Extension extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int courseID;
    private String description;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());
        description = request.getParameter("description").replace("" + '"', "")
                .replace(";", "").trim();
        try {
            saveExtension();
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/lockScores.jsp?cID=" + courseID;
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void saveExtension() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        statement.executeUpdate(Constants.ADD_EXTENSION(courseID, description));
        statement.close();
        con.close();
    }
}
