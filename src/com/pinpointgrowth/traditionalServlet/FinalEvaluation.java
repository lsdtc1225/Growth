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
import com.pinpointgrowth.traditionalBeans.FinalEvaluationBean;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;


@WebServlet(urlPatterns = {"/FinalEvaluation"})
public class FinalEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginBean loginBean;
    private String userName;
    private int tID;
    //private ArrayList<FinalEvaluationBean> finalEvaluationBeanList = new ArrayList<FinalEvaluationBean>();
    private float averageResult = 0;
    private int numberOfCourse = 0;
    
    private void getTeacherID() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Constants.TEACHER_ID_QUERY(userName));
        resultSet.first();
        tID = resultSet.getInt(resultSet.findColumn("T_ID"));

        connection.close();
        statement.close();
        resultSet.close();
    }

    private float getCourseResult(int cID) throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String courseResultSQL = TraditionalConstants.GET_COURSE_RESULT_SQL(cID);
        ResultSet resultSet = statement.executeQuery(courseResultSQL);
        resultSet.first();

        float result = resultSet.getFloat(resultSet.findColumn("Result"));
        if(resultSet.wasNull()){
            return (float) -1.0;
        } else{
            return result;
        }
    }

    private void getFinalEvaluationBeanList(ArrayList<FinalEvaluationBean> finalEvaluationBeanList) throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Constants.GET_COURSES_FOR_TEACHER(tID));
        while(resultSet.next()){
            int cID = resultSet.getInt(resultSet.findColumn("C_ID"));
            String cName = resultSet.getString(resultSet.findColumn("CName"));
            
            float result = getCourseResult(cID);
            // if(result < 0){
            //     continue;
            // }

            if(result >= 0){
                averageResult += result;
                numberOfCourse++;
            }

            FinalEvaluationBean finalEvaluationBean = new FinalEvaluationBean();
            finalEvaluationBean.setcID(cID);
            finalEvaluationBean.setcName(cName);
            finalEvaluationBean.setResult(result);

            finalEvaluationBeanList.add(finalEvaluationBean);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();

        ArrayList<FinalEvaluationBean> finalEvaluationBeanList = new ArrayList<FinalEvaluationBean>();

        try{
            getTeacherID();
            getFinalEvaluationBeanList(finalEvaluationBeanList);
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        if((finalEvaluationBeanList.size() != 0) && (numberOfCourse!=0)){
            averageResult /= (float)numberOfCourse;
        }

        request.setAttribute("finalEvaluationBeanList", finalEvaluationBeanList);
        request.setAttribute("averageResult", averageResult);

        String nextJSP = "/finalEvaluation.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

}
