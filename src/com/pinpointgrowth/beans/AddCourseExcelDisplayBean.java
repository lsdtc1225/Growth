package com.pinpointgrowth.beans;

import java.util.List;

import com.pinpointgrowth.excel.StudentDataRecord;

/**
 * bean used to validate username and password from frontPage.jsp
 */
public class AddCourseExcelDisplayBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8080442316627539932L;
    private String courseName;
    private String courseTerm;
    private String courseRoom;
    private List<StudentDataRecord> studentList;

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

    public List<StudentDataRecord> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDataRecord> studentList) {
        this.studentList = studentList;
    }

}
