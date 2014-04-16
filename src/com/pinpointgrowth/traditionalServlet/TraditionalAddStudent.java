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
import com.pinpointgrowth.excel.StudentDataRecord;


@WebServlet(urlPatterns={"/TraditionalAddStudent"})
public class TraditionalAddStudent extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LoginBean loginBean;
    private String userName;
    private int numberOfStudent;
    private int cID;
    private ArrayList<StudentDataRecord> studentList;

    private void saveStudentList() throws ClassNotFoundException, SQLException{
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = connection.createStatement();

        for (int i = 0; i < numberOfStudent; i++){
            statement.executeUpdate(Constants.STUDENT_ADD_SQL(studentList.get(i).getStudentFirstName(), studentList.get(i).getStudentLastName(), studentList.get(i).getStudentGrade()));
            ResultSet resultSet = statement.executeQuery(Constants.GET_STUDENT_ID_SQL(studentList.get(i).getStudentFirstName(), studentList.get(i).getStudentLastName(), studentList.get(i).getStudentGrade()));
            resultSet.last();
            int sID = resultSet.getInt(resultSet.findColumn("S_ID"));
            studentList.get(i).setsID(sID);
            statement.executeUpdate(Constants.TAKING_ADD_SQL(sID, cID));
        }

        connection.close();
        statement.close();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        loginBean = (LoginBean) request.getSession().getAttribute("loginInfo");
        userName = loginBean.getUsername();
        numberOfStudent = Integer.parseInt(request.getParameter("numberOfStudent"));
        cID = Integer.parseInt(request.getParameter("cID"));
        studentList = new ArrayList<StudentDataRecord>();

        for(int i = 1; i <= numberOfStudent; i++){
            StudentDataRecord record = new StudentDataRecord();
            record.setStudentFirstName(request.getParameter("studentFirstName_" + i).replace("" + '"', "").replace(";", ""));
            record.setStudentLastName(request.getParameter("studentLastName_" + i).replace("" + '"', "").replace(";", ""));
            try {
                record.setStudentGrade(Integer.parseInt(request.getParameter("studentGrade_" + i).replace("" + '"', "").replace(";", "")));
            } catch (NumberFormatException e) {
                record.setStudentGrade(0);
            }
            studentList.add(record);
        }

        try{
            saveStudentList();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("studentList", studentList);
        String nextJSP = "/traditionalAddStudentScore.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }
}
