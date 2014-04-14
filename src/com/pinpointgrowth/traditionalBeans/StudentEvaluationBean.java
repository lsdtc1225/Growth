package com.pinpointgrowth.traditionalBeans;

import java.util.ArrayList;

import com.pinpointgrowth.traditionalDTO.EvaluationDTO;

public class StudentEvaluationBean {
    

    private ArrayList<EvaluationDTO> evaluationList;
    private float performanceWeight;
	private float traditionalWeight;

	private float performancePassRate;
	private float traditionalPassRate;

    private float result;
	
	public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public float getPerformanceWeight() {
        return performanceWeight;
    }

    public void setPerformanceWeight(float performanceWeight) {
        this.performanceWeight = performanceWeight;
    }

    public float getTraditionalWeight() {
        return traditionalWeight;
    }

    public void setTraditionalWeight(float traditionalWeight) {
        this.traditionalWeight = traditionalWeight;
    }

    public float getPerformancePassRate() {
        return performancePassRate;
    }

    public void setPerformancePassRate(float performancePassRate) {
        this.performancePassRate = performancePassRate;
    }

    public float getTraditionalPassRate() {
        return traditionalPassRate;
    }

    public void setTraditionalPassRate(float traditionalPassRate) {
        this.traditionalPassRate = traditionalPassRate;
    }

    public ArrayList<EvaluationDTO> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(ArrayList<EvaluationDTO> evaluationList) {
        this.evaluationList = evaluationList;
    }
    
}
