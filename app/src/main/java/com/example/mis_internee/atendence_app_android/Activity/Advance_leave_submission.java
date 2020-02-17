package com.example.mis_internee.atendence_app_android.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by MIS-Internee on 03-Jan-18.
 */

public class Advance_leave_submission extends AppCompatActivity {
    TextView DATE, DATE2;
    int day, month, year;
    Spinner spinner1;
    Button btnsbmit;
    private ProgressDialog progressdialog;
    private int conter = 0;
    EditText Edescription;
    RequestQueue requestQueue;
    String insertURL = "http://scorpio.sgroup.pk:8085/atendence/issueDate.php";
    int val;
    String Remk;
    String LD, item, L_ID_VAL, L_CODE_VAL, EmpCode, DEPCode, DEPid, DESIGcode, SHIFTcode, DESIGtype, L_DESC, DESIGid;
    TextView Test;
    String d1, d2;
    ProgressDialog progressDialog,getdata;
    ArrayList<String> L_ID = new ArrayList<String>();
    ArrayList<String> L_code = new ArrayList<String>();
    ArrayList<String> L_Desc = new ArrayList<String>();

    String leave_id, leave_code, leave_desc;
    private ArrayList<String> LEAVE_DESC;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
    String empCode,EMP_CODE, DEP_CODE, DESIG_CODE, SHIFT_CODE, DESIG_TYPE, DEP_ID, DESIG_ID,UNIT_ID,UNIT_CODE,IMEI;
    String EmployeUrl;
    String emp_id;
    java.util.Date date1,date2;

