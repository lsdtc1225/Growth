package com.pinpointgrowth.tier;

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
import com.pinpointgrowth.beans.TierBean;
import com.pinpointgrowth.constants.Constants;

public class Tier extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1219881735747057280L;
    private boolean tier;
    private boolean update;
    private List<Double> defualtGrowthTargets;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        tier = Boolean.parseBoolean(request.getParameter("tier").trim());
        update = Boolean.parseBoolean(request.getParameter("update").trim());

        defualtGrowthTargets = new ArrayList<Double>();
        defualtGrowthTargets.add(.05);
        defualtGrowthTargets.add(.1);
        defualtGrowthTargets.add(.2);
        defualtGrowthTargets.add(.3);
        defualtGrowthTargets.add(.4);
        defualtGrowthTargets.add(.5);
        defualtGrowthTargets.add(.75);

        int courseID = Integer.parseInt(request.getParameter("cID").trim());
        if (!allAssignmentsComplete(courseID)) {
            String nextJSP = "/completeAllAssignments.jsp?cID=" + courseID;
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(nextJSP);
            dispatcher.forward(request, response);
        } else {
            if (tier) {
                int numberOfTiers = Integer.parseInt(request
                        .getParameter("numberOfTiers"));
                try {
                    List<StudentDTO> studentList = getStudentsForCourse(courseID);
                    List<List<StudentDTO>> tieredLists;
                    TierBean tierBean = new TierBean();
                    if (!update) {
                        tieredLists = setupTierLists(numberOfTiers,
                                studentList, courseID);
                        for (int i = 0; i < numberOfTiers; i++) {
                            double percent = defualtGrowthTargets.get(i)
                                    * getMaxPreScore(courseID);
                            tierBean.addPercent(percent);
                        }

                    } else {
                        tieredLists = updateTierLists(numberOfTiers,
                                studentList, courseID, request);

                        for (int i = 0; i < numberOfTiers; i++) {
                            Double percent = Double.parseDouble(request
                                    .getParameter("target_" + i).trim());
                            tierBean.addPercent(percent);
                        }

                    }
                    tierBean.setTiers(tieredLists);
                    request.setAttribute("tierBean", tierBean);

                } catch (Exception e) {
                    ExceptionUtils.printRootCauseStackTrace(e);
                }

                String nextJSP = "/tiers.jsp";
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);

            } else {
                try {
                    List<StudentDTO> studentList = getStudentsForCourse(courseID);
                    List<Integer> oIDList = getObjectiveIDs(courseID);
                    studentList = sortByScore(studentList, oIDList);
                    GrowthTargetBean growthTargetBean = new GrowthTargetBean();
                    for (StudentDTO student : studentList) {
                        student.setGrowthTarget(0.0);
                    }
                    growthTargetBean.setTargets(studentList);
                    request.setAttribute("growthTargetBean", growthTargetBean);
                } catch (Exception e) {
                    ExceptionUtils.printRootCauseStackTrace(e);
                }
                String nextJSP = "/individualGrowthTargets.jsp?cID=" + courseID;
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
            }
        }
    }

    private boolean allAssignmentsComplete(int courseID) {
        try {
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet assignments = statement.executeQuery(Constants
                    .GET_ALL_ASSIGNMENTS_FOR_COURSE(courseID));
            while (assignments.next()) {
                int rubricID = assignments.getInt(assignments
                        .findColumn("R_ID"));
                Statement statement2 = con.createStatement();
                ResultSet objectiveIDs = statement2.executeQuery(Constants
                        .GET_OBJECTIVES_FOR_RUBRIC(rubricID));
                while (objectiveIDs.next()) {
                    int objectiveID = objectiveIDs.getInt(objectiveIDs
                            .findColumn("O_ID"));
                    Statement statement3 = con.createStatement();
                    ResultSet students = statement3.executeQuery(Constants
                            .GET_STUDOBJLOOKUP_FOR_OID(objectiveID));
                    while (students.next()) {
                        try {
                            Integer preGradePerf = students.getInt(students
                                    .findColumn("PreGradePerf"));
                            if (preGradePerf == null) {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    if (!students.first()) {
                        return false;
                    }
                    Statement statement4 = con.createStatement();
                    ResultSet studentIDs = statement4.executeQuery(Constants
                            .GET_STUDENTS_FOR_COURSE(courseID));
                    while (studentIDs.next()) {
                        int studentID = studentIDs.getInt(studentIDs
                                .findColumn("S_ID"));
                        Statement statement5 = con.createStatement();
                        ResultSet studObjLookup = statement5
                                .executeQuery(Constants.GET_STUOBJLOOKUP(
                                        studentID, objectiveID));
                        if (!studObjLookup.first()) {
                            return false;
                        }
                    }
                }
            }

            if (!assignments.first()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        return false;
    }

    private List<List<StudentDTO>> updateTierLists(int numberOfTiers,
            List<StudentDTO> studentList, int courseID,
            HttpServletRequest request) throws ClassNotFoundException,
            SQLException {
        List<List<StudentDTO>> returnList = new ArrayList<List<StudentDTO>>();
        List<Double> percentList = new ArrayList<Double>();
        for (int i = 0; i < numberOfTiers; i++) {
            double percent = Double.parseDouble(request.getParameter("" + i)
                    .replace("%", "").trim());
            Double percent2 = (double) percent / (double) 100.0;
            percentList.add(percent2);
        }

        List<Integer> oIDList = getObjectiveIDs(courseID);
        studentList = sortByScore(studentList, oIDList);
        int studentsCounted = 0;
        List<Integer> studentsForEachTier = new ArrayList<Integer>();
        for (int i = 0; i < numberOfTiers; i++) {
            Double percent = percentList.get(i);
            int studentsForThisTier = (int) (studentList.size() * percent);
            studentsForEachTier.add(studentsForThisTier);
            studentsCounted += studentsForThisTier;
        }
        int studentsRemaining = studentList.size() - studentsCounted;
        for (int i = (studentsForEachTier.size() - 1); i >= 0; i--) {
            List<StudentDTO> students = new ArrayList<StudentDTO>();
            int studentsForThisTier = studentsForEachTier.get(i);
            for (int j = 0; j < studentsForThisTier; j++) {
                students.add(studentList.get(studentList.size() - 1));
                studentList.remove(studentList.size() - 1);
            }
            if (studentsRemaining > 0) {
                students.add(studentList.get(studentList.size() - 1));
                studentList.remove(studentList.size() - 1);
                studentsRemaining--;
            }
            Collections.reverse(students);
            returnList.add(students);
        }
        Collections.reverse(returnList);
        return returnList;
    }

    private List<List<StudentDTO>> setupTierLists(int numberOfTiers,
            List<StudentDTO> studentList, int courseID)
            throws ClassNotFoundException, SQLException {
        List<List<StudentDTO>> returnList = new ArrayList<List<StudentDTO>>();
        int numberOfStudents = studentList.size();
        List<Integer> oIDList = getObjectiveIDs(courseID);
        studentList = sortByScore(studentList, oIDList);
        int studentsPerTier = numberOfStudents / numberOfTiers;
        int remainingStudents = numberOfStudents
                - (numberOfTiers * studentsPerTier);

        for (int i = 0; i < numberOfTiers; i++) {
            List<StudentDTO> tier = new ArrayList<StudentDTO>();
            for (int j = 0; j < studentsPerTier; j++) {
                tier.add(studentList.get(studentList.size() - 1));
                studentList.remove(studentList.size() - 1);
            }
            if (remainingStudents > 0) {
                tier.add(studentList.get(studentList.size() - 1));
                studentList.remove(studentList.size() - 1);
                remainingStudents--;
            }
            Collections.reverse(tier);
            returnList.add(tier);
        }
        Collections.reverse(returnList);
        return returnList;
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

    private List<StudentDTO> getStudentsForCourse(int courseID)
            throws ClassNotFoundException, SQLException {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        ResultSet studentIDs = statement.executeQuery(Constants
                .GET_STUDENTS_FOR_COURSE(courseID));
        while (studentIDs.next()) {
            int studentID = studentIDs.getInt(studentIDs.findColumn("S_ID"));
            Statement statement2 = con.createStatement();
            ResultSet studentResult = statement2.executeQuery(Constants
                    .GET_STUDENT(studentID));
            studentResult.first();
            StudentDTO studentDTO = new StudentDTO();
            String firstName = studentResult.getString(studentResult
                    .findColumn("SFName"));
            String lastName = studentResult.getString(studentResult
                    .findColumn("SLName"));
            int gradeLevel = studentResult.getInt(studentResult
                    .findColumn("GradeLevel"));
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setGradeLevel(gradeLevel);
            studentDTO.setStudentID(studentID);
            returnList.add(studentDTO);
        }

        statement.close();
        con.close();

        return returnList;
    }

    private double getMaxPreScore(int courseID) throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        List<Integer> oIDList = getObjectiveIDs(courseID, "N");
        int sumOfScores = 0;
        for (Integer oID : oIDList) {
            Statement statement = con.createStatement();
            ResultSet objective = statement.executeQuery(Constants
                    .GET_OBJECTIVE(oID));
            objective.first();
            int weight = objective.getInt(objective.findColumn("Weight"));
            int scoreValue = objective.getInt(objective.findColumn("MaxScore"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        con.close();
        return score;
    }

    private List<Integer> getObjectiveIDs(int courseID, String preOrPost)
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
            // only looking for pre or post
            if (postScore.equals(preOrPost)) {
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
}
