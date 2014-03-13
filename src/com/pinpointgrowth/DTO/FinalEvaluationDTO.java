package com.pinpointgrowth.DTO;

public class FinalEvaluationDTO implements Comparable<FinalEvaluationDTO> {

    private double preScore;
    private double postScore;
    private double growth;
    private StudentDTO studentDTO;
    private boolean extensionMet;
    private boolean topTier;

    public double getPreScore() {
        return preScore;
    }

    public void setPreScore(double preScore) {
        this.preScore = preScore;
    }

    public double getPostScore() {
        return postScore;
    }

    public void setPostScore(double postScore) {
        this.postScore = postScore;
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    @Override
    public int compareTo(FinalEvaluationDTO o) {
        if (this.studentDTO.getCourseName().compareTo(
                o.getStudentDTO().getCourseName()) == 0) {
            if (this.studentDTO.getLastName().compareTo(
                    o.getStudentDTO().getLastName()) == 0) {
                return this.studentDTO.getFirstName().compareTo(
                        o.getStudentDTO().getFirstName());
            } else {
                return this.studentDTO.getLastName().compareTo(
                        o.getStudentDTO().getLastName());
            }
        } else {
            return this.studentDTO.getCourseName().compareTo(
                    o.getStudentDTO().getCourseName());
        }

    }

    public boolean getExtensionMet() {
        return extensionMet;
    }

    public void setExtensionMet(boolean extensionMet) {
        this.extensionMet = extensionMet;
    }

    public boolean getTopTier() {
        return topTier;
    }

    public void setTopTier(boolean topTier) {
        this.topTier = topTier;
    }
}
