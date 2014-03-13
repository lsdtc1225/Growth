package com.pinpointgrowth.beans;

import java.util.List;

import com.pinpointgrowth.DTO.CourseDTO;
import com.pinpointgrowth.DTO.RubricDTO;

public class CourseListRubricListBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7753800044152908018L;
    private List<CourseDTO> courseList;
    private List<RubricDTO> rubricList;

    public List<CourseDTO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseDTO> courseList) {
        this.courseList = courseList;
    }

    public List<RubricDTO> getRubricList() {
        return rubricList;
    }

    public void setRubricList(List<RubricDTO> rubricList) {
        this.rubricList = rubricList;
    }
}
