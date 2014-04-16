package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

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


@WebServlet(urlPatterns = { "/PostTestScore" })
public class PostTestScore extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginBean loginBean;
    PreTestSetupBean preTestSetupBean;

    private String userName;
    private int cID;

    private int sID;
    private int postTestScore;

    private void savePostTestRecord() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        String updatePostTestScoreSQL = TraditionalConstants.UPDATE_POST_TEST_SCORE_SQL(cID, sID, postTestScore);
        statement.executeUpdate(updatePostTestScoreSQL);

        connection.close();
        statement.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");
        userName = loginBean.getUsername();
        cID = preTestSetupBean.getcID();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            sID = Integer.parseInt(paramName.substring(8));
            String paramValue = request.getParameter(paramName);
            if (paramValue.equals("")){
                postTestScore = 0;
            } else{
                postTestScore = Integer.parseInt(paramValue);
            }
            
            try {
                savePostTestRecord();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        String nextJSP = "/postTestSetupSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

}
