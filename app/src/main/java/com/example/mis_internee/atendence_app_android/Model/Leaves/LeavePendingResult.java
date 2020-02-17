
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeavePendingResult implements Serializable {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

}
