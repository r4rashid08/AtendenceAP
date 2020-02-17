package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.mis_internee.atendence_app_android.Model.FcmModel.FcmResult;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeaveSubmitResult;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.PrefManager;
import com.example.mis_internee.atendence_app_android.Utils.Utility;
import com.google.gson.Gson;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;


public class subIsue extends AppCompatActivity implements  RangeTimePickerDialog.ISelectedTime {
    TextView DATE, DATE2,tv7,tv8,TV_DATE;
    int day, month, year;
    String date, empId;

    EditText remarks;
    public ProgressDialog progressdialog;
    public int conter = 0;
    String Remk;
    Spinner spinner1;
    int val;

    ArrayList<String> LEAVE_DESC =new ArrayList<>();

    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
     ArrayList<String> LEAVE_BAL;
    String  item, leaveInfo,data_info;
    RequestQueue requestQueue;

    String testToken = "http://scorpio.sgroup.pk:8085/atendence/Test_Token.php";
    String EmployeUrl;
    Button btn;
    String DEP_CODEE,L_ID_VAL,L_CODE_VAL,L_CREDIT,L_DESC,L_Bal;
    String DESIG_CODE,EMP_CODE,SHIFT_CODE,DEP_ID,DESIG_TYPE,DESIG_ID,MANAGER_CODE,HOD_CODE,UNIT_ID,
            UNIT_CODE,DESIG_NAME="",IS_HOD, FLAG_ATTANDANCE;
    private String EMP_NAME;
    LinearLayout mLeaveView,mDateView;
    Spinner  mHLSpinner;
    ProgressDialog  getdata;

    HttpURLConnection httpURLConnection;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter;

    int RC;

    BufferedReader bufferedReader;

    StringBuilder stringBuilder;

    boolean check = true;

    TextView Ed_timein,Ed_timeout;

    public String[] EMP_TOKEN;
    public String EMPTOKEN, spinnerText,monthYear;

     float bal=0;
   public    int countSl =0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_isue);

        if(LEAVE_DESC!=null){
            LEAVE_DESC.clear();
        }


        getdata = ProgressDialog.show(subIsue.this, "Collecting Information", "Please Wait", false, false);
        empId = Objects.requireNonNull(getIntent().getExtras()).getString("EMP_ID");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {

                                      getdata.dismiss();
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                  }
                              },
                0,
                5000);

        remarks =  findViewById(R.id.rmrks);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DATE =  findViewById(R.id.date1);
        DATE2 =  findViewById(R.id.date2);
        spinner1 =  findViewById(R.id.spinner);
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        btn =  findViewById(R.id.sub_btn_leave);
        mHLSpinner =findViewById(R.id.halfspinner);
        mLeaveView =findViewById(R.id.leave_spinner_view);
      //  btn.setEnabled(false);
        btn.setTextColor(getResources().getColor(R.color.white));
        Ed_timein=findViewById(R.id.timein);
        Ed_timeout=findViewById(R.id.timeout);
        mDateView =findViewById(R.id.date_view);


        tv7 =findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        date = getIntent().getExtras().getString("date");

      //  countSl = Utility.countLA;

        tv7.setText(item);
        TV_DATE =  findViewById(R.id.tv_date);



        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MMM-yyyy");


        try {
            Date starting= simpleDateFormat1.parse(date);
            date = simpleDateFormat2.format(starting);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        EmployeUrl = "http://scorpio.sgroup.pk:8085/atendence/select2.php" + "?emp_id=" + empId +"&DATE="+date;
        TV_DATE.setText(date);


        Ed_timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RangeTimePickerDialog dialog = new RangeTimePickerDialog();
                dialog.newInstance(R.color.colorPrimary, R.color.White, R.color.Yellow, R.color.colorPrimary, true);
                FragmentManager fragmentManager = getFragmentManager();
                dialog.show(fragmentManager, "");


            }
        });
        Ed_timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RangeTimePickerDialog dialog = new RangeTimePickerDialog();
                dialog.newInstance(R.color.colorPrimary, R.color.White, R.color.Yellow, R.color.colorPrimary, true);
                FragmentManager fragmentManager = getFragmentManager();
                dialog.show(fragmentManager, "");

            }
        });

        if (getIntent().getExtras() != null) {
//            empCode=getIntent().getExtras().getString("EMP_CODE");
            data_info = getIntent().getExtras().getString("data_info");
//            DESIG_CODEE =getIntent().getExtras().getString("DESIG_CODE");
//            SHIFT_CODEE = getIntent().getExtras().getString("SHIFT_CODE");

            UNIT_CODE=getIntent().getExtras().getString("UNIT_CODE");
            UNIT_ID=getIntent().getExtras().getString("UNIT_ID");
//            IMEI=getIntent().getExtras().getString("CREATED_BY");
//            DEP_IDD = getIntent().getExtras().getString("DEP_ID");
//            DESIG_IDD = getIntent().getExtras().getString("DESIG_ID");
            LEAVE_DESC = getIntent().getExtras().getStringArrayList("LEAVE_DESC");
            LEAVE_ID = getIntent().getExtras().getStringArrayList("LEAVE_ID");
            LEAVE_CODE = getIntent().getExtras().getStringArrayList("LEAVE_CODE");
            LEAVE_BAL = getIntent().getExtras().getStringArrayList("LEAVES");

        }


        getAlldata();

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LEAVE_DESC);
        spinner1.setAdapter(staticAdapter);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);





        mHLSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