    private DateFormat format;
    EditText Ed_timein,Ed_timeout;
    private Time timeValue1,timeValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advance_leave_submission);
        progressDialog = new ProgressDialog(Advance_leave_submission.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getdata = ProgressDialog.show(Advance_leave_submission.this, "Collecting Information", "Please Wait", false, false);

        DATE = (TextView) findViewById(R.id.date1);
        DATE2 = (TextView) findViewById(R.id.date2);
        btnsbmit = (Button) findViewById(R.id.btnSubmit);
        Ed_timein=(EditText)findViewById(R.id.timein);
        Ed_timeout=(EditText)findViewById(R.id.timeout);
        Edescription = (EditText) findViewById(R.id.rmrks);
        emp_id = getIntent().getExtras().getString("EMP_ID");
        EmployeUrl="http://scorpio.sgroup.pk:8085/atendence/select.php" + "?emp_id=" + emp_id;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
//        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        if (getIntent().getExtras() != null) {

//            empCode = getIntent().getExtras().getString("EMP_CODE");
//            DEP_CODE = getIntent().getExtras().getString("DEP_CODE");
//            DESIG_CODE = getIntent().getExtras().getString("DESIG_CODE");
//            SHIFT_CODE = getIntent().getExtras().getString("SHIFT_CODE");
//            DESIG_TYPE = getIntent().getExtras().getString("DESIG_TYPE");
//            DEP_ID = getIntent().getExtras().getString("DEP_ID");
//            UNIT_CODE=getIntent().getExtras().getString("UNIT_CODE");
//            UNIT_ID=getIntent().getExtras().getString("UNIT_ID");
//            IMEI=getIntent().getExtras().getString("CREATED_BY");
//            DESIG_ID = getIntent().getExtras().getString("DESIG_ID");
            getAlldata();
            LEAVE_DESC = getIntent().getExtras().getStringArrayList("LEAVE_DESC");
            LEAVE_ID = getIntent().getExtras().getStringArrayList("LEAVE_ID");
            LEAVE_CODE = getIntent().getExtras().getStringArrayList("LEAVE_CODE");

        }

        Ed_timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Advance_leave_submission.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Ed_timein.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        Ed_timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int seconds = mcurrentTime.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Advance_leave_submission.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Ed_timeout.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        String t1=Ed_timein.getText().toString();
        String t2=Ed_timeout.getText().toString();
        SimpleDateFormat sddf = new SimpleDateFormat("HH:mm");
        try {
            timeValue1 = new Time(sddf.parse(t1).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            timeValue2 = new Time(sddf.parse(t1).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(t1.trim().equals("")){
            Ed_timein.setError("Please Choose a Date");
        }
        else if(t2.trim().equals("")){
            Ed_timeout.setError("Please Choose correct Date");
        }

        else if(timeValue1.after(timeValue2)){
            Ed_timein.setError("Wrong Dates");
            Ed_timeout.setError("Wrong Dates");
        }
        if (LEAVE_DESC != null) {

            spinner1 = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> staticAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LEAVE_DESC);
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            try {
                spinner1.setAdapter(staticAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                        Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();


                        val = spinner1.getSelectedItemPosition();
                        L_DESC = LEAVE_DESC.get(val);
                        L_ID_VAL = LEAVE_ID.get(val);
                        L_CODE_VAL = LEAVE_CODE.get(val);
//                    Test.setText(LEAVE_DESC.get(val));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {


                    }
                });

            }
            catch (NullPointerException e){
                Toast.makeText(this, "Null Pointer ", Toast.LENGTH_SHORT).show();
            }
        }

        try {
    DATE.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Advance_leave_submission.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    d1 = (dayOfMonth + "-" + (month + 1) + "-" + year);
                    DATE.setText(d1);
                }
            }, year, month, day);
            datePickerDialog.show();


        }
    });
    DATE2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(Advance_leave_submission.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    d2 = (dayOfMonth + "-" + (month + 1) + "-" + year);
                    DATE2.setText(d2);
                }
            }, year, month, day);
            datePickerDialog2.show();
        }
    });
}
catch (NullPointerException e){
    Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
}
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            btnsbmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Remk = Edescription.getText().toString();
                    try {
                        format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        date1 = format.parse(d1);

                        date2=  format.parse(d2);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                        if (DATE.getText().equals("Start Date")) {
//                            Toast.makeText(Advance_leave_submission.this, "Please enter valid date", Toast.LENGTH_SHORT).show();
//                        }
//                        if (DATE2.getText().equals("")) {
//                            Toast.makeText(Advance_leave_submission.this, "Please enter valid date", Toast.LENGTH_SHORT).show();
//                        }
                    if(d1==null){
                         Toast.makeText(Advance_leave_submission.this, "Please enter valid date", Toast.LENGTH_SHORT).show();
                    }
                   else if(d2==null){
                         Toast.makeText(Advance_leave_submission.this, "Please enter valid date", Toast.LENGTH_SHORT).show();
                    }

                  else if(date1.after(date2)){
                          Toast.makeText(Advance_leave_submission.this, "Please enter valid date", Toast.LENGTH_SHORT).show();

                    }
                  else {
                      insertData();
                    }

//                StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//                        if (response.trim().equals("success")) {
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
//                            builder.setMessage("Application Submitted Successfully...!!!").setNegativeButton("Go back", null).create().show();
//                            Edescription.getText().clear();
//                            getdata.dismiss();
//
//
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
//                            builder.setMessage("Date Already Exist..!!").setNegativeButton("Go back", null).create().show();
//                            getdata.dismiss();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
//                        builder.setMessage("Network Error").setNegativeButton("Go back", null).create().show();
//                        getdata.dismiss();
//                    }
//                }) {
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> parameters  = new HashMap<String, String>();
//
//                        parameters.put("LEAVE_DATE1", d1);
//                        parameters.put("LEAVE_DATE2", d2);
//                        parameters.put("REMARKS", Remk);
//                        parameters.put("EMP_ID", emp_id);
//                        parameters.put("EMP_CODE", EMP_CODE);
//                        parameters.put("DEP_CODE", DEP_CODE);
//                        parameters.put("DESIG_CODE", DESIG_CODE);
//                        parameters.put("SHIFT_CODE", SHIFT_CODE);
//                        parameters.put("DESIG_TYPE", DESIG_TYPE);
//                        parameters.put("DEP_ID", DEP_ID);
//                        parameters.put("UNIT_CODE",UNIT_CODE);
//                        parameters.put("UNIT_ID",UNIT_ID);
//                        parameters.put("DESIG_ID", DESIG_ID);
//                        parameters.put("LEAVE_DESC", L_DESC);
//                        parameters.put("LEAVE_ID", L_ID_VAL);
//                        parameters.put("LEAVE_CODE", L_CODE_VAL);
//
//                        return parameters;
//                    }
//                };
//                requestQueue.add(request);
//            }
//
                }
            });
        }
    catch (NullPointerException e){
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }
    }
    public void getAlldata() {
        RequestQueue requestQueue = Volley.newRequestQueue(Advance_leave_submission.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                EmployeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                getdata.dismiss();
                Toast.makeText(Advance_leave_submission.this, "Thanks for patience", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray employee = response.getJSONArray("LeavesResult");
                    for (int i = 0; i < employee.length(); i++) {
                        JSONObject empobj = employee.getJSONObject(i);
                        EMP_CODE = empobj.getString("EMP_CODE");
                        DEP_CODE = empobj.getString("DEP_CODE");
                        DESIG_CODE = empobj.getString("DESIG_CODE");
                        SHIFT_CODE = empobj.getString("SHIFT_CODE");
                        DESIG_TYPE = empobj.getString("DESIG_TYPE");
                        DEP_ID = empobj.getString("DEP_ID");
                        DESIG_ID = empobj.getString("DESIG_ID");
                        UNIT_ID = empobj.getString("UNIT_ID");
                        UNIT_CODE = empobj.getString("UNIT_CODE");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);


    }


    public void insertData(){
        getdata = ProgressDialog.show(Advance_leave_submission.this, "Collecting Information", "Please Wait", false, false);


        StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response.trim().equals("success")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
                    builder.setMessage("Application Submitted Successfully...!!!").setNegativeButton("Go back", null).create().show();
                    Edescription.getText().clear();
                    getdata.dismiss();


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
                    builder.setMessage("Date Already Exist..!!").setNegativeButton("Go back", null).create().show();
                    getdata.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Advance_leave_submission.this);
                builder.setMessage("Network Error").setNegativeButton("Go back", null).create().show();
                getdata.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();

                parameters.put("LEAVE_DATE1", d1);
                parameters.put("LEAVE_DATE2", d2);
                parameters.put("REMARKS", Remk);
                parameters.put("EMP_ID", emp_id);
                parameters.put("EMP_CODE", EMP_CODE);
                parameters.put("DEP_CODE", DEP_CODE);
                parameters.put("DESIG_CODE", DESIG_CODE);
                parameters.put("SHIFT_CODE", SHIFT_CODE);
                parameters.put("DESIG_TYPE", DESIG_TYPE);
                parameters.put("DEP_ID", DEP_ID);
                parameters.put("UNIT_CODE",UNIT_CODE);
                parameters.put("UNIT_ID",UNIT_ID);
                parameters.put("DESIG_ID", DESIG_ID);
                parameters.put("LEAVE_DESC", L_DESC);
                parameters.put("LEAVE_ID", L_ID_VAL);
                parameters.put("LEAVE_CODE", L_CODE_VAL);

                return parameters;
            }
        };
        requestQueue.add(request);
    }





    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}