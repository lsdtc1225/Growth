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
import com.pinpointgrowth.beans.GrowthTargetBean;
import com.pinpointgrowth.constants.Constants;

public class LateStudentAdd extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6160802842943866010L;
    private int courseID;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        courseID = Integer.parseInt(request.getParameter("cID").trim());
        String firstName = request.getParameter("firstName").trim()
                .replace("" + '"', "").replace(";", "");
        String lastName = request.getParameter("lastName").trim()
                .replace("" + '"', "").replace(";", "");
        int gradeLevel = Integer.parseInt(request.getParameter("gradeLevel")
                .trim());
        try {
            int studentID = saveStudent(firstName, lastName, gradeLevel);
            saveScores(studentID, request);
            GrowthTargetBean growthTargetBean = new GrowthTargetBean();
            List<StudentDTO> studentList = new ArrayList<StudentDTO>();
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setGrowthTarget(0.0);
            studentDTO.setStudentID(studentID);
            studentDTO.setScore(getScoreForStudent(studentDTO));
            studentList.add(studentDTO);
            growthTargetBean.setCourseID(courseID);
            growthTargetBean.setTargets(studentList);
            request.setAttribute("lateStudentScoreBean", growthTargetBean);
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        String nextJSP = "/lateStudentSuccess.jsp?cID=" + courseID;
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private double getScoreForStudent(StudentDTO studentDTO)
            throws ClassNotFoundException, SQLException {
        List<Integer> oIDList = getObjectiveIDs(courseID);
        List<StudentDTO> studentList = new ArrayList<StudentDTO>();
        studentList.add(studentDTO);
        studentList = sortByScore(studentList, oIDList);
        return studentList.get(0).getScore();
    }

    private List<StudentDTO> sortByScore(List<StudentDTO> studentList,
            List<Integer> oIDList) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (StudentDTO student : studentList) {
            int sumOfScores = 0;
            for (Integer oID : oIDList) {
                Statement statement = con.createStatement();
                ResultSet objective = statement.executeQuery(Constants
                        .GET_OBJECTIVE(oID));
                objective.first();
                int weight = objective.getInt(objective.findColumn("Weight"));
                ResultSet score = statement.executeQuery(Constants
                        .GET_STUOBJLOOKUP(student.getStudentID(), oID));
                score.first();
                int scoreValue = score.getInt(score.findColumn("PreGradePerf"));
                sumOfScores += (scoreValue * weight);
            }
            double score = (double) sumOfScores / (double) oIDList.size();
            student.setScore(score);
        }
        List<Double> tempList = new ArrayList<Double>();

        for (StudentDTO student : studentList) {
            if (!tempList.contains(student.getScore())) {
                tempList.add(student.getScore());
            }
        }
        Collections.sort(tempList);
        List<StudentDTO> newList = new ArrayList<StudentDTO>();
        for (int i = (tempList.size() - 1); i >= 0; i--) {
            List<StudentDTO> studentsWithThisScore = new ArrayList<StudentDTO>();
            for (StudentDTO student : studentList) {
                if (tempList.get(i).equals(student.getScore())) {
                    studentsWithThisScore.add(student);
                }
            }
            for (StudentDTO student : studentsWithThisScore) {
                newList.add(student);
            }
        }

        return newList;
    }

    private List<Integer> getObjectiveIDs(int courseID)
            throws ClassNotFoundException, SQLException {
        List<Integer> returnList = new ArrayList<Integer>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet rIDs = statement.executeQuery(Constants
                .GET_ALL_ASSIGNMENTS_FOR_COURSE(courseID));
        while (rIDs.next()) {
            int rID = rIDs.getInt(rIDs.findColumn("R_ID"));
            Statement statement3 = con.createStatement();
            ResultSet rubric = statement3.executeQuery(Constants
                    .GET_RUBRIC(rID));
            rubric.first();
            String postScore = rubric.getString(rubric.findColumn("PostScore"));
            // only looking for prescores
            if (postScore.equals("N")) {
                Statement statement2 = con.createStatement();
                ResultSet oIDs = statement2.executeQuery(Constants
                        .GET_OBJECTIVES_FOR_RUBRIC(rID));
                while (oIDs.next()) {
                    int oID = oIDs.getInt(oIDs.findColumn("O_ID"));
                    returnList.add(oID);
                }
            }
        }
        statement.close();
        con.close();
        return returnList;
    }

    private void saveScores(int studentID, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {

        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet assignments = statement.executeQuery(Constants
                .GET_ALL_ASSIGNMENTS_FOR_COURSE(courseID));
        while (assignments.next()) {

            int rubricID = assignments.getInt(assignments.findColumn("R_ID"));

            Statement statement2 = con.createStatement();
            ResultSet objectiveIDs = statement2.executeQuery(Constants
                    .GET_OBJECTIVES_FOR_RUBRIC(rubricID));

            while (objectiveIDs.next()) {

                int objectiveID = objectiveIDs.getInt(objectiveIDs
                        .findColumn("O_ID"));
                Statement statement3 = con.createStatement();
                int score = Integer.parseInt(request.getParameter(
                        "" + objectiveID).trim());
                statement3.executeUpdate(Constants.SAVE_PERFORMANCE_SCORES(
                        studentID, objectiveID, score));
            }
        }
    }

    private int saveStudent(String firstName, String lastName, int gradeLevel)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        Statement studentIDStatement = con.createStatement();
        statement.executeUpdate(Constants.STUDENT_ADD_SQL(firstName, lastName,
                gradeLevel));
        ResultSet result = studentIDStatement.executeQuery(Constants
                .GET_STUDENT_ID_SQL(firstName, lastName, gradeLevel));
        result.last();
        int studentID = result.getInt(result.findColumn("S_ID"));
        statement.executeUpdate(Constants.TAKING_ADD_SQL(studentID, courseID));

        return studentID;
    }

}
