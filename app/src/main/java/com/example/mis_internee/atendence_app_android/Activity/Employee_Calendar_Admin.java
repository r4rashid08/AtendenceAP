package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.Adapter.EMPLOYEE_CALENDAR_ADMIN_ADAPTER;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Employee_Calendar_Admin extends AppCompatActivity {

    CalendarView cal;
    ListView lv;
    ArrayList<String> name,emp_code,dep_code,desig_code,shift_code,dep_id,desig_type,desig_id,achkin,achkout,flag=new ArrayList<>();
    String Emp_id,  EmployeUrl;
    private int day, month, year;
    private Calendar c;
    Integer[] imgid;

    String[] emp_id,ACHKIN_TIME;
    private ProgressDialog progressdialog;
    private int conter = 0;
    String Empid;

    String dae;
    private SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");

    private static final String TAG = "Employee_Calendar_Admin";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private boolean shouldShow = false;
    private ActionBar toolbar;
    String [] EMP;

    CompactCalendarView compactCalendarView;
    final List<String> mutableBookings = new ArrayList<>();

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__calendar__admin);
        lv=(ListView)findViewById(R.id.empList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Emp_id=getIntent().getExtras().getString("EMP_ID");
//        EmployeUrl="http://scorpio.sgroup.pk:8085/atendence/pres_absnt_emp.php"+ "?date="+"08-Feb-2018" ;
        c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        dae = day + "-" + month + "-" + year;



        compactCalendarView = (CompactCalendarView) findViewById(R.id.calendar);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                dae=dateFormatForDisplaying.format(dateClicked);
                Toast.makeText(Employee_Calendar_Admin.this, dae, Toast.LENGTH_SHORT).show();
                User user=new User(Employee_Calendar_Admin.this);
                String adminEmp_id=user.getEmp_id().toString();
                getJSON("http://scorpio.sgroup.pk:8085/atendence/pres_absnt_emp.php?date="+dae+"&EMP_ID="+adminEmp_id);
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(Employee_Calendar_Admin.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });





}
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                progressdialog.hide();

                    try {

                        loadIntoListView(s);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("ERROR", "Error pasting data "+e.toString());
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);
            final JSONArray jsonArray = object.getJSONArray("Result");
            final String[] EMP_NAME = new String[jsonArray.length()];
            ACHKIN_TIME = new String[jsonArray.length()];
            emp_id = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;

                try {
                    obj = jsonArray.getJSONObject(i);
                    EMP_NAME[i] = (i + 1) + "      " + obj.getString("EMP_NAME") + "      " + obj.getString("ACHKIN_TIME");
                    emp_id[i] = obj.getString("EMP_ID");
                    ACHKIN_TIME[i] = obj.getString("ACHKIN_TIME");
                    Empid = emp_id[i].toString();
//                if(ACHKIN_TIME[i]=="null"){
//                    lv.setBackgroundResource(R.drawable.red);
//                }


                } catch (JSONException e) {
                    Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
//        lv.setAdapter(itemsAdapter);
            EMPLOYEE_CALENDAR_ADMIN_ADAPTER adapter = new EMPLOYEE_CALENDAR_ADMIN_ADAPTER(this, EMP_NAME, ACHKIN_TIME);
            lv.setAdapter(adapter);
        }
        catch (NullPointerException e){
            AlertDialog alertDialog = new AlertDialog.Builder(
                    Employee_Calendar_Admin.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Network Error...!!!");

            // Setting Dialog Message
            alertDialog.setMessage("Please check your internet");

            // Setting Icon to Dialog
//            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(Employee_Calendar_Admin.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                }
                String empId= emp_id[position].toString();

                Toast.makeText(Employee_Calendar_Admin.this, empId, Toast.LENGTH_SHORT).show();
                Intent i = new Intent (Employee_Calendar_Admin.this,Admin_Employee_Attendance.class);
                i.putExtra("EMP_ID",empId);
                startActivity(i);
                progressdialog.hide();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

