
package com.example.mis_internee.atendence_app_android.Model.FcmModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcmResult {

    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
