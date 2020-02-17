package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.Model.CurrentDate.CurrentDataInfo;
import com.example.mis_internee.atendence_app_android.Model.Login.LoginModel;
import com.example.mis_internee.atendence_app_android.Model.Login.LoginResult;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.Model.Version.VersionModel;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.Utility;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

import app.frantic.kplcompressor.Util;
import retrofit2.Call;

/**
 * Created by MIS-Internee on 03-Jan-18.
 */

public class Popup_Activity_dialog extends Activity {
    Button btn;
    int val;
    TextView tvDate, hod_remarks;
    String EmployeUrl;
    TextView EMP_ID, ARTIME, OFFTIME, STATUS;
    String emp_id, ACHK_IN="", ACHK_OUT="", ATTNDFLAG="", HOD_remarks;

    private ProgressDialog progressdialog;
    private int conter = 0;
    String date;
    String empCode, DEP_CODE, UNIT_ID, UNIT_CODE, IMEI, DESIG_CODE, SHIFT_CODE, DESIG_TYPE, DEP_ID, DESIG_ID;
    private ArrayList<String> At;
    private ArrayList<String> dutyOf;
    private ArrayList<String> status;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
    private ArrayList<String> LEAVE_BAL;
    private ArrayList<String> LEAVE_DESC;
    private TextView hod_remarks_text;
    private ProgressDialog progressDialog;
    private String DUE_mDATE;
    public  int dbDays,countSL;

    private String ALD_DAYS;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        btn = (Button) findViewById(R.id.subIssue_1);
        ARTIME = (TextView) findViewById(R.id.ArTi);
        OFFTIME = (TextView) findViewById(R.id.offTime);
        STATUS = (TextView) findViewById(R.id.status);
        tvDate = (TextView) findViewById(R.id.Datee);
        hod_remarks =  findViewById(R.id.hod_remarks);
        hod_remarks_text = (TextView) findViewById(R.id.hodText);


        Intent i = getIntent();
        date = i.getStringExtra("date");
        emp_id = i.getStringExtra("EMP_ID");
        tvDate.setText(date);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * 0.33));

        if(Utility.isNetworkAvailable()) {
            getCurrentDate();
        }else {
            Toast.makeText(this, "not InterNet available", Toast.LENGTH_SHORT).show();
        }

      //  getJSON("http://scorpio.sgroup.pk:8085/atendence/showTime.php" + "?date=" + date + "&&EMP_ID=" + emp_id);

        if (getIntent().getExtras() != null) {

            empCode = getIntent().getExtras().getString("EMP_CODE");
            countSL = getIntent().getExtras().getInt("countSl");
            DEP_CODE = getIntent().getExtras().getString("DEP_CODE");
            DESIG_CODE = getIntent().getExtras().getString("DESIG_CODE");
            SHIFT_CODE = getIntent().getExtras().getString("SHIFT_CODE");
            DESIG_TYPE = getIntent().getExtras().getString("DESIG_TYPE");
            UNIT_CODE = getIntent().getExtras().getString("UNIT_CODE");
            UNIT_ID = getIntent().getExtras().getString("UNIT_ID");
            IMEI = getIntent().getExtras().getString("CREATED_BY");
            DEP_ID = getIntent().getExtras().getString("DEP_ID");
            DESIG_ID = getIntent().getExtras().getString("DESIG_ID");
            LEAVE_DESC = getIntent().getExtras().getStringArrayList("LEAVE_DESC");
            LEAVE_ID = getIntent().getExtras().getStringArrayList("LEAVE_ID");
            LEAVE_CODE = getIntent().getExtras().getStringArrayList("LEAVE_CODE");
            LEAVE_BAL = getIntent().getExtras().getStringArrayList("LEAVES");
//            DUE_DAYS = getIntent().getExtras().getString("DUE_DAYS");
            DUE_mDATE = getIntent().getExtras().getString("DUE_mDATE");
        }
//        if(DUE_DAYS!=null) {
//
//        }
//        else
//        {
//            dbDays=30;
//        }



   // }

