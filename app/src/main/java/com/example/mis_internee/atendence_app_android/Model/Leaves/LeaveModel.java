
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveModel implements Serializable {

    @SerializedName("Result")
    @Expose
    private List<LeavesResult> leavesResult = null;

    public List<LeavesResult> getLeavesResult() {
        return leavesResult;
    }

    public void setLeavesResult(List<LeavesResult> leavesResult) {
        this.leavesResult = leavesResult;
    }

}
