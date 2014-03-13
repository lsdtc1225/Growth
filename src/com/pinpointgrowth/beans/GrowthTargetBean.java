package com.pinpointgrowth.beans;

import java.util.List;

import com.pinpointgrowth.DTO.StudentDTO;

public class GrowthTargetBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3324891717452197980L;
    private List<StudentDTO> targets;
    private int courseID;

    public List<StudentDTO> getTargets() {
        return targets;
    }

    public void setTargets(List<StudentDTO> targets) {
        this.targets = targets;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

}
