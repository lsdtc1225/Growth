package com.pinpointgrowth.beans;

public class RubricCreationBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6816456880669264325L;

    private int numberOfSLO;
    private String rubricName;

    public int getNumberOfSLO() {
        return numberOfSLO;
    }

    public void setNumberOfSLO(int numberOfSLO) {
        this.numberOfSLO = numberOfSLO;
    }

    public String getRubricName() {
        return rubricName;
    }

    public void setRubricName(String rubricName) {
        this.rubricName = rubricName;
    }

}
