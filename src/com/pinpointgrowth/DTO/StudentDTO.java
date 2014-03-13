package com.pinpointgrowth.DTO;

public class StudentDTO {

    private int studentID;
    private String firstName;
    private String lastName;
    private int gradeLevel;
    private boolean performanceScoresEntered;
    private double score;
    private double growthTarget;
    private String courseName;
    private int courseID;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public boolean isPerformanceScoresEntered() {
        return performanceScoresEntered;
    }

    public void setPerformanceScoresEntered(boolean performanceScoresEntered) {
        this.performanceScoresEntered = performanceScoresEntered;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getGrowthTarget() {
        return growthTarget;
    }

    public void setGrowthTarget(double growthTarget) {
        this.growthTarget = growthTarget;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
