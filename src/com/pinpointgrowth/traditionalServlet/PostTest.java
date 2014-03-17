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

/**
 * Servlet implementation class PostTest
 */
@WebServlet(urlPatterns = {"/PostTest" })
public class PostTest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String userName;
    private int cID;
    private int numberOfRange;
    private HashMap<Integer, String> scoreDescriptionMap = new HashMap<Integer, String>();

    private boolean recordExist(Statement statement) throws SQLException {
        String checkSetupForPostTestSQL = TraditionalConstants.CHECK_SETUP_FOR_POST_TEST_SQL(cID);
        ResultSet resultSet = statement.executeQuery(checkSetupForPostTestSQL);
        return resultSet.first();
    }

    private void savePostTestRecord() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        if(recordExist(statement)){
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
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        PreTestSetupBean preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");

        userName = loginBean.getUsername();
        cID = preTestSetupBean.getcID();
        numberOfRange = preTestSetupBean.getNumberOfRange();

        
        for(int i = 1; i <= numberOfRange; i++){
            String paramTopScore = request.getParameter("topScore_"+i).replace("" + '"', "").replace(";", "");
            String paramDescription = request.getParameter("description_"+i).replace("" + '"', "").replace(";", "");
            scoreDescriptionMap.put(Integer.parseInt(paramTopScore), paramDescription);
        }

        try {
            savePostTestRecord();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        String nextJSP = "/postTest3.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }






}
