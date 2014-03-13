package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pinpointgrowth.DTO.AssignmentDTO;
import com.pinpointgrowth.constants.Constants;

public class AssignmentListBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2865379971056002968L;
    private List<AssignmentDTO> assignmentList;
    private int courseID;
    private String postScore;

    public List<AssignmentDTO> getAssignmentList() throws SQLException,
            ClassNotFoundException {
        if (assignmentList == null) {
            assignmentList = new ArrayList<AssignmentDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(Constants
                    .GET_ALL_ASSIGNMENTS_FOR_COURSE(courseID));
            while (results.next()) {
                AssignmentDTO assignmentDTO = new AssignmentDTO();
                int a_id = results.getInt(results.findColumn("A_ID"));
                int r_id = results.getInt(results.findColumn("R_ID"));
                String assignmentName = results.getString(results
                        .findColumn("AName"));
                assignmentDTO.setAssignmentID(a_id);
                assignmentDTO.setAssignmentName(assignmentName);
                assignmentDTO.setCourseID(courseID);
                assignmentDTO.setRubricID(r_id);
                Statement statement2 = con.createStatement();
                ResultSet rubric = statement2.executeQuery(Constants
                        .GET_RUBRIC(r_id));
                rubric.first();
                String rubricPostScore = rubric.getString(rubric
                        .findColumn("PostScore"));
                // only add if postScore == 'N' for prescore
                // and if postScore == 'Y' for postscore
                if (postScore.equals(rubricPostScore)) {
                    assignmentList.add(assignmentDTO);
                }

            }
        }
        return assignmentList;
    }

    public void setAssignmentList(List<AssignmentDTO> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getPostScore() {
        return postScore;
    }

    public void setPostScore(String postScore) {
        this.postScore = postScore;
    }

}
