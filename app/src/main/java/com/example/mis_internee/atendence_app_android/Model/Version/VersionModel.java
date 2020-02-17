
package com.example.mis_internee.atendence_app_android.Model.Version;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionModel implements Serializable {

    @SerializedName("VersionResult")
    @Expose
    private List<VersionResult> versionResult = null;

    public List<VersionResult> getVersionResult() {
        return versionResult;
    }

    public void setVersionResult(List<VersionResult> versionResult) {
        this.versionResult = versionResult;
    }

}
