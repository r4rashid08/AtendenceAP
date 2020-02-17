
package com.example.mis_internee.atendence_app_android.Model.CurrentDate;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentDataInfo {

    @SerializedName("Result")
    @Expose
    private List<CurrentDateModel> result = null;

    public List<CurrentDateModel> getResult() {
        return result;
    }

    public void setResult(List<CurrentDateModel> result) {
        this.result = result;
    }

}
