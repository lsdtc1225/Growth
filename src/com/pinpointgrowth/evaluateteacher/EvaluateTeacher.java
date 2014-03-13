package com.pinpointgrowth.evaluateteacher;

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

import com.pinpointgrowth.DTO.FinalEvaluationDTO;
import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.beans.TeacherEvaluationBean;
import com.pinpointgrowth.constants.Constants;

public class EvaluateTeacher extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 4633436412768034436L;
    private String userName;
    private int teacherID;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();

        if (request.getParameter("cID") != null) {

            int courseID = Integer.parseInt(request.getParameter("cID").trim());
            if (oneCourseReady(courseID)) {
                try {

                    List<StudentDTO> studentList = getStudentsForCourse(courseID);

                    List<FinalEvaluationDTO> finalList = setupScores(
                            studentList, courseID);
                    List<FinalEvaluationDTO> yesList = getYesList(finalList,
                            courseID);
                    List<FinalEvaluationDTO> noList = getNoList(finalList,
                            courseID);
                    Collections.sort(yesList);
                    Collections.sort(noList);
                    TeacherEvaluationBean teacherEvaluationBean = new TeacherEvaluationBean();
                    teacherEvaluationBean.setYesList(yesList);
                    teacherEvaluationBean.setNoList(noList);
                    double percentage = (double) yesList.size()
                            / ((double) finalList.size());
                    teacherEvaluationBean.setPercentage(percentage);
                    request.setAttribute("teacherEvaluationBean",
                            teacherEvaluationBean);
                    request.setAttribute("oneCourse", true);

                } catch (Exception e) {
                    ExceptionUtils.printRootCauseStackTrace(e);
                }

                String nextJSP = "/teacherEvaluation.jsp";
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
            } else {
                // forward to not ready
                String nextJSP = "/notReadyToEvaluate.jsp";
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(nextJSP);
                dispatcher.forward(request, response);
            }
        } else {
            try {
                teacherID = getTeacherID();
                List<Integer> courseIDs = getCourseIDs(teacherID);
                if (allCoursesReady(courseIDs)) {
                    List<FinalEvaluationDTO> yesList = getAllYesList(courseIDs);
                    List<FinalEvaluationDTO> noList = getAllNoList(courseIDs);
                    Collections.sort(yesList);
                    Collections.sort(noList);
                    TeacherEvaluationBean teacherEvaluationBean = new TeacherEvaluationBean();
                    teacherEvaluationBean.setYesList(yesList);
                    teacherEvaluationBean.setNoList(noList);
                    double percentage = (double) yesList.size()
                            / ((double) getStudentsForTeacher(teacherID).size());
                    teacherEvaluationBean.setPercentage(percentage);
                    request.setAttribute("teacherEvaluationBean",
                            teacherEvaluationBean);
                    request.setAttribute("oneCourse", false);
                    String nextJSP = "/teacherEvaluation.jsp";
                    RequestDispatcher dispatcher = getServletContext()
                            .getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);
                } else {
                    // forward to not ready
                    String nextJSP = "/notReadyToEvaluate.jsp";
                    RequestDispatcher dispatcher = getServletContext()
                            .getRequestDispatcher(nextJSP);
                    dispatcher.forward(request, response);

                }
            } catch (Exception e) {
                ExceptionUtils.printRootCauseStackTrace(e);
            }

        }
    }

    private boolean allCoursesReady(List<Integer> courseIDs) {
        for (Integer courseID : courseIDs) {
            if (!oneCourseReady(courseID)) {
                return false;
            }
        }
        return true;
    }

    private boolean oneCourseReady(int courseID) {
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
                                con.close();
                                return false;
                            }
                        } catch (Exception e) {
                            con.close();
                            return false;
                        }
                    }
                    if (!students.first()) {
                        con.close();
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
                            con.close();
                            return false;
                        }
                    }
                }
            }

            if (!assignments.first()) {
                con.close();
                return false;
            }

            if (!extensionReady(courseID)) {
                con.close();
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        return false;
    }

    private boolean extensionReady(int courseID) throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet extension = statement.executeQuery(Constants
                .GET_EXTENSION_ACTIVITY_FOR_COURSE(courseID));
        extension.first();
        if (courseHasExtensionActivity(courseID)) {
            List<StudentDTO> studentList = getStudentList(courseID);
            studentList = removeLowerTiers(studentList, courseID);
            if (extensionReadyForStudents(studentList)) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } else {
            con.close();
            return true;
        }

    }

    private boolean extensionReadyForStudents(List<StudentDTO> studentList)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        for (StudentDTO student : studentList) {
            Statement statement = con.createStatement();
            ResultSet enrolled = statement.executeQuery(Constants.GET_ENROLLED(
                    student.getStudentID(), student.getCourseID()));
            enrolled.first();
            String extensionMet = enrolled.getString(enrolled
                    .findColumn("ExtensionMet"));
            if (!((extensionMet.equals("Y")) || (extensionMet.equals("N")))) {
                con.close();
                return false;
            }
        }
        con.close();
        return true;
    }

    private List<StudentDTO> removeLowerTiers(List<StudentDTO> studentList,
            int courseID) throws ClassNotFoundException, SQLException {
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
        con.close();
        return returnList;
    }

    private List<StudentDTO> getStudentList(int courseID)
            throws ClassNotFoundException, SQLException {
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
            studentDTO.setCourseID(courseID);
            returnList.add(studentDTO);
        }
        con.close();
        return returnList;
    }

    private boolean courseHasExtensionActivity(int courseID)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet extension = statement.executeQuery(Constants
                .GET_EXTENSION_ACTIVITY_FOR_COURSE(courseID));
        boolean result = extension.first();
        con.close();
        return result;
    }

    private List<FinalEvaluationDTO> getAllNoList(List<Integer> courseIDs)
            throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> returnList = new ArrayList<FinalEvaluationDTO>();
        for (Integer courseID : courseIDs) {
            List<StudentDTO> students = getStudentsForCourse(courseID);
            List<FinalEvaluationDTO> list = setupScores(students, courseID);
            list = getNoList(list, courseID);
            returnList.addAll(list);
        }
        return returnList;
    }

    private List<FinalEvaluationDTO> getAllYesList(List<Integer> courseIDs)
            throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> returnList = new ArrayList<FinalEvaluationDTO>();
        for (Integer courseID : courseIDs) {
            List<StudentDTO> students = getStudentsForCourse(courseID);
            List<FinalEvaluationDTO> list = setupScores(students, courseID);
            list = getYesList(list, courseID);
            returnList.addAll(list);
        }
        return returnList;
    }

    private List<FinalEvaluationDTO> getYesList(
            List<FinalEvaluationDTO> finalList, int courseID)
            throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> yesList = new ArrayList<FinalEvaluationDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        for (FinalEvaluationDTO finalEvaluationDTO : finalList) {
            StudentDTO studentDTO = finalEvaluationDTO.getStudentDTO();
            Statement statement = con.createStatement();
            ResultSet growthTarget = statement.executeQuery(Constants
                    .GET_ENROLLED(studentDTO.getStudentID(), courseID));
            growthTarget.first();
            double target = growthTarget.getDouble(growthTarget
                    .findColumn("GrowthTarget"));
            finalEvaluationDTO.getStudentDTO().setGrowthTarget(target);
            double maxPostScore = getMaxPostScore(courseID);
            double postScore = getPostScore(finalEvaluationDTO.getStudentDTO(),
                    courseID);
            if ((finalEvaluationDTO.getGrowth() >= target)
                    || (maxPostScore == postScore)) {
                if (finalEvaluationDTO.getTopTier()) {
                    if (finalEvaluationDTO.getExtensionMet()) {
                        yesList.add(finalEvaluationDTO);
                    }
                } else {
                    yesList.add(finalEvaluationDTO);
                }
            }
        }
        con.close();
        return yesList;
    }

    private List<FinalEvaluationDTO> getNoList(
            List<FinalEvaluationDTO> finalList, int courseID)
            throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> noList = new ArrayList<FinalEvaluationDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        for (FinalEvaluationDTO finalEvaluationDTO : finalList) {
            StudentDTO studentDTO = finalEvaluationDTO.getStudentDTO();
            Statement statement = con.createStatement();
            ResultSet growthTarget = statement.executeQuery(Constants
                    .GET_ENROLLED(studentDTO.getStudentID(), courseID));
            growthTarget.first();
            double target = growthTarget.getDouble(growthTarget
                    .findColumn("GrowthTarget"));
            finalEvaluationDTO.getStudentDTO().setGrowthTarget(target);
            double maxPostScore = getMaxPostScore(courseID);
            double postScore = getPostScore(finalEvaluationDTO.getStudentDTO(),
                    courseID);
            if ((finalEvaluationDTO.getGrowth() < target)
                    && (maxPostScore != postScore)) {

                noList.add(finalEvaluationDTO);

            } else {
                if (finalEvaluationDTO.getTopTier()) {
                    if (!finalEvaluationDTO.getExtensionMet()) {
                        noList.add(finalEvaluationDTO);
                    }
                }
            }
        }
        con.close();
        return noList;
    }

    private List<FinalEvaluationDTO> setupScores(List<StudentDTO> studentList,
            int courseID) throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> returnList = new ArrayList<FinalEvaluationDTO>();
        for (StudentDTO student : studentList) {
            FinalEvaluationDTO finalEvaluationDTO = new FinalEvaluationDTO();
            finalEvaluationDTO.setStudentDTO(student);
            boolean topTier = getTopTier(student);
            if (courseHasExtensionActivity(courseID)) {
                if (topTier) {
                    finalEvaluationDTO.setTopTier(true);
                    finalEvaluationDTO
                            .setExtensionMet(getExtensionMet(student));
                } else {
                    finalEvaluationDTO.setTopTier(false);
                }
            } else {
                finalEvaluationDTO.setTopTier(false);
            }
            double preScore = getPreScore(student, courseID);
            double postScore = getPostScore(student, courseID);
            finalEvaluationDTO.setPreScore(preScore);
            finalEvaluationDTO.setPostScore(postScore);
            double maxPreScore = getMaxPreScore(courseID);
            double maxPostScore = getMaxPostScore(courseID);
            double growth = (postScore / maxPostScore)
                    - (preScore / maxPreScore);
            finalEvaluationDTO.setGrowth(growth);
            returnList.add(finalEvaluationDTO);
        }
        return returnList;
    }

    private boolean getExtensionMet(StudentDTO student)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        int courseID = student.getCourseID();
        int studentID = student.getStudentID();
        ResultSet enrolled = statement.executeQuery(Constants.GET_ENROLLED(
                studentID, courseID));
        enrolled.first();
        String extensionMet = enrolled.getString(enrolled
                .findColumn("ExtensionMet"));
        if (extensionMet.equals("Y")) {
            con.close();
            return true;
        }

        con.close();
        return false;
    }

    private boolean getTopTier(StudentDTO student)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        int courseID = student.getCourseID();
        int studentID = student.getStudentID();
        ResultSet enrolled = statement.executeQuery(Constants.GET_ENROLLED(
                studentID, courseID));
        enrolled.first();
        int tier = enrolled.getInt(enrolled.findColumn("Tier"));
        // if teacher chose not to tier, tier will be 0 here
        if (tier == 1) {
            con.close();
            return true;
        }
        con.close();
        return false;
    }

    private double getMaxPostScore(int courseID) throws ClassNotFoundException,
            SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        List<Integer> oIDList = getObjectiveIDs(courseID, "Y");
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

    private double getPreScore(StudentDTO student, int courseID)
            throws ClassNotFoundException, SQLException {
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
            ResultSet score = statement.executeQuery(Constants
                    .GET_STUOBJLOOKUP(student.getStudentID(), oID));
            score.first();
            int scoreValue = score.getInt(score.findColumn("PreGradePerf"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        con.close();
        return score;
    }

    private double getPostScore(StudentDTO student, int courseID)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        List<Integer> oIDList = getObjectiveIDs(courseID, "Y");
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

        con.close();
        return score;
    }

    private List<StudentDTO> getStudentsForTeacher(int teacherID2)
            throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        List<Integer> courseIDs = getCourseIDs(teacherID2);
        List<Integer> studentIDs = getStudentIDs(courseIDs);
        for (Integer studentID : studentIDs) {
            Statement statement = con.createStatement();
            ResultSet student = statement.executeQuery(Constants
                    .GET_STUDENT(studentID));
            student.first();
            StudentDTO studentDTO = new StudentDTO();
            String firstName = student.getString(student.findColumn("SFName"));
            String lastName = student.getString(student.findColumn("SLName"));
            int gradeLevel = student.getInt(student.findColumn("GradeLevel"));
            studentDTO.setStudentID(studentID);
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setGradeLevel(gradeLevel);
            returnList.add(studentDTO);
        }
        con.close();
        return returnList;
    }

    private List<Integer> getStudentIDs(List<Integer> courseIDs)
            throws ClassNotFoundException, SQLException {
        List<Integer> returnList = new ArrayList<Integer>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (Integer courseID : courseIDs) {
            Statement statement = con.createStatement();
            ResultSet students = statement.executeQuery(Constants
                    .GET_STUDENTS_FOR_COURSE(courseID));
            while (students.next()) {
                int studentID = students.getInt(students.findColumn("S_ID"));
                returnList.add(studentID);
            }
        }

        con.close();
        return returnList;
    }

    private List<Integer> getCourseIDs(int teacherID2) throws SQLException,
            ClassNotFoundException {
        List<Integer> returnList = new ArrayList<Integer>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet courses = statement.executeQuery(Constants
                .GET_COURSES_FOR_TEACHER(teacherID2));
        while (courses.next()) {
            int courseID = courses.getInt(courses.findColumn("C_ID"));
            returnList.add(courseID);
        }
        statement.close();
        con.close();
        return returnList;
    }

    private int getTeacherID() throws SQLException, ClassNotFoundException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(Constants
                .TEACHER_ID_QUERY(userName));
        resultSet.first();
        int column = resultSet.findColumn("T_ID");
        int teacherID = resultSet.getInt(column);
        statement.close();
        con.close();
        return teacherID;
    }

    private List<StudentDTO> getStudentsForCourse(int courseID)
            throws ClassNotFoundException, SQLException {
        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
        Statement courseStatement = con.createStatement();
        ResultSet course = courseStatement.executeQuery(Constants
                .GET_COURSE(courseID));
        course.first();
        String courseName = course.getString(course.findColumn("CName"));

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
            studentDTO.setCourseName(courseName);
            studentDTO.setCourseID(courseID);
            returnList.add(studentDTO);
        }

        statement.close();
        con.close();

        return returnList;
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
