
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LeaveInfo implements Serializable {

    @SerializedName("LEAVE_CODE")
    @Expose
    private String lEAVECODE;
    @SerializedName("LEAVE_DESC")
    @Expose
    private String lEAVEDESC;

    public String getLEAVECODE() {
        return lEAVECODE;
    }

    public void setLEAVECODE(String lEAVECODE) {
        this.lEAVECODE = lEAVECODE;
    }

    public String getLEAVEDESC() {
        return lEAVEDESC;
    }

    public void setLEAVEDESC(String lEAVEDESC) {
        this.lEAVEDESC = lEAVEDESC;
    }

}