/*

    private void getJSON(final String urlWebService) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                progressDialog = new ProgressDialog(Popup_Activity_dialog.this);

                progressDialog.setMessage("Loading...");

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setIndeterminate(true);
//                progressDialog.show();
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                try {

                    loadIntoListView(s);
                } catch (NullPointerException e) {
                    AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(Popup_Activity_dialog.this);
                    alertDialogBuilder.setTitle("Network Error...!!!");
                    alertDialogBuilder
                            .setMessage("Please Check Network Connection")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    Popup_Activity_dialog.this.finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                catch (JSONException e) {
                    AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(Popup_Activity_dialog.this);
                    alertDialogBuilder.setTitle("Network Error...!!!");
                    alertDialogBuilder
                            .setMessage("Please Check Network Connection")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    Popup_Activity_dialog.this.finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
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
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }



    @SuppressLint("ResourceAsColor")
    private void loadIntoListView(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        final JSONArray jsonArray = object.getJSONArray("Result");
        final String[] ACHKIN = new String[jsonArray.length()];
        final String[] ACHKOUT = new String[jsonArray.length()];
        final String[] FLAG = new String[jsonArray.length()];
        final String[] ALD = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject obj;
         //   try {
                obj = jsonArray.getJSONObject(i);
                ACHKIN[i] = obj.getString("ACHKIN_TIME");
                ACHKOUT[i] = obj.getString("ACHKOUT_TIME");
                FLAG[i] = obj.getString("ATTND_FLAG");
                ALD[i] = obj.getString("ALLOW_LEAVE_DAYS");
//                HOD_REMARKS[i]=obj.getString("HOD_REMARKS");
                ACHK_IN = ACHKIN[i].toString();
                ACHK_OUT = ACHKOUT[i].toString();
                ATTNDFLAG = FLAG[i].toString();
                ALD_DAYS = ALD[i].toString();
                dbDays = Integer.parseInt(ALD_DAYS);
                if ((ATTNDFLAG.equals("A")) || (ATTNDFLAG.equals("LA"))
                        || (ATTNDFLAG.equals("SL")) || (ATTNDFLAG.equals("OD"))
                        || (ATTNDFLAG.equals("ML"))) {

                    hod_remarks.setVisibility(View.VISIBLE);
                    hod_remarks_text.setVisibility(View.VISIBLE);
                }

           */
