package com.pinpointgrowth.constants;

public class Constants {

    public static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Pinpoint";
    public static final String DATABASE_USERNAME = "growth";
    public static final String DATABASE_PASSWORD = "growth";
    public static final String PREVIOUS_AUTO_INCREMENT_VALUE = "SELECT LAST_INSERT_ID();";
    public static final String GET_ALL_RUBRICS = "SELECT * FROM Pinpoint.Rubric;";
    public static final String GET_ALL_DISTRICTS = "SELECT * FROM Pinpoint.Districts ORDER BY DistrictName;";

    public static String TEACHER_ID_QUERY(String userName) {
        return "SELECT T_ID FROM Pinpoint.Instructor WHERE Login = " + '"'
                + userName + '"' + ";";
    }

    public static String COURSE_ADD_SQL(int teacherID, String term,
            String className, String CourseLength) {
        return "INSERT INTO Pinpoint.Classes (T_ID, Term, CName, CourseLength, Locked) VALUES ("
                + teacherID
                + ","
                + '"'
                + term
                + '"'
                + ","
                + '"'
                + className
                + '"' + "," + '"' + CourseLength + '"' + "," + "'N'" + ");";
    }

    public static String GET_COURSE_ID_SQL(int teacherID, String term,
            String className, String CourseLength) {
        return "SELECT C_ID FROM Pinpoint.Classes WHERE T_ID = " + teacherID
                + " AND Term = " + '"' + term + '"' + " AND CName = " + '"'
                + className + '"' + " AND CourseLength = " + '"' + CourseLength
                + '"' + " ORDER BY C_ID;";
    }

    public static String STUDENT_ADD_SQL(String firstName, String lastName,
            int grade) {
        return "INSERT INTO Pinpoint.Student (SFName, SLName, GradeLevel) VALUES ("
                + '"'
                + firstName
                + '"'
                + ","
                + '"'
                + lastName
                + '"'
                + ","
                + grade + ");";
    }

    public static String GET_STUDENT_ID_SQL(String firstName, String lastName,
            int grade) {
        return "SELECT S_ID FROM Pinpoint.Student WHERE SFName = " + '"'
                + firstName + '"' + " AND SLName = " + '"' + lastName + '"'
                + " AND GradeLevel = " + grade + " ORDER BY S_ID;";
    }

    public static String TAKING_ADD_SQL(int studentID, int courseID) {
        return "INSERT INTO Pinpoint.Enrolled (S_ID, C_ID) VALUES ("
                + studentID + ',' + courseID + ");";
    }

    public static String EMAIL_TAKEN_QUERY(String emailAddress) {
        return "SELECT * FROM Pinpoint.Instructor WHERE Email = " + '"'
                + emailAddress + '"' + ';';
    }

    public static String ADD_RUBRIC_TO_TEACHER(int r_id, int t_id) {
        return "INSERT INTO Pinpoint.TeachRubLookup (R_ID,T_ID) VALUES ("
                + r_id + ',' + t_id + ");";
    }

    public static String GET_ALL_RUBRICS_WITH_NAME(String rubricName) {

        return "SELECT * FROM Pinpoint.Rubric WHERE RName = " + '"'
                + rubricName + '"' + ';';
    }

    public static String ADD_NEW_PRESCORE_RUBRIC_SQL(String rubricName) {
        return "Insert into Pinpoint.Rubric (RName,PostScore) values (" + '"'
                + rubricName + '"' + ",'N'" + ");";
    }

    public static String ADD_OBJECTIVE_FROM_RUBRIC(String name, String desc,
            int minScore, int maxScore, int weight) {
        return "INSERT INTO Pinpoint.Objective (OName,Description,Weight,MinScore,MaxScore,StudentSpec) VALUES ("
                + '"'
                + name
                + '"'
                + ','
                + '"'
                + desc
                + '"'
                + ','
                + weight
                + ',' + minScore + ',' + maxScore + ',' + 0 + ");";
    }

    public static String GET_OBJECTIVE(String name, String desc, int minScore,
            int maxScore, int weight) {

        return "SELECT * FROM Pinpoint.Objective WHERE OName = " + '"' + name
                + '"' + " AND Weight = " + weight + " AND MinScore = "
                + minScore + " AND MaxScore = " + maxScore + ";";
    }

    public static String ADD_OBJECTIVE_TO_RUBRIC(int o_id, int r_id) {
        return "Insert into Pinpoint.RubObjLookup (O_ID,R_ID) VALUES (" + o_id
                + ',' + r_id + ");";
    }

    public static String GET_RUBRIC_IDS_FOR_TEACHER(int teacherID) {
        return "SELECT * FROM Pinpoint.TeachRubLookup WHERE T_ID = "
                + teacherID + ';';
    }

    public static String GET_COURSE(int courseID) {
        return "Select * from Pinpoint.Classes WHERE C_ID = " + courseID + ';';
    }

    public static String GET_RUBRIC(int rubricID) {
        return "Select * from Pinpoint.Rubric WHERE R_ID = " + rubricID + ';';
    }

    public static String GET_COURSES_FOR_TEACHER(int teacherID) {
        return "Select * from Pinpoint.Classes WHERE T_ID = " + teacherID + ';';
    }

    public static String GET_OBJECTIVES_FOR_RUBRIC(int rubricID) {
        return "Select * from Pinpoint.RubObjLookup WHERE R_ID = " + rubricID
                + ';';
    }

    public static String GET_STUDENTS_FOR_COURSE(int courseID) {
        return "Select * from Pinpoint.Enrolled WHERE C_ID = " + courseID + ';';
    }

