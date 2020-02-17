package com.example.mis_internee.atendence_app_android.Activity;


import android.content.Intent;
import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.ListView;

import com.example.mis_internee.atendence_app_android.Adapter.CustomListAdapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class EmployeeAtendence extends AppCompatActivity {
    private static  final String TAG="EmployeeAtendence";
    private CalendarView mCalendar;
    ListView lv;
    int pos=0;
    Integer[] imgid;
    List<String> LISTSTRING;
    String emp_id;
    String[] listValue = new String[] {"Employee Calendar","Application Pending List"};
    Button Emp_app,Emp_attnd,Add_manager,Emp_list,Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_atendence);
        lv=(ListView)findViewById(R.id.menuList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Employee Menu");
        User user= new User(this);
        emp_id=user.getEmp_id();
//        emp_id=getIntent().getExtras().getString("EMP_ID");
        LISTSTRING = new ArrayList<String>(Arrays.asList(listValue));
        imgid= new Integer[]{
                R.drawable.cap,
                R.drawable.leave,
                R.drawable.addmngr,
//                R.drawable.emplistt,
//                R.drawable.default12,



        };
        CustomListAdapter adapter=new CustomListAdapter(this, listValue, imgid);
//        list=(ListView)findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position+1;
                if(pos==1){
                    Intent i = new Intent(EmployeeAtendence.this,Employee_Calendar_Admin.class);
                    i.putExtra("emp_id",emp_id);
                    startActivity(i);
                }
                if(pos==2){
                    Intent i = new Intent(EmployeeAtendence.this,Emp_notification.class);
                    startActivity(i);
                }
//                if(pos==3){
//                    Intent i = new Intent(EmployeeAtendence.this,Manager_List.class);
//                    startActivity(i);
//                }
                if(pos==4){
//                    Intent i = new Intent(EmployeeAtendence.this,EmployeeAttendenceList_Admin.class);
//                    startActivity(i);
                }
//                if(pos==5){
//                    Intent i = new Intent(EmployeeAtendence.this,testAct.class);
//                    startActivity(i);
//                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
