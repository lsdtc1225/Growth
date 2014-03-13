package com.pinpointgrowth.scoreinput;

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

import com.pinpointgrowth.constants.Constants;

public class SaveScores extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 32782770278150011L;
    private int assignmentID;
    private int studentID;
    private int courseID;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        assignmentID = Integer.parseInt(request.getParameter("aID").trim());
        studentID = Integer.parseInt(request.getParameter("sID").trim());

        try {
            List<Integer> objectiveIDs = getObjectiveIDList();
            saveObjectiveScores(objectiveIDs, request);
            request.setAttribute("assignmentID", assignmentID);
            request.setAttribute("courseID", courseID);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/StudentEvaluation?assignmentID=" + assignmentID
                + "&courseID=" + courseID;
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

    private void saveObjectiveScores(List<Integer> objectiveIDs,
            HttpServletRequest request) throws SQLException,
            ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        for (Integer oID : objectiveIDs) {
            int score = Integer.parseInt(request.getParameter("" + oID).trim());
            Statement myStatement = con.createStatement();
            ResultSet recordExists = myStatement.executeQuery(Constants
                    .GET_STUDENT_FROM_STUOBJLOOKUP(studentID, oID));
            if (recordExists.first()) {
                statement.executeUpdate(Constants.UPDATE_PERFORMANCE_SCORE(
                        studentID, oID, score));
            } else {
                statement.execute(Constants.SAVE_PERFORMANCE_SCORES(studentID,
                        oID, score));
            }
        }

    }

    private List<Integer> getObjectiveIDList() throws ClassNotFoundException,
            SQLException {
        List<Integer> returnList = new ArrayList<Integer>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet rubricID = statement.executeQuery(Constants
                .GET_ASSIGNMENT(assignmentID));
        rubricID.first();
        int rubricIDInt = rubricID.getInt(rubricID.findColumn("R_ID"));
        courseID = rubricID.getInt(rubricID.findColumn("C_ID"));
        ResultSet objectiveIDs = statement.executeQuery(Constants
                .GET_OBJECTIVES_FOR_RUBRIC(rubricIDInt));
        while (objectiveIDs.next()) {
            int objectiveID = objectiveIDs.getInt(objectiveIDs
                    .findColumn("O_ID"));
            returnList.add(objectiveID);
        }
        statement.close();
        con.close();
        return returnList;
    }
}