    public static String INSERT_STUOBJLOOKUP(Integer studentID,
            Integer objectiveID) {
        return "INSERT INTO Pinpoint.StudObjLookup (S_ID,O_ID) VALUES ("
                + studentID + ',' + objectiveID + ");";
    }

    public static String ADD_ASSIGNMENT(String assignmentName, int rubricID,
            int courseID) {
        return "INSERT INTO Pinpoint.Assignment (R_ID,AName,C_ID) VALUES ("
                + rubricID + ',' + '"' + assignmentName + '"' + ',' + courseID
                + ");";
    }

    public static String GET_ALL_ASSIGNMENTS_FOR_COURSE(int courseID) {
        return "SELECT * FROM Pinpoint.Assignment WHERE C_ID = " + courseID
                + ';';
    }

    public static String GET_STUDENT(int studentID) {
        return "SELECT * FROM Pinpoint.Student WHERE S_ID = " + studentID + ';';
    }

    public static String GET_ASSIGNMENT(int assignmentID) {
        return "SELECT * FROM Pinpoint.Assignment WHERE A_ID = " + assignmentID
                + ';';
    }

    public static String GET_OBJECTIVE(int objectiveID) {
        return "SELECT * FROM Pinpoint.Objective WHERE O_ID = " + objectiveID
                + ';';
    }

    public static String SAVE_PERFORMANCE_SCORES(int studentID, Integer oID,
            int score) {
        return "INSERT INTO Pinpoint.StudObjLookup (O_ID,S_ID,PreGradePerf) VALUES ("
                + oID + ',' + studentID + ',' + score + ");";
    }

    public static String GET_STUDENT_FROM_STUOBJLOOKUP(int studentID,
            Integer oID) {
        return "SELECT * FROM Pinpoint.StudObjLookup WHERE S_ID = " + studentID
                + " AND O_ID = " + oID + ';';
    }

    public static String GET_STUOBJLOOKUP(int studentID, int objectiveID) {
        return "SELECT * FROM Pinpoint.StudObjLookup WHERE S_ID = " + studentID
                + " AND O_ID = " + objectiveID + ';';
    }

    public static String UPDATE_PERFORMANCE_SCORE(int studentID,
            Integer objectiveID, int score) {
        return "UPDATE Pinpoint.StudObjLookup SET PreGradePerf = " + score
                + " WHERE S_ID = " + studentID + " AND O_ID = " + objectiveID
                + ';';
    }

    public static String UPDATE_TIER(int tier, int studentID, int courseID) {
        return "UPDATE Pinpoint.Enrolled SET Tier = " + tier + " WHERE S_ID = "
                + studentID + " AND C_ID = " + courseID + ";";
    }

    public static String SAVE_GROWTH_TARGET(int studentID, int courseID,
            Double target) {
        return "UPDATE Pinpoint.Enrolled SET GrowthTarget = " + target
                + " WHERE S_ID = " + studentID + " AND C_ID = " + courseID
                + ";";
    }

    public static String ADD_NEW_POSTSCORE_RUBRIC_SQL(String rubricName) {
        return "Insert into Pinpoint.Rubric (RName,PostScore) values (" + '"'
                + rubricName + '"' + ",'Y'" + ");";
    }

    public static String GET_ENROLLED(int studentID, int courseID) {
        return "SELECT * FROM Pinpoint.Enrolled WHERE S_ID = " + studentID
                + " AND C_ID = " + courseID + ";";
    }

    public static String ADD_EXTENSION(int courseID, String description) {
        return "INSERT INTO Pinpoint.ExtensionActivity (C_ID,ExtensionDescription) VALUES ("
                + courseID + "," + "'" + description + "'" + ");";
    }

    public static String Lock_SCORES(int courseID) {
        return "UPDATE Pinpoint.Classes SET Locked = 'Y' WHERE C_ID = "
                + courseID + ";";
    }

    public static String GET_STUDOBJLOOKUP_FOR_OID(int objectiveID) {
        return "SELECT * FROM Pinpoint.StudObjLookup WHERE O_ID = "
                + objectiveID + ";";
    }

    public static String REMOVE_STUDENT_FROM_ENROLLED(int studentID) {
        return "DELETE FROM Pinpoint.Enrolled WHERE S_ID = " + studentID;
    }

    public static String REMOVE_STUDENT_FROM_STUDOBJLOOKUP(int studentID) {
        return "DELETE FROM Pinpoint.StudObjLookup WHERE S_ID = " + studentID;
    }

    public static String REMOVE_STUDENT_FROM_STUDENT(int studentID) {
        return "DELETE FROM Pinpoint.Student WHERE S_ID = " + studentID;
    }

    public static String GET_EXTENSION_ACTIVITY_FOR_COURSE(int courseID) {
        return "SELECT * FROM Pinpoint.ExtensionActivity WHERE C_ID = "
                + courseID + ";";
    }

    public static String UPDATE_EXTENSION_MET_TRUE(int studentID, int courseID) {
        return "UPDATE Pinpoint.Enrolled SET ExtensionMet = 'Y' WHERE S_ID = "
                + studentID + " AND C_ID = " + courseID + ";";
    }

    public static String UPDATE_EXTENSION_MET_FALSE(int studentID, int courseID) {
        return "UPDATE Pinpoint.Enrolled SET ExtensionMet = 'N' WHERE S_ID = "
                + studentID + " AND C_ID = " + courseID + ";";
    }

    public static String GET_DISTRICT_WITH_NAME(String schoolDistrict) {
        return "SELECT * FROM Pinpoint.Districts WHERE DistrictName = " + '"'
                + schoolDistrict + '"' + ";";
    }

}
