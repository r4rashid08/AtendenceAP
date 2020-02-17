package com.example.mis_internee.atendence_app_android.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mis_internee.atendence_app_android.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddEvent_Admin extends AppCompatActivity {
    Spinner spin;
    Button btn;
    String selectedItemPosition, SPIN, insertUrl="http://scorpio.sgroup.pk:8085/atendence/updateEmpApp.php";
    RequestQueue requestQueue;
    TextView Name,Date,dep,TV,Remarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event__admin);
    }}