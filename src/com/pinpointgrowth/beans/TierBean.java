package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pinpointgrowth.DTO.StudentDTO;
import com.pinpointgrowth.constants.Constants;

public class TierBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6118757575994847058L;
    private List<List<StudentDTO>> tiers;
    private int totalStudents;
    private int courseID;
    private List<String> colors;
    private List<Double> percents;

    public boolean addPercent(Double percent) {
        if (percents == null) {
            percents = new ArrayList<Double>();
        }
        percents.add(percent);
        return true;
    }

    public boolean clearPercents() {
        if (percents != null) {
            percents.clear();
        }
        return true;
    }

    public void savePercents() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (int i = 1; i <= tiers.size(); i++) {
            for (StudentDTO student : tiers.get(i - 1)) {
                Statement statement = con.createStatement();
                double target;
                if (percents != null) {
                    target = percents.get(i - 1);
                } else {
                    target = 0.0;
                }
                statement.executeUpdate(Constants.SAVE_GROWTH_TARGET(
                        student.getStudentID(), courseID, target));
            }
        }
    }

    public double getPercent(int index) {
        if (percents != null) {
            return percents.get(index);
        } else {
            return 0.0;
        }
    }

    public List<List<StudentDTO>> getTiers() {
        return tiers;
    }

    public void setTiers(List<List<StudentDTO>> tiers) {
        this.tiers = tiers;
    }

    public int getTotalStudents() {
        totalStudents = 0;
        for (List<StudentDTO> list : tiers) {
            totalStudents += list.size();
        }
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public boolean saveTiers() throws ClassNotFoundException, SQLException {
        Class.forName(Constants.JDBC_DRIVER_CLASS);
        Connection con = DriverManager.getConnection(Constants.DATABASE_URL,
                Constants.DATABASE_USERNAME, Constants.DATABASE_PASSWORD);

        for (int i = 1; i <= tiers.size(); i++) {
            for (StudentDTO student : tiers.get(i - 1)) {
                Statement statement = con.createStatement();
                statement.executeUpdate(Constants.UPDATE_TIER(i,
                        student.getStudentID(), courseID));
            }
        }
        return true;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public List<String> getColors() {
        if (colors == null) {
            colors = new ArrayList<String>();
            if (tiers.size() == 2) {
                colors.add("#83ff31");
                colors.add("#fd3f3f");
            } else if (tiers.size() == 3) {
                colors.add("#83ff31");
                colors.add("#fdff7c");
                colors.add("#fd3f3f");
            } else if (tiers.size() == 4) {
                colors.add("#83ff31");
                colors.add("#7cb9ff");
                colors.add("#ffcd7c");
                colors.add("#fd3f3f");

            } else if (tiers.size() == 5) {
                colors.add("#83ff31");
                colors.add("#7cb9ff");
                colors.add("#fdff7c");
                colors.add("#ffcd7c");
                colors.add("#fd3f3f");
            } else if (tiers.size() == 6) {
                colors.add("#83ff31");
                colors.add("#7cffff");
                colors.add("#7cb9ff");
                colors.add("#fdff7c");
                colors.add("#ffcd7c");
                colors.add("#fd3f3f");
            }
        }
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
