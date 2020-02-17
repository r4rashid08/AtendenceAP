
package com.example.mis_internee.atendence_app_android.Model.Leaves;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveCode implements Serializable {

    @SerializedName("LeaveInfo")
    @Expose
    private List<LeaveInfo> leaveInfo = null;

    public List<LeaveInfo> getLeaveInfo() {
        return leaveInfo;
    }

    public void setLeaveInfo(List<LeaveInfo> leaveInfo) {
        this.leaveInfo = leaveInfo;
    }

}
