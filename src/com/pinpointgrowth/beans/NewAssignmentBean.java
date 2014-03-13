package com.pinpointgrowth.beans;

public class NewAssignmentBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -151075691946065238L;
    private String assignmentName;
    private int rubricID;
    private int courseID;

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getRubricID() {
        return rubricID;
    }

    public void setRubricID(int rubricID) {
        this.rubricID = rubricID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

}
