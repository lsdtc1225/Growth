package com.pinpointgrowth.traditionalServlet;

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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;
import com.pinpointgrowth.traditionalDTO.EvaluationDTO;
import com.pinpointgrowth.traditionalBeans.PreTestSetupBean;
import com.pinpointgrowth.traditionalBeans.StudentEvaluationBean;

import com.pinpointgrowth.DTO.FinalEvaluationDTO;
import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.beans.TeacherEvaluationBean;


@WebServlet(urlPatterns = { "/TraditionalEvaluation"})
public class TraditionalEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int cID;
    private int numberOfRange;
    private int preHighestScore;
    private int postHighestScore;

    private ArrayList<Integer> preCutScore = new ArrayList<Integer>();
    private ArrayList<Integer> postCutScore = new ArrayList<Integer>();

    private float performanceWeight;
    private float traditionalWeight;

    private float performancePassRate;
    private float traditionalPassRate;

    private StudentEvaluationBean studentEvaluationBean= new StudentEvaluationBean();

    private void setHighestScore() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String preHighestScoreSQL = TraditionalConstants.PRE_HIGHEST_SCORE_SQL(cID);
        String postHighestScoreSQL = TraditionalConstants.POST_HIGHEST_SCORE_SQL(cID);
        ResultSet resultSet = statement.executeQuery(preHighestScoreSQL);
        resultSet.last();
        preHighestScore = resultSet.getInt(resultSet.findColumn("PreHighest"));
        resultSet = statement.executeQuery(postHighestScoreSQL);
        resultSet.last();
        postHighestScore = resultSet.getInt(resultSet.findColumn("PostHighest"));

        connection.close();
        statement.close();
        resultSet.close();
    }

    private void setCutScore() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String setPreCutScoreSQL = TraditionalConstants.SET_PRE_CUT_SCORE_SQL(cID);
        String setPostCutScoreSQL = TraditionalConstants.SET_POST_CUT_SCORE_SQL(cID);
        ResultSet resultSet = statement.executeQuery(setPreCutScoreSQL);
        while(resultSet.next()){
            preCutScore.add(resultSet.getInt(resultSet.findColumn("CutScore")));
        }
        resultSet = statement.executeQuery(setPostCutScoreSQL);
        while(resultSet.next()){
            postCutScore.add(resultSet.getInt(resultSet.findColumn("CutScore")));
        }

        connection.close();
        statement.close();
        resultSet.close();
    }

    private void getStudentRecord() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String getStudentNameAndScoreSQL = TraditionalConstants.STUDENT_NAME_AND_SCORE_SQL(cID);
        ResultSet resultSet = statement.executeQuery(getStudentNameAndScoreSQL);

        ArrayList<EvaluationDTO> evaluationList = new ArrayList<EvaluationDTO>();

        float numberOfPassed = 0;
        while(resultSet.next()){
            EvaluationDTO evaluationDTO = new EvaluationDTO();
            int studentID = resultSet.getInt(resultSet.findColumn("S_ID"));
            String studentFirstName = resultSet.getString(resultSet.findColumn("SFName"));
            String studentLastName = resultSet.getString(resultSet.findColumn("SLName"));
            int preTestScore = resultSet.getInt(resultSet.findColumn("PreGradeTrad"));
            int postTestScore = resultSet.getInt(resultSet.findColumn("PostGradeTrad"));
            boolean isPassed = false;  
            boolean performancePassed = false;  // by default is faulse
            evaluationDTO.setSFName(studentFirstName);
            evaluationDTO.setSLName(studentLastName);
            evaluationDTO.setPreTestScore(preTestScore);
            evaluationDTO.setPostTestScore(postTestScore);

            switch(preCutScore.size()){
                case 3:
                    if(preTestScore > preCutScore.get(1)){ // preTest is in top range
                        if(postTestScore > postCutScore.get(1)){ // postTest is still in Top range
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(0)){ // preTest is in second range
                        if(postTestScore > postCutScore.get(0)){ // postTest is is still in second range or top range
                            isPassed = true;
                        }
                    }
                    else{ // preTest is in lowest range
                        if((preHighestScore - preTestScore) > (postHighestScore - postTestScore)){
                            isPassed = true;
                        }
                    }
                    break;
                case 4:
                    if(preTestScore > preCutScore.get(2)){
                        if(postTestScore > postCutScore.get(2)){
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(1)){
                        if(postTestScore > postCutScore.get(1)){
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(0)){
                        if(postTestScore > postCutScore.get(0)){
                            isPassed = true;
                        }
                    }
                    else{
                        if((preHighestScore - preTestScore) > (postHighestScore - postTestScore)){
                            isPassed = true;
                        }
                    }
                    break;
                case 5:
                    if(preTestScore > preCutScore.get(3)){
                        if(postTestScore > postCutScore.get(3)){
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(2)){
                        if(postTestScore > postCutScore.get(2)){
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(1)){
                        if(postTestScore > postCutScore.get(1)){
                            isPassed = true;
                        }
                    }
                    else if(preTestScore > preCutScore.get(0)){
                        if(postTestScore > postCutScore.get(0)){
                            isPassed = true;
                        }
                    }
                    else{
                        if((preHighestScore - preTestScore) > (postHighestScore - postTestScore)){
                            isPassed = true;
                        }
                    }
                    break;
                default:
                    break;  
            }
            evaluationDTO.setPassed(isPassed);
            // if(yesList.size()!=0){
            //     for(FinalEvaluationDTO finalEvaluationRecord : yesList){
            //         if(finalEvaluationRecord.getStudentDTO().getStudentID() == studentID){
            //             performancePassed = true;
            //             break;
            //         }
            //     }
            // }
            evaluationDTO.setPerformancePassed(performancePassed);
            if(isPassed){
                numberOfPassed+= 1;
            }
            evaluationList.add(evaluationDTO);
        }
        traditionalPassRate = numberOfPassed/(float)evaluationList.size();//set traditional pass rate
        studentEvaluationBean.setTraditionalPassRate(traditionalPassRate);
        studentEvaluationBean.setEvaluationList(evaluationList);

        connection.close();
        statement.close();
        resultSet.close();
    }

    private void setWeight() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String getWeightSQL = TraditionalConstants.GET_WEIGHT_SQL(cID);
        ResultSet resultSet = statement.executeQuery(getWeightSQL);
        resultSet.last();
        performanceWeight = resultSet.getFloat(resultSet.findColumn("PerformanceWeight")) / 100;
        traditionalWeight = resultSet.getFloat(resultSet.findColumn("TraditionalWeight")) / 100;

        studentEvaluationBean.setPerformanceWeight(performanceWeight);
        studentEvaluationBean.setTraditionalWeight(traditionalWeight);

        connection.close();
        statement.close();
        resultSet.close();
    }

    //=======================DANGER SECTION BEGIN===========================
    private List<StudentDTO> getStudentsForCourse(int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<StudentDTO> returnList = new ArrayList<StudentDTO>();
        ResultSet resultSet = statement.executeQuery(Constants.GET_COURSE(cID));
        resultSet.first();
        String courseName = resultSet.getString(resultSet.findColumn("CName"));

        resultSet = statement.executeQuery(Constants.GET_STUDENTS_FOR_COURSE(cID));  //studentIDs result
        while (resultSet.next()){
            int studentID = resultSet.getInt(resultSet.findColumn("S_ID"));
            ResultSet studentResult = statement.executeQuery(Constants.GET_STUDENT(studentID));
            studentResult.first();
            StudentDTO studentDTO = new StudentDTO();
            String firstName = studentResult.getString(studentResult.findColumn("SFName"));
            String lastName = studentResult.getString(studentResult.findColumn("SLName"));
            int gradeLevel = studentResult.getInt(studentResult.findColumn("GradeLevel"));
            studentDTO.setFirstName(firstName);
            studentDTO.setLastName(lastName);
            studentDTO.setGradeLevel(gradeLevel);
            studentDTO.setStudentID(studentID);
            studentDTO.setCourseName(courseName);
            studentDTO.setCourseID(cID);
            returnList.add(studentDTO);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return returnList;
    }

    private boolean getTopTier(StudentDTO student) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        int courseID = student.getCourseID();
        int studentID = student.getStudentID();
        ResultSet enrolled = statement.executeQuery(Constants.GET_ENROLLED(studentID, courseID));
        enrolled.first();
        int tier = enrolled.getInt(enrolled.findColumn("Tier"));
        // if teacher chose not to tier, tier will be 0 here
        boolean result = false;
        if (tier == 1) {
            result = true;
        }

        connection.close();
        statement.close();
        enrolled.close();
        return result;
    }

    private boolean courseHasExtensionActivity(int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Constants.GET_EXTENSION_ACTIVITY_FOR_COURSE(cID));

        boolean result = resultSet.first();

        connection.close();
        statement.close();
        resultSet.close();
        return result;
    }

    private boolean getExtensionMet(StudentDTO student) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        int courseID = student.getCourseID();
        int studentID = student.getStudentID();
        ResultSet resultSet = statement.executeQuery(Constants.GET_ENROLLED(studentID, courseID));
        resultSet.first();
        String extensionMet = resultSet.getString(resultSet.findColumn("ExtensionMet"));
        boolean result = false;
        if (extensionMet.equals("Y")) {
            result = true;
        }

        connection.close();
        statement.close();
        resultSet.close();
        return result;
    }

    private double getPreScore(StudentDTO student, int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<Integer> oIDList = getObjectiveIDs(cID, "N");
        int sumOfScores = 0;
        for (Integer oID : oIDList) {
            ResultSet objective = statement.executeQuery(Constants.GET_OBJECTIVE(oID));
            objective.first();
            int weight = objective.getInt(objective.findColumn("Weight"));
            ResultSet score = statement.executeQuery(Constants.GET_STUOBJLOOKUP(student.getStudentID(), oID));
            score.first();
            int scoreValue = score.getInt(score.findColumn("PreGradePerf"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        connection.close();
        statement.close();
        return score;
    }

    private double getMaxPreScore(int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<Integer> oIDList = getObjectiveIDs(cID, "N");
        int sumOfScores = 0;
        for (Integer oID : oIDList) {
            ResultSet objective = statement.executeQuery(Constants.GET_OBJECTIVE(oID));
            objective.first();
            int weight = objective.getInt(objective.findColumn("Weight"));
            int scoreValue = objective.getInt(objective.findColumn("MaxScore"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        connection.close();
        statement.close();
        return score;
    }

    private List<FinalEvaluationDTO> setupScores(List<StudentDTO> studentList, int cID) throws ClassNotFoundException, SQLException {
        List<FinalEvaluationDTO> returnList = new ArrayList<FinalEvaluationDTO>();

        for (StudentDTO student : studentList) {
            FinalEvaluationDTO finalEvaluationDTO = new FinalEvaluationDTO();
            finalEvaluationDTO.setStudentDTO(student);
            boolean topTier = getTopTier(student);
            if (courseHasExtensionActivity(cID)) {
                if (topTier) {
                    finalEvaluationDTO.setTopTier(true);
                    finalEvaluationDTO.setExtensionMet(getExtensionMet(student));
                } else {
                    finalEvaluationDTO.setTopTier(false);
                }
            } else {
                finalEvaluationDTO.setTopTier(false);
            }
            double preScore = getPreScore(student, cID);
            double postScore = getPostScore(student, cID);
            finalEvaluationDTO.setPreScore(preScore);
            finalEvaluationDTO.setPostScore(postScore);
            double maxPreScore = getMaxPreScore(cID);
            double maxPostScore = getMaxPostScore(cID);
            double growth = (postScore / maxPostScore) - (preScore / maxPreScore);
            finalEvaluationDTO.setGrowth(growth);
            returnList.add(finalEvaluationDTO);
        }
        return returnList;
    }

    private List<Integer> getObjectiveIDs(int cID, String preOrPost) throws ClassNotFoundException, SQLException {
        List<Integer> returnList = new ArrayList<Integer>();
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        ResultSet rIDs = statement.executeQuery(Constants.GET_ALL_ASSIGNMENTS_FOR_COURSE(cID));
        while (rIDs.next()) {
            int rID = rIDs.getInt(rIDs.findColumn("R_ID"));
            Statement statement3 = connection.createStatement();
            ResultSet rubric = statement3.executeQuery(Constants.GET_RUBRIC(rID));
            rubric.first();
            String postScore = rubric.getString(rubric.findColumn("PostScore"));
            // only looking for pre or post
            if (postScore.equals(preOrPost)) {
                Statement statement2 = connection.createStatement();
                ResultSet oIDs = statement2.executeQuery(Constants.GET_OBJECTIVES_FOR_RUBRIC(rID));
                while (oIDs.next()) {
                    int oID = oIDs.getInt(oIDs.findColumn("O_ID"));
                    returnList.add(oID);
                }
            }
        }

        connection.close();
        statement.close();
        return returnList;
    }

    private double getMaxPostScore(int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<Integer> oIDList = getObjectiveIDs(cID, "Y");
        int sumOfScores = 0;
        for (Integer oID : oIDList) {
            ResultSet objective = statement.executeQuery(Constants.GET_OBJECTIVE(oID));
            objective.first();
            int weight = objective.getInt(objective.findColumn("Weight"));
            int scoreValue = objective.getInt(objective.findColumn("MaxScore"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        connection.close();
        statement.close();
        return score;
    }

    private double getPostScore(StudentDTO student, int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<Integer> oIDList = getObjectiveIDs(cID, "Y");
        int sumOfScores = 0;
        for (Integer oID : oIDList) {
            ResultSet objective = statement.executeQuery(Constants.GET_OBJECTIVE(oID));
            objective.first();
            int weight = objective.getInt(objective.findColumn("Weight"));
            ResultSet score = statement.executeQuery(Constants.GET_STUOBJLOOKUP(student.getStudentID(), oID));
            score.first();
            int scoreValue = score.getInt(score.findColumn("PreGradePerf"));
            sumOfScores += (scoreValue * weight);
        }
        double score = (double) sumOfScores / (double) oIDList.size();

        connection.close();
        statement.close();
        return score;
    }

    private List<FinalEvaluationDTO> getYesList(List<FinalEvaluationDTO> finalList, int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<FinalEvaluationDTO> yesList = new ArrayList<FinalEvaluationDTO>();
        for (FinalEvaluationDTO finalEvaluationDTO : finalList) {
            StudentDTO studentDTO = finalEvaluationDTO.getStudentDTO();
            //statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.GET_ENROLLED(studentDTO.getStudentID(), cID));
            resultSet.first();
            double target = resultSet.getDouble(resultSet.findColumn("GrowthTarget"));
            finalEvaluationDTO.getStudentDTO().setGrowthTarget(target);
            double maxPostScore = getMaxPostScore(cID);
            double postScore = getPostScore(finalEvaluationDTO.getStudentDTO(), cID);
            if ((finalEvaluationDTO.getGrowth() >= target) || (maxPostScore == postScore)) {
                if (finalEvaluationDTO.getTopTier()) {
                    if (finalEvaluationDTO.getExtensionMet()) {
                        yesList.add(finalEvaluationDTO);
                    }
                } else {
                    yesList.add(finalEvaluationDTO);
                }
            }
        }

        connection.close();
        statement.close();
        return yesList;
    }

    private List<FinalEvaluationDTO> getNoList(List<FinalEvaluationDTO> finalList, int cID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        List<FinalEvaluationDTO> noList = new ArrayList<FinalEvaluationDTO>();
        for (FinalEvaluationDTO finalEvaluationDTO : finalList) {
            StudentDTO studentDTO = finalEvaluationDTO.getStudentDTO();
            //statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.GET_ENROLLED(studentDTO.getStudentID(), cID));
            resultSet.first();
            double target = resultSet.getDouble(resultSet.findColumn("GrowthTarget"));
            finalEvaluationDTO.getStudentDTO().setGrowthTarget(target);
            double maxPostScore = getMaxPostScore(cID);
            double postScore = getPostScore(finalEvaluationDTO.getStudentDTO(), cID);
            if ((finalEvaluationDTO.getGrowth() < target) && (maxPostScore != postScore)) {
                noList.add(finalEvaluationDTO);
            } else {
                if (finalEvaluationDTO.getTopTier()) {
                    if (!finalEvaluationDTO.getExtensionMet()) {
                        noList.add(finalEvaluationDTO);
                    }
                }
            }
        }

        connection.close();
        statement.close();
        return noList;
    }
    //=======================DANGER SECTION ENDS===========================

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PreTestSetupBean preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");
        cID = preTestSetupBean.getcID();
        numberOfRange = preTestSetupBean.getNumberOfRange();

        try {
            setHighestScore();
            setCutScore();
            setWeight();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try{
            //=======================DANGER SECTION BEGIN===========================
            List<StudentDTO> studentList = getStudentsForCourse(cID);
            List<FinalEvaluationDTO> finalList = setupScores(studentList, cID);
            List<FinalEvaluationDTO> yesList = getYesList(finalList, cID);
            List<FinalEvaluationDTO> noList = getNoList(finalList, cID);
            if(finalList.size() == 0 || yesList.size() == 0 || noList.size() == 0){
                performancePassRate = 0;
                studentEvaluationBean.setPerformancePassRate(0);
            } else{
                performancePassRate = (float) yesList.size() / (float)finalList.size();
                studentEvaluationBean.setPerformancePassRate(performancePassRate);
            }

            //=======================DANGER SECTION ENDS===========================

        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            performancePassRate = 0;
            studentEvaluationBean.setPerformancePassRate(0);
        }

        try{
            getStudentRecord();
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        studentEvaluationBean.setResult(performanceWeight*performancePassRate + traditionalWeight*traditionalPassRate);
        request.setAttribute("studentEvaluationBean", studentEvaluationBean);

        String nextJSP = "/traditionalEvaluation.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
