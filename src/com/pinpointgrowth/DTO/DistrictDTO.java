package com.pinpointgrowth.DTO;

public class DistrictDTO implements Comparable<DistrictDTO> {
    private String districtName;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public int compareTo(DistrictDTO o) {
        return this.districtName.compareTo(o.districtName);
    }
}
