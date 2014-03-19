package com.pinpointgrowth.traditionalServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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


@WebServlet(urlPatterns = { "/TraditionalEvaluation"})
public class TraditionalEvaluation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int cID;
	private int numberOfRange;
	private int preHighestScore;
	private int postHighestScore;
	

	private void setNumberOfRange() throws ClassNotFoundException, SQLException{
		Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PreTestSetupBean preTestSetupBean = (PreTestSetupBean) request.getSession().getAttribute("preTestSetupBean");
		cID = preTestSetupBean.getcID();
		numberOfRange = preTestSetupBean.getNumberOfRange();


		try {
            setNumberOfRange();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
		System.out.println("evaluation:" + numberOfRange);

		String nextJSP = "/test.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);
	}

}
