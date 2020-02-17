
package com.example.mis_internee.atendence_app_android.Model.LeavesDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeavesDetail {

    @SerializedName("Result")
    @Expose
    private List<LeavesDetailResult> result = null;

    public List<LeavesDetailResult> getResult() {
        return result;
    }

    public void setResult(List<LeavesDetailResult> result) {
        this.result = result;
    }

}
