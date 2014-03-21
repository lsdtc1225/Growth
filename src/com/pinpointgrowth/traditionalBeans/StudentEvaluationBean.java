package com.pinpointgrowth.traditionalBeans;

import java.util.ArrayList;

import com.pinpointgrowth.traditionalDTO.EvaluationDTO;

public class StudentEvaluationBean {
    

    private ArrayList<EvaluationDTO> evaluationList;


    public ArrayList<EvaluationDTO> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(ArrayList<EvaluationDTO> evaluationList) {
        this.evaluationList = evaluationList;
    }
    
}
