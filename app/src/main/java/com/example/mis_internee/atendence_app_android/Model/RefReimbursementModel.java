
package com.example.mis_internee.atendence_app_android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefReimbursementModel implements Serializable {

    @SerializedName("LeavesResult")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
