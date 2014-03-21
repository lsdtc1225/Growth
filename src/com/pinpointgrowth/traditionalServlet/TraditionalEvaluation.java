package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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


@WebServlet(urlPatterns = { "/TraditionalEvaluation"})
public class TraditionalEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int cID;
    private int numberOfRange;
    private int preHighestScore;
    private int postHighestScore;

    private ArrayList<Integer> preCutScore = new ArrayList<Integer>();
    private ArrayList<Integer> postCutScore = new ArrayList<Integer>();

    private StudentEvaluationBean studentEvaluationBean= new StudentEvaluationBean();


    private void setHighestScore() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        String preHighestScoreSQL = TraditionalConstants.PRE_HIGHEST_SCORE_SQL(cID);
        String postHighestScoreSQL = TraditionalConstants.POST_HIGHEST_SCORE_SQL(cID);
        ResultSet resultSet = statement.executeQuery(preHighestScoreSQL);
        resultSet.last();
        int column = resultSet.findColumn("PreHighest");
        preHighestScore = resultSet.getInt(column);
        resultSet = statement.executeQuery(postHighestScoreSQL);
        resultSet.last();
        column = resultSet.findColumn("PostHighest");
        postHighestScore = resultSet.getInt(column);
    }   

    private void setCutScore() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

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
    }

    private void getStudentRecord() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        String getStudentNameAndScoreSQL = TraditionalConstants.STUDENT_NAME_AND_SCORE_SQL(cID);
        ResultSet resultSet = statement.executeQuery(getStudentNameAndScoreSQL);

        ArrayList<EvaluationDTO> evaluationList = new ArrayList<EvaluationDTO>();

        while(resultSet.next()){
            EvaluationDTO evaluationDTO = new EvaluationDTO();
            String studentFirstName = resultSet.getString(resultSet.findColumn("SFName"));
            String studentLastName = resultSet.getString(resultSet.findColumn("SLName"));
            int preTestScore = resultSet.getInt(resultSet.findColumn("PreGradeTrad"));
            int postTestScore = resultSet.getInt(resultSet.findColumn("PostGradeTrad"));
            boolean isPassed = false;
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
            evaluationList.add(evaluationDTO);
        }
        studentEvaluationBean.setEvaluationList(evaluationList);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PreTestSetupBean preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");
        cID = preTestSetupBean.getcID();
        numberOfRange = preTestSetupBean.getNumberOfRange();

        try {
            setHighestScore();
            setCutScore();
            getStudentRecord();
            request.setAttribute("studentEvaluationBean", studentEvaluationBean);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        String nextJSP = "/traditionalEvaluation.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

}
