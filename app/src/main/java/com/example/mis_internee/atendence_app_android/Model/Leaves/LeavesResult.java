
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LeavesResult implements Serializable {

    @SerializedName("LEAVESTATUS")
    @Expose
    private String lEAVESTATUS;

    public String getLEAVESTATUS() {
        return lEAVESTATUS;
    }

    public void setLEAVESTATUS(String lEAVESTATUS) {
        this.lEAVESTATUS = lEAVESTATUS;
    }

}
