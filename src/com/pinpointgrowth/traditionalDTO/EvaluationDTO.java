package com.pinpointgrowth.traditionalDTO;

public class EvaluationDTO {
    private String SFName;
    private String SLName;
    private int preTestScore;
    private int postTestScore;
    private boolean passed;

    public String getSFName() {
        return SFName;
    }

    public boolean getPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setSFName(String sFName) {
        SFName = sFName;
    }
    public String getSLName() {
        return SLName;
    }
    public void setSLName(String sLName) {
        SLName = sLName;
    }
    public int getPreTestScore() {
        return preTestScore;
    }
    public void setPreTestScore(int preTestScore) {
        this.preTestScore = preTestScore;
    }
    public int getPostTestScore() {
        return postTestScore;
    }
    public void setPostTestScore(int postTestScore) {
        this.postTestScore = postTestScore;
    }

}
