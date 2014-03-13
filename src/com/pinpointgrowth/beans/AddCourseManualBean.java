package com.pinpointgrowth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddCourseManualBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7166143146578084181L;

    private String courseName;
    private String courseTerm;
    private String courseRoom;
    private String numberOfStudents;
    private List<String> lengthOptions;
    private List<String> defaultLengthOptions;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }

    public String getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(String numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<String> getLengthOptions() {
        defaultLengthOptions = new ArrayList<String>();
        defaultLengthOptions.add("Full Year");
        defaultLengthOptions.add("Semester");
        defaultLengthOptions.add("Quarter");
        List<String> returnList = new ArrayList<String>();
        if (courseRoom != null) {
            returnList.add(courseRoom);
            for (String option : defaultLengthOptions) {
                if (!returnList.contains(option)) {
                    returnList.add(option);
                }
            }
        } else {
            returnList = defaultLengthOptions;
        }
        return returnList;
    }

    public void setLengthOptions(List<String> lengthOptions) {
        this.lengthOptions = lengthOptions;
    }
}