/* } catch (JSONException e) {
                e.printStackTrace();
            }

*//*

        }
*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String dt1 = date +" "+ACHK_IN+":00";
                String dt2 = date + " "+ ACHK_OUT +":00";


                String mindate1 = date +" "+ACHK_IN+":00";
                String mindate2= date + " "+ "09:00" +":00";



                //  int diff = DateTimeUtils.getDateDiff(dt2,dt1, DateTimeUnits.HOURS);

                Utility.mDifInMin = DateTimeUtils.getDateDiff(mindate1,mindate2, DateTimeUnits.MINUTES);

                Utility.mDutyHour = DateTimeUtils.getDateDiff(dt2,dt1, DateTimeUnits.HOURS);
                Utility.mDutyMin = DateTimeUtils.getDateDiff(dt2,dt1, DateTimeUnits.MINUTES);


                Log.d("difference","time In :"+dt1+"");
                Log.d("difference","time out : "+dt2+"");
                Log.d("difference","duty Hour : "+ Utility.mDutyHour +":" +Utility.mDutyMin );

                Log.d("difference","late Arrive : "+Utility.mDifInMin+"");










                if (ATTNDFLAG == null)     {
                    Toast.makeText(getApplication(), "Attendance Not found...!!!", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("P")) {
                    Toast.makeText(getApplication(), "Status is Present", Toast.LENGTH_LONG).show();
                  //    tempFuncation("0");
                } else if (ATTNDFLAG.equals("WL")) {
                    Toast.makeText(getApplication(), "Weekly Leave", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("SL")) {
                 //   tempFuncation("1");
                    Toast.makeText(getApplication(), "Short Leave already submitted", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("GL")) {
                    Toast.makeText(getApplication(), "Status is General Leave", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("OD")) {
                 //   tempFuncation("1");
                    Toast.makeText(getApplication(), "Status is OD", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("CL")) {
                   //  tempFuncation("0");
                    Toast.makeText(getApplication(), "Casual Leave Submitted already", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("ML")) {
                   //  tempFuncation("0");
                    Toast.makeText(getApplication(), "ML submitted Already", Toast.LENGTH_LONG).show();
                } else if (ATTNDFLAG.equals("HF")) {
                    Toast.makeText(getApplication(), "Half Leave", Toast.LENGTH_LONG).show();
                  //   tempFuncation("1");
                }else if (ATTNDFLAG.equals("PTL")) {
                    Toast.makeText(getApplication(), "Paternity Leave", Toast.LENGTH_LONG).show();
                }else if (ATTNDFLAG.equals("MTL")) {
                    Toast.makeText(getApplication(), "Maternity Leave", Toast.LENGTH_LONG).show();
                }else if (ATTNDFLAG.equals("H/U")) {
                    Toast.makeText(getApplication(), "Hajj Umrah Leave", Toast.LENGTH_LONG).show();
                }else if (ATTNDFLAG.equals("CPL")) {
                    Toast.makeText(getApplication(), "Compensatory Leave", Toast.LENGTH_LONG).show();
                }else if (ATTNDFLAG.equals("SSL")) {
                    Toast.makeText(getApplication(), "Special Short Leave", Toast.LENGTH_LONG).show();
                }



                else if (ATTNDFLAG.equals("EL")) {


                    tempFuncation("13");
                }

                else if (ATTNDFLAG.equals("NS")) {
                    tempFuncation("0");
                }
                else if (ATTNDFLAG.equals("A")) {

                       tempFuncation("11");


                }

                else if (ATTNDFLAG.trim().equals("LA")) {




                        tempFuncation("13");




                }
            }
        });



    }



    private void getCurrentDate() {

        Call<CurrentDataInfo> call = ServerTask.getInstance().getServices().get_current_date(emp_id,date);

        call.enqueue(new ServerCallback<CurrentDataInfo>() {
            @Override
            public void onFailure(ServerError restError) {
                //   hideProgress();

                AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                        Builder(Popup_Activity_dialog.this);
                alertDialogBuilder.setTitle("Server Error...!!!");
                alertDialogBuilder
                        .setMessage("Please wait ...Server problem... ")
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Popup_Activity_dialog.this.finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

            @Override
            public void onSuccess(retrofit2.Response<CurrentDataInfo> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<CurrentDataInfo> response) {



                try {

                    assert response.body() != null;
                    ACHK_IN = response.body().getResult().get(0).getACHKINTIME().trim();
                    ARTIME.setText(ACHK_IN);
                    ACHK_OUT = response.body().getResult().get(0).getACHKOUTTIME().trim();
                    OFFTIME.setText(ACHK_OUT);
                    ATTNDFLAG =response.body().getResult().get(0).getATTNDFLAG().trim();
                    STATUS.setText(ATTNDFLAG);
                    ALD_DAYS = response.body().getResult().get(0).getALLOWLEAVEDAYS();
                    dbDays = Integer.parseInt(ALD_DAYS);

                 ;


                 /*   if ((ATTNDFLAG.equals("A")) || (ATTNDFLAG.equals("LA"))
                            || (ATTNDFLAG.equals("SL")) || (ATTNDFLAG.equals("OD"))
                            || (ATTNDFLAG.equals("ML"))) {

                        hod_remarks.setVisibility(View.VISIBLE);
                        hod_remarks_text.setVisibility(View.VISIBLE);
                    }
*/


                }catch (Exception e){e.printStackTrace();}



            }



        });

    }

     public  void tempFuncation(String  data){




         Intent i = new Intent(Popup_Activity_dialog.this, subIsue.class);

         i.putExtra("date", date);
         i.putExtra("data_info",data);
         i.putExtra("countSl",countSL);
         i.putExtra("EMP_ID", emp_id);
         i.putExtra("DEP_CODE", DEP_CODE);
         i.putExtra("DESIG_CODE", DESIG_CODE);
         i.putExtra("SHIFT_CODE", SHIFT_CODE);
         i.putExtra("DESIG_TYPE", DESIG_TYPE);
         i.putExtra("DEP_ID", DEP_ID);
         i.putExtra("DESIG_ID", DESIG_ID);
         i.putExtra("EMP_CODE", empCode);
         i.putExtra("UNIT_CODE", UNIT_CODE);
         i.putExtra("UNIT_ID", UNIT_ID);
         i.putExtra("CREATED_BY", IMEI);
         Bundle bndl = new Bundle();
         bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
         bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
         bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
         bndl.putStringArrayList("LEAVES", LEAVE_BAL);
         i.putExtras(bndl);
         startActivity(i);

     }

}
