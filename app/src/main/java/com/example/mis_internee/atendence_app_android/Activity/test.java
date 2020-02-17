package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mis_internee.atendence_app_android.R;

/**
 * Created by MIS-Internee on 03-Jan-18.
 */

public class test extends Activity {
    Button btn;
    int val;
    TextView tvDate;
    String EmployeUrl;
String [] datwa={"Delete","Edit"};
ListView lv;
ArrayAdapter arrayAdapter;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = (Button) findViewById(R.id.subIssue);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        lv=(ListView)findViewById(R.id.lv);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .2));
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, datwa);
        lv.setAdapter(arrayAdapter);

    }
}
