package com.pinpointgrowth.traditionalConstants;

public class TraditionalConstants {
    
    //used in PreTest.java
    public static String ADD_SETUP_FOR_PRE_TEST_SQL(int cID, int cutScore, String Description, int ScoreLevel){
        return "INSERT INTO Pinpoint.PreTest(C_ID, CutScore, Description, ScoreLevel) VALUES ("
                + cID + "," + cutScore + "," + '"' + Description + '"' + "," + ScoreLevel + ");";
    }

    public static String CHECK_SETUP_FOR_PRE_TEST_SQL(int cID){
        return "SELECT * FROM Pinpoint.PreTest WHERE C_ID = " + cID + ';';
    }

    public static String DELETE_SETUP_FOR_PRE_TEST_SQL(int cID){
        return "DELETE FROM Pinpoint.PreTest WHERE C_ID = " + cID + ';';
    }

    // used in PreTestScore.java
    public static String ADD_PRE_TEST_SCORE_SQL(int cID, int sID, int preTestScore){
        return "INSERT INTO Pinpoint.StudentScore(C_ID, S_ID, PreGradeTrad) VALUES("
                + cID + "," + sID + "," + preTestScore + ");";
    }

    public static String CHECK_PRE_SCORE_RECORD_SQL(int cID, int sID){
        return "SELECT * FROM Pinpoint.StudentScore WHERE C_ID = " + cID + " AND S_ID = " + sID + ';';
    }

    public static String UPDATE_PRE_TEST_SCORE_SQL(int cID, int sID, int preTestScore){
        return "UPDATE Pinpoint.StudentScore SET PreGradeTrad = " + preTestScore + " WHERE C_ID = " + cID + " AND S_ID = " + sID + ';';
    }

    // used in PostTest.java
    public static String ADD_SETUP_FOR_POST_TEST_SQL(int cID, int cutScore, String Description, int ScoreLevel){
        return "INSERT INTO Pinpoint.PostTest(C_ID, CutScore, Description, ScoreLevel) VALUES ("
                + cID + "," + cutScore + "," + '"' + Description + '"' + "," + ScoreLevel + ");";
    }

    public static String CHECK_SETUP_FOR_POST_TEST_SQL(int cID){
        return "SELECT *  FROM Pinpoint.PostTest WHERE C_ID = " + cID + ';';
    }

    public static String DELETE_SETUP_FOR_POST_TEST_SQL(int cID){
        return "DELETE FROM Pinpoint.PostTest WHERE C_ID = " + cID + ';';
    }

    // used in PostTestScore.java
    public static String UPDATE_POST_TEST_SCORE_SQL(int cID, int sID, int postTestScore){
        return "UPDATE Pinpoint.StudentScore SET PostGradeTrad = " + postTestScore + " WHERE C_ID = " + cID + " AND S_ID = " + sID + ';';
    }

    // used in TraditionalEvaluation.java 
    // @ setHighestScore()
    public static String PRE_HIGHEST_SCORE_SQL(int cID){
        return "SELECT MAX(PreGradeTrad) AS PreHighest FROM Pinpoint.StudentScore WHERE C_ID = " + cID + ';';
    }

    public static String POST_HIGHEST_SCORE_SQL(int cID){
        return "SELECT MAX(PostGradeTrad) AS PostHighest FROM Pinpoint.StudentScore WHERE C_ID = " + cID + ';';
    }

    // @ setCutScore()
    public static String SET_PRE_CUT_SCORE_SQL(int cID){
        return "SELECT CutScore FROM Pinpoint.PreTest WHERE C_ID = " + cID + ';';
    }

    public static String SET_POST_CUT_SCORE_SQL(int cID){
        return "SELECT CutScore FROM Pinpoint.PostTest WHERE C_ID = " + cID + ';';
    }

    // @ 
    public static String STUDENT_NAME_AND_SCORE_SQL(int cID){
        return "SELECT  SFName, SLName, PreGradeTrad, PostGradeTrad FROM Pinpoint.Student S, Pinpoint.StudentScore SS WHERE SS.S_ID = S.S_ID AND SS.C_ID = " + cID + ';';
    }
}





















