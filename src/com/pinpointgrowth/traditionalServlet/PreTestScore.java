package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.beans.CourseBean;
import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;
import com.pinpointgrowth.traditionalBeans.PreTestSetupBean;


@WebServlet(urlPatterns = { "/PreTestScore" })
public class PreTestScore extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginBean loginBean;
    private PreTestSetupBean preTestSetupBean;
    private String userName;
    private int cID;
    private String cName;


    private int sID;
    private int preTestScore;
    
    private boolean recordExist() throws ClassNotFoundException, SQLException {
        //Initiate DB connection
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();
        String checkPreScoreRecordSQL = TraditionalConstants.CHECK_PRE_SCORE_RECORD_SQL(cID, sID);
        ResultSet resultSet = statement.executeQuery(checkPreScoreRecordSQL);
        Boolean result = resultSet.first();
        //close DB connection
        connection.close();
        statement.close();
        resultSet.close();
        return result;
    }

    private void savePreTestRecord() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        if(recordExist()){
            String updatePreTestScoreSQL = TraditionalConstants.UPDATE_PRE_TEST_SCORE_SQL(cID, sID, preTestScore);
            statement.executeUpdate(updatePreTestScoreSQL);
        } else{
            String addPreTestScoreSQL = TraditionalConstants.ADD_PRE_TEST_SCORE_SQL(cID, sID, preTestScore);
            statement.executeUpdate(addPreTestScoreSQL);
        }

        connection.close();
        statement.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");

        userName = loginBean.getUsername();
        cID = preTestSetupBean.getcID();
        cName = preTestSetupBean.getcName();


        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            sID = Integer.parseInt(paramName.substring(8));
            String paramValue = request.getParameter(paramName);
            if (paramValue.equals("")){
                preTestScore = 0;
            } else{
                preTestScore = Integer.parseInt(paramValue);
            }
            
            try {
                savePreTestRecord();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String nextJSP = "/preTestSetupSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

}
