package com.pinpointgrowth.beans;

import java.text.DecimalFormat;
import java.util.List;

import com.pinpointgrowth.DTO.FinalEvaluationDTO;

public class TeacherEvaluationBean {
    private List<FinalEvaluationDTO> yesList;
    private List<FinalEvaluationDTO> noList;
    private double percentage;

    public List<FinalEvaluationDTO> getYesList() {
        return yesList;
    }

    public void setYesList(List<FinalEvaluationDTO> yesList) {
        this.yesList = yesList;
    }

    public List<FinalEvaluationDTO> getNoList() {
        return noList;
    }

    public void setNoList(List<FinalEvaluationDTO> noList) {
        this.noList = noList;
    }

    public String getPercentage() {
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(percentage);
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getNumericalRating() {
        Double percentCopy = this.percentage;
        percentCopy = percentCopy * 100;
        if (percentCopy.compareTo(90.0) >= 0) {
            return 5;
        } else if (percentCopy.compareTo(80.0) >= 0) {
            return 4;
        } else if (percentCopy.compareTo(70.0) >= 0) {
            return 3;
        } else if (percentCopy.compareTo(60.0) >= 0) {
            return 2;
        } else {
            return 1;
        }
    }

}
