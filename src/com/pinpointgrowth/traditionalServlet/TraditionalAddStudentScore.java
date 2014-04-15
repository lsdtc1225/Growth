package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.beans.AddCourseManualBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.excel.StudentDataRecord;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;


@WebServlet(urlPatterns={"/TraditionalAddStudentScore"})
public class TraditionalAddStudentScore extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int cID;
    private String cName;
    private int preTestScore;
    private int postTestScore;
    
    private void savePreTestRecord(int sID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String addPreTestScoreSQL = TraditionalConstants.ADD_PRE_TEST_SCORE_SQL(cID, sID, preTestScore);
        statement.executeUpdate(addPreTestScoreSQL);

        connection.close();
        statement.close();
    }

    private void savePostTestRecord(int sID) throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String updatePostTestScoreSQL = TraditionalConstants.UPDATE_POST_TEST_SCORE_SQL(cID, sID, postTestScore);
        statement.executeUpdate(updatePostTestScoreSQL);

        connection.close();
        statement.close();
    }

    private void getCourseName() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String courseNameSQL = TraditionalConstants.GET_COURSE_NAME_SQL(cID);
        ResultSet resultSet = statement.executeQuery(courseNameSQL);

        resultSet.first();
        cName = resultSet.getString(resultSet.findColumn("CName"));

        connection.close();
        statement.close();
        resultSet.close();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cID = Integer.parseInt(request.getParameter("cID"));
        

        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            int sID;
            if(paramName.matches("preTest_[1-9][0-9]*")){//preTestScore
                sID = Integer.parseInt(paramName.substring(8));
                String paramValue = request.getParameter(paramName);
                if(paramValue.equals("")){
                    preTestScore = 0;
                } else{
                    preTestScore = Integer.parseInt(paramValue);
                }
                try{
                    savePreTestRecord(sID);
                } catch (ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                }
            } else if(paramName.matches("postTest_[1-9][0-9]*")){ // postTestScore
                sID = Integer.parseInt(paramName.substring(9));
                String paramValue = request.getParameter(paramName);
                if(paramValue.equals("")){
                    postTestScore = 0;
                } else{
                    postTestScore = Integer.parseInt(paramValue);
                }
                try{
                    savePostTestRecord(sID);
                } catch (ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                }
            } else {
                continue;
            }
        }
        try{
            getCourseName();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        request.setAttribute("cName", cName);
        String nextJSP = "/addStudentSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
