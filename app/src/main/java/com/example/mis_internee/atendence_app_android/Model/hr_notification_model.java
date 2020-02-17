package com.example.mis_internee.atendence_app_android.Model;


/**
 * Created by MIS-Internee on 24-Jul-18.
 */

public class hr_notification_model {
    String leave_date;
    String leave_desc;
    String remarks;
    String hr_date;
    String[] list;
    String[] leave_descc;
    String[] leave_idd;
    String[] leave_codee;
    String[] bal;
    //constructor


    public hr_notification_model(String s, String s1, String hr_remark, String s2) {
        this.leave_date = s;
        this.leave_desc = s1;
        this.remarks = hr_remark;
        this.hr_date=s2;

    }

    //getters


    public String getLeave_date() {
        return this.leave_date;
    }

    public String getLeave_desc() {
        return this.leave_desc;
    }

    public String getRemarks() {
        return this.remarks;
    }
    public String getHr_date() {
        return this.hr_date;
    }


}