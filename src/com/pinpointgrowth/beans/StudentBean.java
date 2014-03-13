package com.pinpointgrowth.beans;

import com.pinpointgrowth.DTO.StudentDTO;

public class StudentBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2982211659416638184L;
    private StudentDTO studentDTO;

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

}
