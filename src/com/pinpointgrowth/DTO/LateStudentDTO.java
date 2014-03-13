package com.pinpointgrowth.DTO;

import java.util.List;

public class LateStudentDTO {

    private String assignmentName;
    private List<ObjectiveDTO> objectiveList;

    public List<ObjectiveDTO> getObjectiveList() {
        return objectiveList;
    }

    public void setObjectiveList(List<ObjectiveDTO> objectiveList) {
        this.objectiveList = objectiveList;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

}
