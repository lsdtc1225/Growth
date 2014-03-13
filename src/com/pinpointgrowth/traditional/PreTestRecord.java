package com.pinpointgrowth.traditional;

import java.util.ArrayList;

public class PreTestRecord {
    private ArrayList<String> nameList;
    private ArrayList<Integer> scoreList;
    private boolean finishInsert;
    private int studentRecordCount;
    private int lowScore;
    private int highScore;
    private int numberOfDes;
    private ArrayList<Integer> scoreRange;
    private ArrayList<String> descriptionList;

    public PreTestRecord()
    {
        nameList = new ArrayList<String>();
        scoreList = new ArrayList<Integer>();
        finishInsert = false;
        studentRecordCount = 0;
        lowScore = 0;
        highScore = 0;
        numberOfDes = 0;
        scoreRange = new ArrayList<Integer>();
        descriptionList = new ArrayList<String>();
    }

    public ArrayList<String> getNameList(){
        return nameList;
    }
    public void setNameList(ArrayList<String> nameList){
        this.nameList = nameList;
    }
    public ArrayList<Integer> getScore(){
        return scoreList;
    }
    public void setScore(ArrayList<Integer> score){
        this.scoreList = score;
    }
    public void AddName(String name){
        nameList.add(name);
        studentRecordCount++;
    }
    public void AddScore(int score){
        scoreList.add(score);
    }
    public String getFirstName(){
        return nameList.get(0);
    }
    public void setToFinish(){
        this.finishInsert = true;
    }
    public int getStudentRecordCount(){
        return this.studentRecordCount;
    }

    public String getStudentName(int count){
        return nameList.get(count);
    }
    
    public int getStudentScore(int count){
        return scoreList.get(count);
    }
    
    public void setScoreRange(int low,  int high){
        this.lowScore = low;
        this.highScore = high;
    }
    
    public String checkStudentScore(int score){
        boolean result = false;
        int count = 0;
        while ( count < numberOfDes-1 && result == false){
            int cutScore = this.scoreRange.get(count);
           if (score < cutScore){
               result = true;
               return descriptionList.get(count);
           }
            count++;
        }
        return descriptionList.get(count); 
    }
    
    public void setNumberOfDes(int inNumberOfDes){
        this.numberOfDes = inNumberOfDes;
    }
    
    public int getNumberOfDes(){
        return this.numberOfDes;
    }
    
    public void insertCutPoint(int cutPoint){
        this.scoreRange.add(cutPoint);
    }
    
    public int getScore(int pos){
        return this.scoreRange.get(pos);
    }
    
    public void insertDescription(String description){
        this.descriptionList.add(description);
    }
    
    public String getDescription(int pos){
        return this.descriptionList.get(pos);
    }

}
