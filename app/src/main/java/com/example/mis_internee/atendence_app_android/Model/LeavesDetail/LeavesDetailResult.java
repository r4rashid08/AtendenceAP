
package com.example.mis_internee.atendence_app_android.Model.LeavesDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeavesDetailResult {

    @SerializedName("EMP_ID")
    @Expose
    private String eMPID;
    @SerializedName("LEAVE_ID")
    @Expose
    private String lEAVEID;
    @SerializedName("LEAVE_DESC")
    @Expose
    private String lEAVEDESC;
    @SerializedName("LEAVE_CODE")
    @Expose
    private String lEAVECODE;
    @SerializedName("LEAVES")
    @Expose
    private String lEAVES;

    public String getEMPID() {
        return eMPID;
    }

    public void setEMPID(String eMPID) {
        this.eMPID = eMPID;
    }

    public String getLEAVEID() {
        return lEAVEID;
    }

    public void setLEAVEID(String lEAVEID) {
        this.lEAVEID = lEAVEID;
    }

    public String getLEAVEDESC() {
        return lEAVEDESC;
    }

    public void setLEAVEDESC(String lEAVEDESC) {
        this.lEAVEDESC = lEAVEDESC;
    }

    public String getLEAVECODE() {
        return lEAVECODE;
    }

    public void setLEAVECODE(String lEAVECODE) {
        this.lEAVECODE = lEAVECODE;
    }

    public String getLEAVES() {
        return lEAVES;
    }

    public void setLEAVES(String lEAVES) {
        this.lEAVES = lEAVES;
    }

}
