
package com.example.mis_internee.atendence_app_android.Model.CurrentDate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentDateModel {

    @SerializedName("ALLOW_LEAVE_DAYS")
    @Expose
    private String aLLOWLEAVEDAYS;
    @SerializedName("UNIT_CODE")
    @Expose
    private String uNITCODE;
    @SerializedName("ACHKIN_DATE")
    @Expose
    private String aCHKINDATE;
    @SerializedName("ACHKIN_TIME")
    @Expose
    private String aCHKINTIME;
    @SerializedName("ACHKOUT_TIME")
    @Expose
    private String aCHKOUTTIME;
    @SerializedName("ATTND_FLAG")
    @Expose
    private String aTTNDFLAG;
    @SerializedName("DAY")
    @Expose
    private String dAY;

    public String getALLOWLEAVEDAYS() {
        return aLLOWLEAVEDAYS;
    }

    public void setALLOWLEAVEDAYS(String aLLOWLEAVEDAYS) {
        this.aLLOWLEAVEDAYS = aLLOWLEAVEDAYS;
    }

    public String getUNITCODE() {
        return uNITCODE;
    }

    public void setUNITCODE(String uNITCODE) {
        this.uNITCODE = uNITCODE;
    }

    public String getACHKINDATE() {
        return aCHKINDATE;
    }

    public void setACHKINDATE(String aCHKINDATE) {
        this.aCHKINDATE = aCHKINDATE;
    }

    public String getACHKINTIME() {
        return aCHKINTIME;
    }

    public void setACHKINTIME(String aCHKINTIME) {
        this.aCHKINTIME = aCHKINTIME;
    }

    public String getACHKOUTTIME() {
        return aCHKOUTTIME;
    }

    public void setACHKOUTTIME(String aCHKOUTTIME) {
        this.aCHKOUTTIME = aCHKOUTTIME;
    }

    public String getATTNDFLAG() {
        return aTTNDFLAG;
    }

    public void setATTNDFLAG(String aTTNDFLAG) {
        this.aTTNDFLAG = aTTNDFLAG;
    }

    public String getDAY() {
        return dAY;
    }

    public void setDAY(String dAY) {
        this.dAY = dAY;
    }

}
