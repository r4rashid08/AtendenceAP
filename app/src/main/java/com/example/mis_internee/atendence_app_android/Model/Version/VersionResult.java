
package com.example.mis_internee.atendence_app_android.Model.Version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VersionResult  implements Serializable {

    @SerializedName("VERSION")
    @Expose
    private String vERSION;

    public String getVERSION() {
        return vERSION;
    }

    public void setVERSION(String vERSION) {
        this.vERSION = vERSION;
    }

}
