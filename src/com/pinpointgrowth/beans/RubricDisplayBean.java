package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pinpointgrowth.DTO.RubricDTO;
import com.pinpointgrowth.constants.Constants;

public class RubricDisplayBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -636682931382032974L;
    private List<RubricDTO> rubricList;
    private String userName;
    private String postScore;

    public List<RubricDTO> getRubricList() throws SQLException,
            ClassNotFoundException {
        if (rubricList == null) {
            rubricList = new ArrayList<RubricDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            int teacherID = getTeacherID(statement);
            ResultSet results = statement.executeQuery(Constants
                    .GET_RUBRIC_IDS_FOR_TEACHER(teacherID));
            while (results.next()) {
                Statement statement2 = con.createStatement();
                int r_id = results.getInt(results.findColumn("R_ID"));
                ResultSet result = statement2.executeQuery(Constants
                        .GET_RUBRIC(r_id));
                result.first();
                RubricDTO rubricDTO = new RubricDTO();
                rubricDTO.setRubricID(r_id);
                rubricDTO.setRubricName(result.getString(result
                        .findColumn("RName")));
                String rubricPostScore = result.getString(result
                        .findColumn("PostScore"));
                // only add if postscore == 'N' for prescore and only
                // add if postscore == 'Y' for postscore
                if (rubricPostScore.equals(postScore)) {
                    rubricList.add(rubricDTO);
                }
            }
        }
        return rubricList;
    }

    public void setRubricList(List<RubricDTO> rubricList) {
        this.rubricList = rubricList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private int getTeacherID(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(Constants
                .TEACHER_ID_QUERY(userName));
        resultSet.first();
        int column = resultSet.findColumn("T_ID");
        int teacherID = resultSet.getInt(column);
        return teacherID;
    }

    public String getPostScore() {
        return postScore;
    }

    public void setPostScore(String postScore) {
        this.postScore = postScore;
    }

}
