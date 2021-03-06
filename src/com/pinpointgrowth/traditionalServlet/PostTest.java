package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;
import com.pinpointgrowth.traditionalConstants.TraditionalConstants;
import com.pinpointgrowth.traditionalBeans.PreTestSetupBean;


@WebServlet(urlPatterns = {"/PostTest" })
public class PostTest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginBean loginBean;
    private PreTestSetupBean preTestSetupBean;

    private String userName;
    private int cID;
    private int numberOfRange;

    private HashMap<Integer, String> scoreDescriptionMap;

    private boolean recordExist() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String checkSetupForPostTestSQL = TraditionalConstants.CHECK_SETUP_FOR_POST_TEST_SQL(cID);
        ResultSet resultSet = statement.executeQuery(checkSetupForPostTestSQL);
        Boolean result = resultSet.first();

        connection.close();
        statement.close();
        resultSet.close();
        return result;
    }

    private void savePostTestRecord() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        if(recordExist()){
            String deleteSetupForPostTestSQL = TraditionalConstants.DELETE_SETUP_FOR_POST_TEST_SQL(cID);
            statement.executeUpdate(deleteSetupForPostTestSQL);
        }
            
        for(int scoreLevel = 1; scoreLevel <= numberOfRange; scoreLevel++){
            if(!scoreDescriptionMap.isEmpty()){
                int min = Collections.min(scoreDescriptionMap.keySet());
                String addSetupForPostTestSQL = TraditionalConstants.ADD_SETUP_FOR_POST_TEST_SQL(cID, min, scoreDescriptionMap.get(min), scoreLevel);
                statement.executeUpdate(addSetupForPostTestSQL);
                scoreDescriptionMap.remove(min);
            }
        }

        connection.close();
        statement.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");

        userName = loginBean.getUsername();
        cID = preTestSetupBean.getcID();
        numberOfRange = preTestSetupBean.getNumberOfRange();

        scoreDescriptionMap = new HashMap<Integer, String>();

        for(int i = 1; i <= numberOfRange; i++){
            String paramTopScore = request.getParameter("topScore_"+i).replace("" + '"', "").replace(";", "");
            String paramDescription = request.getParameter("description_"+i).replace("" + '"', "").replace(";", "");
            scoreDescriptionMap.put(Integer.parseInt(paramTopScore), paramDescription);
        }

        try {
            savePostTestRecord();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        String nextJSP = "/postTest3.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }






}
