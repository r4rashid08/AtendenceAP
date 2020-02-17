
package com.example.mis_internee.atendence_app_android.Model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResult implements Serializable {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("PASSWORD")
    @Expose
    private String pASSWORD;
    @SerializedName("USERNAME")
    @Expose
    private String uSERNAME;
    @SerializedName("EMP_NAME")
    @Expose
    private String eMPNAME;
    @SerializedName("IS_HOD")
    @Expose
    private String iSHOD;
    @SerializedName("IS_MANAGER")
    @Expose
    private String iSMANAGER;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPASSWORD() {
        return pASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getEMPNAME() {
        return eMPNAME;
    }

    public void setEMPNAME(String eMPNAME) {
        this.eMPNAME = eMPNAME;
    }

    public String getISHOD() {
        return iSHOD;
    }

    public void setISHOD(String iSHOD) {
        this.iSHOD = iSHOD;
    }

    public String getISMANAGER() {
        return iSMANAGER;
    }

    public void setISMANAGER(String iSMANAGER) {
        this.iSMANAGER = iSMANAGER;
    }

}
