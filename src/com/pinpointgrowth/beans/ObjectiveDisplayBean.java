package com.pinpointgrowth.beans;

import java.util.List;

import com.pinpointgrowth.DTO.ObjectiveDTO;

public class ObjectiveDisplayBean implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1559261208762065111L;
    private List<ObjectiveDTO> objectiveList;
    public List<ObjectiveDTO> getObjectiveList() {
        return objectiveList;
    }
    public void setObjectiveList(List<ObjectiveDTO> objectiveList) {
        this.objectiveList = objectiveList;
    }

}
