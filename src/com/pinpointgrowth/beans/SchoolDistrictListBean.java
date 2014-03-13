package com.pinpointgrowth.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pinpointgrowth.DTO.DistrictDTO;
import com.pinpointgrowth.constants.Constants;

public class SchoolDistrictListBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8553609911398691295L;
    private List<DistrictDTO> districtList;

    public List<DistrictDTO> getDistrictList() throws ClassNotFoundException,
            SQLException {
        if (districtList == null) {
            districtList = new ArrayList<DistrictDTO>();
            Class.forName(Constants.JDBC_DRIVER_CLASS);
            Connection con = DriverManager.getConnection(
                    Constants.DATABASE_URL, Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            Statement statement = con.createStatement();
            ResultSet districts = statement
                    .executeQuery(Constants.GET_ALL_DISTRICTS);
            while (districts.next()) {
                String districtName = districts.getString(districts
                        .findColumn("DistrictName"));
                DistrictDTO districtDTO = new DistrictDTO();
                districtDTO.setDistrictName(districtName);
                districtList.add(districtDTO);
            }

            statement.close();
            con.close();
            Collections.sort(districtList);
        }
        return districtList;
    }

    public void setDistrictList(List<DistrictDTO> districtList) {
        this.districtList = districtList;
    }
}
