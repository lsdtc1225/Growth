package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pinpointgrowth.DTO.LateStudentDTO;
import com.pinpointgrowth.DTO.ObjectiveDTO;
import com.pinpointgrowth.constants.Constants;

public class LateStudentBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4794217384006785289L;
    private int courseID;
    private List<LateStudentDTO> lateStudentList;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public List<LateStudentDTO> getLateStudentList()
            throws ClassNotFoundException, SQLException {
        if (lateStudentList == null) {
            lateStudentList = new ArrayList<LateStudentDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet assignments = statement.executeQuery(Constants
                    .GET_ALL_ASSIGNMENTS_FOR_COURSE(courseID));
            while (assignments.next()) {
                LateStudentDTO lateStudentDTO = new LateStudentDTO();
                String assignmentName = assignments.getString(assignments
                        .findColumn("AName"));
                int rubricID = assignments.getInt(assignments
                        .findColumn("R_ID"));
                lateStudentDTO.setAssignmentName(assignmentName);
                Statement statement2 = con.createStatement();
                ResultSet objectiveIDs = statement2.executeQuery(Constants
                        .GET_OBJECTIVES_FOR_RUBRIC(rubricID));
                List<ObjectiveDTO> obList = new ArrayList<ObjectiveDTO>();
                while (objectiveIDs.next()) {
                    ObjectiveDTO objectiveDTO = new ObjectiveDTO();
                    int objectiveID = objectiveIDs.getInt(objectiveIDs
                            .findColumn("O_ID"));
                    Statement statement3 = con.createStatement();
                    ResultSet obj = statement3.executeQuery(Constants
                            .GET_OBJECTIVE(objectiveID));
                    obj.first();
                    String objectiveName = obj.getString(obj
                            .findColumn("OName"));
                    objectiveDTO.setObjectiveID(objectiveID);
                    objectiveDTO.setObjectiveName(objectiveName);
                    obList.add(objectiveDTO);
                }
                lateStudentDTO.setObjectiveList(obList);
                lateStudentList.add(lateStudentDTO);
            }
        }
        return lateStudentList;
    }

    public void setLateStudentList(List<LateStudentDTO> lateStudentList) {
        this.lateStudentList = lateStudentList;
    }

}