//                    Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
                leaveInfo = mHLSpinner.getSelectedItem().toString();

                if(position==0){
                    L_CREDIT ="0.5";
                    mDateView.setVisibility(View.VISIBLE);
                }else {
                    L_CREDIT="1";
                    mDateView.setVisibility(View.GONE);
                }


                //     Toast.makeText(subIsue.this, leaveInfo, Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });

        if(data_info.trim().equals("10")){
             LEAVE_DESC.clear();
             mLeaveView.setVisibility(View.GONE);
             mDateView.setVisibility(View.VISIBLE);
            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn.setEnabled(true);

        }else if(data_info.trim().equals("0")){
            mDateView.setVisibility(View.GONE);
            mLeaveView.setVisibility(View.VISIBLE);
        }
        else if(data_info.trim().equals("1")){
            mDateView.setVisibility(View.VISIBLE);
            mLeaveView.setVisibility(View.VISIBLE);
        }
        else if(data_info.trim().equals("11")){
            mHLSpinner.setEnabled(false);
            mHLSpinner.setSelection(1);
            mDateView.setVisibility(View.GONE);
            for(int i = 0; i< LEAVE_DESC.size();i++){
                String info = LEAVE_DESC.get(i);
                if(info.toUpperCase().trim().equals("SHORT LEAVE")) {
                    LEAVE_DESC.remove(i);
                }else
                if(info.toUpperCase().trim().equals("PATERNITY LEAVE")) {
                    //  spinner1.setSelection(i);
                    LEAVE_DESC.remove(i);
                }

            }


          //  mLeaveView.setVisibility(View.VISIBLE);
        }


        else if(data_info.trim().equals("13")){

            for(int i = 0; i< LEAVE_DESC.size();i++){
                String info = LEAVE_DESC.get(i);
                if(info.toUpperCase().trim().equals("SHORT LEAVE")) {
                    spinner1.setSelection(i);
                }else
                if(info.toUpperCase().trim().equals("PATERNITY LEAVE")) {
                  //  spinner1.setSelection(i);
                    LEAVE_DESC.remove(i);
                }

            }

              L_CREDIT="0";
            mHLSpinner.setEnabled(false);
           // mHLSpinner.setSelection(1);
            mDateView.setVisibility(View.VISIBLE);

            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn.setEnabled(true);


            //  mLeaveView.setVisibility(View.VISIBLE);
        }



        if (LEAVE_DESC != null && LEAVE_DESC.size()>1) {

            LEAVE_DESC.add("Official Duty");



            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
//                    Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();



                    val= 0;
                    bal=0;
                    val = spinner1.getSelectedItemPosition();

                    spinnerText = spinner1.getSelectedItem().toString();



                    try{
                        if(val!=4) {
                            L_ID_VAL = LEAVE_ID.get(val);
                            L_CODE_VAL = LEAVE_CODE.get(val);
                            L_DESC = LEAVE_DESC.get(val);
                            L_Bal = Utility.LEAVE_BAL.get(val);
                         //   L_CREDIT = LEAVE_CREDIT[position];

                            tv7.setText("Required Credit for " + LEAVE_DESC.get(val) + "  " + L_Bal.trim());

                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(subIsue.this, "Please Restart application", Toast.LENGTH_SHORT).show();
                    }

//                    code= Integer.parseInt(CODE[i].toString());

                    try {
                        bal = Float.parseFloat(L_Bal.trim());
                    }catch (Exception e){e.printStackTrace();}
//                    int l_credit= Integer.parseInt(L_CREDIT);

                    if(val!=4) {

                        if(!data_info.trim().equals("13")) {

                            if (bal > 0) {
                            //    Toast.makeText(subIsue.this, "balance available", Toast.LENGTH_SHORT).show();
                                btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                btn.setEnabled(true);
                            } else {
                            //    Toast.makeText(subIsue.this, "You Don't have Enough balance to submit leave", Toast.LENGTH_SHORT).show();
                                btn.setEnabled(false);
                                btn.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                                btn.setTextColor(getResources().getColor(R.color.white));
                            }
                        }else{
                            btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            btn.setEnabled(true);
                        }
                    }else {


                        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        btn.setEnabled(true);
                    }


                    if(spinnerText.trim().equals("Short Leave") ){

                        mDateView.setVisibility(View.VISIBLE);
                        mLeaveView.setVisibility(View.GONE);
                        L_CREDIT ="0";

                    }
                    else  if(spinnerText.trim().equals("Casual") ){
                        mDateView.setVisibility(View.GONE);
                        mLeaveView.setVisibility(View.VISIBLE);
                    }

                    else if(spinnerText.trim().equals("Official Duty")){
                        L_ID_VAL="4";
                        L_CODE_VAL = "OD";
                        L_DESC ="Official Duty";
                        L_CREDIT ="0";
                        mDateView.setVisibility(View.VISIBLE);
                        mLeaveView.setVisibility(View.GONE);
                        tv7.setText("Please submit your Official Duty ");

                    }
                    else if(spinnerText.trim().equals("Annual")){

                        mLeaveView.setVisibility(View.VISIBLE);
                        mDateView.setVisibility(View.GONE);


                    }

                    else if(spinnerText.trim().equals("Medical")){
                        mDateView.setVisibility(View.GONE);
                        mLeaveView.setVisibility(View.VISIBLE);
                    }
                    else{
                        mDateView.setVisibility(View.GONE);
                    }

                    if(data_info.equals("13")){
                        mDateView.setVisibility(View.VISIBLE);

                    }

                    if(data_info.equals("11")){
                        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        btn.setEnabled(true);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {


                }
            });

        }






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Remk = remarks.getText().toString();



                if(spinnerText.toUpperCase().equals("SHORT LEAVE")) {

               /*     StringTokenizer stringTokenizer = new StringTokenizer(date, "-");
                    String day = stringTokenizer.nextToken();
                    monthYear = stringTokenizer.nextToken();
*/

                   /* PrefManager prefManager = new PrefManager(getApplicationContext());
                    String tempMonthYear = prefManager.getMonthYear(monthYear);
                     countSl = Integer.valueOf(tempMonthYear);
                     Log.d("countsl","count : "+countSl +" : "+ day);*/

                //    if (Integer.valueOf(day) < 26) {

                    if (countSl< 2) {
                        countSl++;
                        L_CREDIT="0";
                        PostData();

                    } else {

                        Toast.makeText(subIsue.this, "you have already submit two short leaves", Toast.LENGTH_SHORT).show();
                    }
               /* }else {
                        StringTokenizer stringTokenizer1 = new StringTokenizer(monthYear,"-");
                        String  newMonth = stringTokenizer1.nextToken();
                        String newYear = stringTokenizer1.nextToken();
                        int intMonth = Integer.valueOf(newMonth);
                        if(intMonth==12){
                            monthYear = "1-"+newYear;
                        }else {
                            monthYear = intMonth+1 +"-"+newYear;
                        }


                        Toast.makeText(subIsue.this, "submit value of next mMonthFinal", Toast.LENGTH_SHORT).show();
                }
*/
              }else {

                 //   Toast.makeText(subIsue.this, "not short leave", Toast.LENGTH_SHORT).show();
                    PostData();

                }




       //       PostData();

            }
        });

    }


    public void getAlldata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                EmployeUrl, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

            //    Toast.makeText(subIsue.this, "Thanks for patience", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray employee = response.getJSONArray("Result");
                       for (int i = 0; i < 1; i++) {

                        JSONObject empobj = employee.getJSONObject(i);
                        EMP_CODE= empobj.getString("EMP_CODE");
                        EMP_NAME= empobj.getString("EMP_NAME");
                        DEP_CODEE = empobj.getString("DEP_CODE");
                        DESIG_CODE = empobj.getString("DESIG_CODE");
                        SHIFT_CODE = empobj.getString("SHIFT_CODE");
                        DESIG_TYPE = empobj.getString("DESIG_TYPE");
                        DEP_ID = empobj.getString("DEP_ID");
                        DESIG_ID = empobj.getString("DESIG_ID");
                        UNIT_ID=empobj.getString("UNIT_ID");
                        UNIT_CODE=empobj.getString("UNIT_CODE");
                        MANAGER_CODE=empobj.getString("MANAGER_CODE");
                        HOD_CODE=empobj.getString("HOD_CODE");
                        DESIG_NAME = empobj.getString("DESIG_NAME");
                        IS_HOD = empobj.getString("IS_MANAGER");
                        FLAG_ATTANDANCE = empobj.getString("ATTND_FLAG");
                      String  Sl = empobj.getString("SHORT_LEAVE_COUNT");
                       countSl = Integer.valueOf(Sl);


                        getJSON("http://scorpio.sgroup.pk:8085/atendence/get_HOD_Token.php?EMP_ID="+MANAGER_CODE);

                        tv8.setText(""+MANAGER_CODE);
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

    public void PostData() {


        @SuppressLint("StaticFieldLeak")
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                getdata = ProgressDialog.show(subIsue.this, "Data is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {



                super.onPostExecute(string1);

                try {

                    Gson g = new Gson();
                    LeaveSubmitResult leaveSubmitResult = g.fromJson(string1, LeaveSubmitResult.class);

                    String status = leaveSubmitResult.getStatus();

                    String msg = leaveSubmitResult.getResult();

                    getdata.dismiss();

                    switch (status) {
                        case "0":


                            if(!spinnerText.trim().equals("Short Leave") ) {
                                LeaveBalanceUpdate(empId, L_CREDIT, L_CODE_VAL);
                            }
                            LeaveDialog(msg, "Application Submit Successfully");

                            break;
                        case "1":

                            LeaveDialog(msg, "Application Submit Failed");
                            break;
                        case "2":

                            LeaveDialog(msg, "Application Already Submited");
                            break;
                    }
                }catch (Exception e){e.printStackTrace();
                  LeaveDialog("Error", "!! Result Failed !!");}



            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... params) {
//                Remarks = remrk.getText().toString();
                ServerConnection serverConnection = new ServerConnection();

                HashMap<String, String> parameters = new HashMap<>();
                @SuppressLint("WrongThread")
                String time1=Ed_timein.getText().toString();
                if(time1.equals("Start Time")){
                    time1= "00:00";
                }
                @SuppressLint("WrongThread")
                String time2=Ed_timeout.getText().toString();
                if(time2.equals("End time")){
                    time2 = "00:00";
                }
               /* parameters.put("LEAVE_REMARKS", Remk);
                parameters.put("USER_ID", empId);
                parameters.put("FROM_USER", EMP_NAME);
                parameters.put("TO_USER", "");
                parameters.put("NOTIFICATION_TYPE", L_CODE_VAL);
                parameters.put("LEAVE_DATE", date);
                parameters.put("TO_USER_ID", MANAGER_CODE);
                parameters.put("LEAVE_ID", L_ID_VAL);
                parameters.put("LEAVE_CODE", L_CODE_VAL);
                parameters.put("LEAVE_DESC", L_DESC);
                parameters.put("LEAVE_CREDIT", L_CREDIT);*/


                parameters.put("TIME_IN", time1);
                parameters.put("TIME_OUT", time2);
                parameters.put("EMP_ID",empId );
                parameters.put("EMP_NAME", EMP_NAME);

                parameters.put("EMP_CODE",EMP_CODE );
                parameters.put("DESIGNATION",DESIG_NAME);
                parameters.put("UNIT_CODE", UNIT_CODE);

                parameters.put("NOTIFICATION_DATE",date );
                parameters.put("NOTIFCAITON_TRAN_TYPE",FLAG_ATTANDANCE );
                parameters.put("HOD_ID",HOD_CODE );
                parameters.put("MANAGER_ID",MANAGER_CODE );

                parameters.put("NATURE_OF_LEAVE", L_ID_VAL);
                parameters.put("STATUS","PENDING" );
                parameters.put("REMARKS",Remk );
                parameters.put("CREATED_BY", EMP_NAME);

                parameters.put("LEAVE_DESC",L_DESC );
                parameters.put("LEAVE_ID",L_ID_VAL );
                parameters.put("CONSUMED",L_CREDIT );
                parameters.put("DATE_FROM",date );

                parameters.put("DATE_TO",date );
                parameters.put("INTERFACE","0" );
                parameters.put("DEP_ID",DEP_ID );
                parameters.put("IS_HOD",IS_HOD );




                return serverConnection.ImageHttpRequest(testToken, parameters);
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        Ed_timein.setText(""+hourStart+":"+minuteStart );
        Ed_timeout.setText(hourEnd+":"+minuteEnd);

    //    Toast.makeText(this, "Start: "+hourStart+":"+minuteStart+"\nEnd: "+hourEnd+":"+minuteEnd, Toast.LENGTH_SHORT).show();

    }


    public class ServerConnection {

        String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @SuppressLint("StaticFieldLeak")
    public class Notify extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=AIzaSyBBD5N6Heo2sA98TbOCzn7VEwufgXZzL6Y");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();

                json.put("to", EMPTOKEN);
                JSONObject info = new JSONObject();
                info.put("title", "Employee Leave Notification");   // Notification title
                info.put("body", "One Leave Notifications is received from sharif group employee for leave approval"); // Notification body
                info.put("click_action", "OPEN_ACTIVITY_2"); // Notification body
                json.put("notification", info);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();

            }
            catch (Exception e)
            {
                Log.d("Error",""+e);
            }


            return null;
        }
    }

    private void getJSON(final String urlWebService) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                getdata = ProgressDialog.show(subIsue.this, "Collecting Information", "Please Wait", false, false);


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                getdata.dismiss();
                getdata.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equals("{\"Result\":[]}")) {

                        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                                Builder(subIsue.this);
                        alertDialogBuilder.setTitle("Data Not Found");
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        subIsue.this.finish();
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (NullPointerException e) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(subIsue.this);
                    alertDialogBuilder.setTitle("Network Error...!!!");
                    alertDialogBuilder
                            .setMessage("Please Check Network Connection")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    subIsue.this.finish();
                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


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
                        sb.append(json).append("\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("ERROR", "Error pasting data " + e.toString());
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
            EMP_TOKEN = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;

                try {
                    obj = jsonArray.getJSONObject(i);
                    EMP_TOKEN[i] = obj.getString("TOKEN");
                    EMPTOKEN=EMP_TOKEN[i];


                } catch (JSONException e) {
                    Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
//        lv.setAdapter(itemsAdapter);

        } catch (NullPointerException e) {
            final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                    Builder(subIsue.this);
            alertDialogBuilder.setTitle("Network Error");
            alertDialogBuilder
                    .setMessage("Please Check Network..!!!")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }


    private void LeaveDialog(String info, String tittle){
        final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                Builder(subIsue.this);
//                    tkn= FirebaseInstanceId.getInstance().getToken();

        new Notify().execute();
        alertDialogBuilder.setTitle(tittle);
        alertDialogBuilder
                .setMessage(info)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      /*  PrefManager prefManager = new PrefManager(getApplicationContext());
                        prefManager.setMonthYear(monthYear,String.valueOf(countSl));*/
                        dialog.cancel();
                        subIsue.this.finish();

                    }
                });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void LeaveBalanceUpdate(String emp_id,String consumed, String leaveCode) {

        Call<FcmResult> call = ServerTask.getInstance().getServices().UpdateLeaveBlance(emp_id,consumed,leaveCode);

        call.enqueue(new ServerCallback<FcmResult>() {
            @Override
            public void onFailure(ServerError restError) {
                //   hideProgress();

            }

            @Override
            public void onSuccess(retrofit2.Response<FcmResult> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<FcmResult> response) {

                assert response.body() != null;

                String result = response.body().getResult();

                Log.d("FCM_RESULT", result);

                // Toast.makeText(MainActivity.this, status+ result, Toast.LENGTH_SHORT).show();




            }



        });

    }



}

