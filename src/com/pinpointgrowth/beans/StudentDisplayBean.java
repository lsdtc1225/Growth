package com.pinpointgrowth.beans;

import java.util.List;

import com.pinpointgrowth.DTO.StudentDTO;

public class StudentDisplayBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8583462485323479219L;
    private List<StudentDTO> studentList;

    public List<StudentDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDTO> studentList) {
        this.studentList = studentList;
    }
}
