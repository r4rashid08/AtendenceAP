package com.example.mis_internee.atendence_app_android.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mis_internee.atendence_app_android.R;

import java.util.ArrayList;

public class Employee_Calendar_Sub_Details extends AppCompatActivity {
    TextView EMP_ID,EMP_NAME,EMP_CODE,DEP_NAME,DESIGTYPE,ACHKIN,ACHKOUT,FLAG;
    private ArrayList<String> LEAVE_DESC;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
    private ArrayList<String> empCode;
    private ArrayList<String> depname;
    private ArrayList<String> desigtype;
    private ArrayList<String> SHIFT_CODE;
    private ArrayList<String> DESIG_TYPE;
    private ArrayList<String> DEP_ID;
    private ArrayList<String> DESIG_ID;
    private ArrayList<String> achkin;
    private ArrayList<String> achkout;
    private ArrayList<String> flag;
    private ArrayList<String> empname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__calendar__sub__details);
        EMP_ID=(TextView)findViewById(R.id.tv_id);
        EMP_NAME=(TextView)findViewById(R.id.tv_name);
        DESIGTYPE=(TextView)findViewById(R.id.tv_desig);
        DEP_NAME=(TextView)findViewById(R.id.tv_depart);
        ACHKIN=(TextView)findViewById(R.id.tv_arrTime);
        ACHKOUT=(TextView)findViewById(R.id.tv_LeaveTime);
        FLAG=(TextView)findViewById(R.id.tv_status);
       String empid = getIntent().getExtras().getString("EMP_ID");


        final ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Employee One Day details");


        if(getIntent().getExtras()!=null) {

            empname = getIntent().getExtras().getStringArrayList("EMP_NAME");
            desigtype = getIntent().getExtras().getStringArrayList("DESIG_TYPE");
            depname = getIntent().getExtras().getStringArrayList("DEP_NAME");
            achkin = getIntent().getExtras().getStringArrayList("ACHKIN_TIME");
            achkout = getIntent().getExtras().getStringArrayList("ACHKOUT_TIME");
            flag = getIntent().getExtras().getStringArrayList("ATTND_FLAG");
        }
        EMP_ID.setText(empid);
        EMP_NAME.setText(empname.toString());
        DESIGTYPE.setText(desigtype.toString());
        DEP_NAME.setText(depname.toString());
        ACHKIN.setText(achkin.toString());
        ACHKOUT.setText(achkout.toString());
        FLAG.setText(flag.toString());




    }
}
