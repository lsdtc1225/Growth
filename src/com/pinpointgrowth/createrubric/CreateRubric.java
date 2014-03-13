package com.pinpointgrowth.createrubric;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.pinpointgrowth.beans.LoginBean;
import com.pinpointgrowth.constants.Constants;

public class CreateRubric extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 5068516882428752262L;
    /**
     * 
     */

    private List<SLORecord> SLOList;

    private String userName;
    private String postScore;
    private int numberOfSLO;
    private String rubricName;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LoginBean loginBean = (LoginBean) request.getSession().getAttribute(
                "loginInfo");
        userName = loginBean.getUsername();
        postScore = request.getParameter("postScore");
        numberOfSLO = Integer.parseInt(request.getParameter("numberOfSLO")
                .trim());
        rubricName = request.getParameter("rubricName").trim();
        SLOList = new ArrayList<SLORecord>();

        for (int i = 1; i < (((numberOfSLO) + 1)); i++) {
            SLORecord record = new SLORecord();
            record.setName(request.getParameter("SLOName_" + i)
                    .replace("" + '"', "").replace(";", ""));
            record.setDesc(request.getParameter("SLODesc_" + i)
                    .replace("" + '"', "").replace(";", ""));
            try {
                record.setMinScore(Integer.parseInt(request
                        .getParameter("SLOMinScore_" + i).replace("" + '"', "")
                        .replace(";", "")));
            } catch (NumberFormatException e) {
                record.setMinScore(0);
            }
            try {
                record.setMaxScore(Integer.parseInt(request
                        .getParameter("SLOMaxScore_" + i).replace("" + '"', "")
                        .replace(";", "")));
            } catch (NumberFormatException e) {
                record.setMaxScore(0);
            }
            try {
                record.setWeight((int) (Double.parseDouble(request
                        .getParameter("SLOWeight_" + i).replace("" + '"', "")
                        .replace(";", ""))));
            } catch (NumberFormatException e) {
                record.setWeight(0);
            }

            SLOList.add(record);
        }

        try {
            saveSLOList();

        } catch (Exception e) {
            ExceptionUtils.printRootCauseStackTrace(e);
        }

        request.getSession().removeAttribute("sessionRubricCreationBean");
        request.removeAttribute("rubricCreationBean");
        String nextJSP = "/rubricCreationSuccess.jsp";
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private void saveSLOList() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);
        Statement statement = con.createStatement();

        int t_id = getTeacherID(statement);
        if (postScore.equals("N")) {
            statement.executeUpdate(Constants
                    .ADD_NEW_PRESCORE_RUBRIC_SQL(rubricName));
        } else {
            statement.executeUpdate(Constants
                    .ADD_NEW_POSTSCORE_RUBRIC_SQL(rubricName));
        }
        ResultSet rubrics = statement.executeQuery(Constants
                .GET_ALL_RUBRICS_WITH_NAME(rubricName));
        rubrics.last();
        int r_id = rubrics.getInt(rubrics.findColumn("R_ID"));
        statement.executeUpdate(Constants.ADD_RUBRIC_TO_TEACHER(r_id, t_id));

        for (SLORecord record : SLOList) {
            statement.executeUpdate(Constants.ADD_OBJECTIVE_FROM_RUBRIC(
                    record.getName(), record.getDesc(), record.getMinScore(),
                    record.getMaxScore(), record.getWeight()));
            ResultSet result = statement.executeQuery(Constants.GET_OBJECTIVE(
                    record.getName(), record.getDesc(), record.getMinScore(),
                    record.getMaxScore(), record.getWeight()));
            result.last();
            int o_id = result.getInt(result.findColumn("O_ID"));
            statement.executeUpdate(Constants.ADD_OBJECTIVE_TO_RUBRIC(o_id,
                    r_id));
        }

    }

    private int getTeacherID(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(Constants
                .TEACHER_ID_QUERY(userName));
        resultSet.first();
        int column = resultSet.findColumn("T_ID");
        int teacherID = resultSet.getInt(column);
        return teacherID;
    }

}
