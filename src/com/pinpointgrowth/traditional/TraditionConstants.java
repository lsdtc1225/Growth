package com.pinpointgrowth.traditional;

public class TraditionConstants {
    
    public static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Pinpoint";
    public static final String DATABASE_USERNAME = "growth";
    public static final String DATABASE_PASSWORD = "growth";
    public static final String PREVIOUS_AUTO_INCREMENT_VALUE = "SELECT LAST_INSERT_ID();";
    public static final String GET_ALL_RUBRICS = "SELECT * FROM Pinpoint.Rubric;";
    public static final String GET_ALL_DISTRICTS = "SELECT * FROM Pinpoint.Districts ORDER BY DistrictName;";
    
    
}
