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

import com.pinpointgrowth.DTO.ObjectiveDTO;
import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.beans.ObjectiveDisplayBean;
import com.pinpointgrowth.beans.StudentBean;
import com.pinpointgrowth.constants.Constants;

public class ScoreInput extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -5517168168931586453L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int assignmentID = Integer.parseInt(request.getParameter("aID").trim());
        int studentID = Integer.parseInt(request.getParameter("sID").trim());

        try {
            StudentBean studentBean = getStudentBean(studentID);
            ObjectiveDisplayBean objectiveDisplayBean = getObjectiveDisplayBean(
                    assignmentID, studentID);
            request.setAttribute("studentBean", studentBean);
            request.setAttribute("objectiveDisplayBean", objectiveDisplayBean);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/scoreInput.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);

    }

    private ObjectiveDisplayBean getObjectiveDisplayBean(int assignmentID,
            int studentID) throws ClassNotFoundException, SQLException {
        ObjectiveDisplayBean returnBean = new ObjectiveDisplayBean();
        List<ObjectiveDTO> objectiveList = new ArrayList<ObjectiveDTO>();

        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet rIDResult = statement.executeQuery(Constants
                .GET_ASSIGNMENT(assignmentID));
        rIDResult.first();
        int rubricID = rIDResult.getInt(rIDResult.findColumn("R_ID"));
        ResultSet oIDResult = statement.executeQuery(Constants
                .GET_OBJECTIVES_FOR_RUBRIC(rubricID));
        while (oIDResult.next()) {
            Statement statement3 = con.createStatement();
            int objectiveID = oIDResult.getInt(oIDResult.findColumn("O_ID"));
            ResultSet objective = statement3.executeQuery(Constants
                    .GET_OBJECTIVE(objectiveID));
            objective.first();
            ObjectiveDTO objectiveDTO = new ObjectiveDTO();
            String description = objective.getString(objective
                    .findColumn("Description"));
            int maxScore = objective.getInt(objective.findColumn("MaxScore"));
            int minScore = objective.getInt(objective.findColumn("MinScore"));
            String objectiveName = objective.getString(objective
                    .findColumn("OName"));
            int weight = objective.getInt(objective.findColumn("Weight"));
            objectiveDTO.setDescription(description);
            objectiveDTO.setMaxScore(maxScore);
            objectiveDTO.setMinScore(minScore);
            objectiveDTO.setObjectiveID(objectiveID);
            objectiveDTO.setObjectiveName(objectiveName);
            objectiveDTO.setWeight(weight);
            Statement myStatement = con.createStatement();
            ResultSet stuObjLookup = myStatement.executeQuery(Constants
                    .GET_STUDENT_FROM_STUOBJLOOKUP(studentID, objectiveID));
            if (stuObjLookup.first()) {
                Statement myStatement2 = con.createStatement();
                ResultSet scoreResults = myStatement2.executeQuery(Constants
                        .GET_STUOBJLOOKUP(studentID, objectiveID));
                scoreResults.first();
                int score = scoreResults.getInt(scoreResults
                        .findColumn("PreGradePerf"));
                objectiveDTO.setPerformanceScore(score);
            }
            objectiveList.add(objectiveDTO);
        }

        returnBean.setObjectiveList(objectiveList);
        statement.close();
        con.close();
        return returnBean;
    }

    private StudentBean getStudentBean(int studentID)
            throws ClassNotFoundException, SQLException {
        StudentBean studentBean = new StudentBean();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery(Constants
                .GET_STUDENT(studentID));
        results.first();
        String firstName = results.getString(results.findColumn("SFName"));
        String lastName = results.getString(results.findColumn("SLName"));
        int gradeLevel = results.getInt(results.findColumn("GradeLevel"));
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(firstName);
        studentDTO.setLastName(lastName);
        studentDTO.setStudentID(studentID);
        studentDTO.setGradeLevel(gradeLevel);
        studentBean.setStudentDTO(studentDTO);
        return studentBean;
    }

}
