package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class approval_form_2 extends AppCompatActivity {
    Spinner spin;
    Button btn;
    String selectedItemPosition, SPIN, insertUrl = "http://scorpio.sgroup.pk:8085/atendence/updateempappp.php";
    RequestQueue requestQueue;
    TextView notification, Date, dep, TV, Remarks;
    private ProgressDialog progressdialog;
    private int conter = 0;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText remark;

    String EMP_ID;
    private ProgressDialog progressDialog;
    public String tkn;
    private String newTkn;
    private String myRes;
    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;

    String ConvertImage;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter;

    int RC;

    BufferedReader bufferedReader;

    StringBuilder stringBuilder;

    boolean check = true;
    private String date, leave_remarks, Dep, emp_id, leave_code, Rem;
    private String RemarksHod;
    private ProgressDialog getdata;
    private String[] EMP_TOKEN;
    private String EMPTOKEN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_form_2);
        DisplayMetrics dm = new DisplayMetrics();
        remark = (EditText) findViewById(R.id.remarkHod);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        spin = (Spinner) findViewById(R.id.spinner2);
        btn = (Button) findViewById(R.id.submit);
        listView = (ListView) findViewById(R.id.lvS);
        EMP_ID = getIntent().getExtras().getString("EMP_ID");
        final ActionBar action = getSupportActionBar();
        action.setTitle("Approval Form");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
//                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
                selectedItemPosition = spin.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        date = getIntent().getExtras().getString("LEAVE_DATE");
        leave_remarks = getIntent().getExtras().getString("LEAVE_REMARKS");
        Dep = getIntent().getExtras().getString("DEP_NAME");
        Rem = getIntent().getExtras().getString("REMARKS");
        emp_id = getIntent().getExtras().getString("EMP_ID");
        leave_code = getIntent().getExtras().getString("LEAVE_CODE");

        notification = (TextView) findViewById(R.id.name);
        Date = (TextView) findViewById(R.id.textView15);
        dep = (TextView) findViewById(R.id.textView19);
        Remarks = (TextView) findViewById(R.id.remarks);
        getJSON("http://scorpio.sgroup.pk:8085/atendence/get_Emp_Token.php?EMP_ID="+emp_id);


        notification.setText(leave_code);
        Date.setText(date);
        dep.setText(Dep);
        Remarks.setText(leave_remarks);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplication(),"Cjaskhdcuas",Toast.LENGTH_LONG).show();
                RemarksHod = remark.getText().toString();
//                progressDialog = new ProgressDialog(approval_form_2.this);
//                progressDialog.setMessage("Please wait Loading...");
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.setCancelable(false);
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.setIndeterminate(true);
//                progressDialog.show();
                UploadDataToServer();
//                Request request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
//
//
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(approval_form_2.this, response, Toast.LENGTH_SHORT).show();
////                        try{
//
//                            myRes = response.substring(0, 7);
//                            tkn = response.substring(7, response.length());
//
//
//
//                        if (myRes.trim().equals("success")) {
//                            new Notify().execute();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(approval_form_2.this);
//                            builder.setMessage("Application " + selectedItemPosition + " Successfully...!!!").setNegativeButton("Go back", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    progressDialog.hide();
//                                    finish();
//                                    startActivity(new Intent(approval_form_2.this, Emp_notification.class));
//                                }
//                            }).create().show();
//
//
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(approval_form_2.this);
//                            builder.setMessage("Already Leave submitted...!!!")
//                                    .setNegativeButton("Go back", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            finish();
//                                        }
//                                    }).create().show();
////                            finish();
//
//                    }
////                        catch (IndexOutOfBoundsException e){
////                            Toast.makeText(approval_form_2.this, "Response is null", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> parameters = new HashMap<String, String>();
//                        parameters.put("LEAVE_DATE", date);
//                        parameters.put("NOTIFICATION_STATUS", selectedItemPosition);
//                        parameters.put("REMARKS", RemarksHod);
//                        parameters.put("APPROVED_BY", "RANA WASEEM");
//                        parameters.put("EMP_ID", emp_id);
//
//
//                        return parameters;
//                    }
//                };
//                requestQueue.add(request);
            }
//
        });


    }

//    public class Notify extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//
//            try {
//                URL url = new URL("https://fcm.googleapis.com/fcm/send");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                conn.setUseCaches(false);
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Authorization", "key=AIzaSyALHegJv8RskIRZ4OkdS39gdvR7xJCc5J8");
//                conn.setRequestProperty("Content-Type", "application/json");
//
//                JSONObject json = new JSONObject();
//
//                json.put("to", tkn);
//
//
//                JSONObject info = new JSONObject();
//                info.put("title", "TechnoWeb");   // Notification title
//                info.put("body", "Hello Test notification"); // Notification body
//
//                json.put("notification", info);
//
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                wr.write(json.toString());
//                wr.flush();
//                conn.getInputStream();
//
//            } catch (Exception e) {
//                Log.d("Error", "" + e);
//            }
//
//
//            return null;
//        }
//    }
    public class Notify2 extends AsyncTask<Void,Void,Void>
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
                info.put("title", "Your Application is Viewed by HOD");   // Notification title
                info.put("body", "Check Your Applications Status..."); // Notification body
                info.put("click_action", "OPEN_ACTIVITY_1");
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

    public void UploadDataToServer() {


        @SuppressLint("StaticFieldLeak")
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressDialog = ProgressDialog.show(approval_form_2.this, "Data is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String response) {

                super.onPostExecute(response);
                progressDialog.dismiss();
                myRes = response.substring(0, 7);
                tkn = response.toString();
                if (myRes.trim().equals("success")) {
//
                    new Notify2().execute();

                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(approval_form_2.this);
//                   alertDialogBuilder.setTitle("Application Submitted...!!!");
                    alertDialogBuilder
                            .setMessage(response)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    approval_form_2.this.finish();
                                    startActivity(new Intent(approval_form_2.this, Emp_notification.class));

                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (response.trim().equals("")) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(approval_form_2.this);
                    alertDialogBuilder.setTitle("Application Submitted...!!!");
                    alertDialogBuilder
                            .setMessage(response)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    approval_form_2.this.finish();
                                    startActivity(new Intent(approval_form_2.this, Emp_notification.class));

                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(approval_form_2.this);
                    alertDialogBuilder.setTitle(response);
                    alertDialogBuilder
                            .setMessage("Error...!!!")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    approval_form_2.this.finish();
                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    Toast.makeText(approval_form_2.this, "Applications Submitted "+myRes, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(Void... params) {
//                Remarks = remrk.getText().toString();
                approval_form_2.DataProcessClass DataProcessClass = new approval_form_2.DataProcessClass();
                User user =new User(getApplicationContext());
                String hod = user.getEmp_id();
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("LEAVE_DATE", date);
                parameters.put("NOTIFICATION_STATUS", selectedItemPosition);
                parameters.put("REMARKS", RemarksHod);
                parameters.put("APPROVED_BY", hod);
                parameters.put("EMP_ID", emp_id);

//
//                }

                return DataProcessClass.DataHttpRequest("http://scorpio.sgroup.pk:8085/atendence/updateempappp.php", parameters);
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class DataProcessClass {

        String DataHttpRequest(String requestURL, HashMap<String, String> PData) {

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

                        new OutputStreamWriter(outputStream, "UTF-8"));

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

    private void getJSON(final String urlWebService) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getdata = ProgressDialog.show(approval_form_2.this, "Collecting Information", "Please Wait", false, false);


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getdata.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equals("{\"Result\":[]}")) {

                        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                                Builder(approval_form_2.this);
                        alertDialogBuilder.setTitle("Data Not Found");
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        approval_form_2.this.finish();
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (NullPointerException e) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(approval_form_2.this);
                    alertDialogBuilder.setTitle("Network Error...!!!");
                    alertDialogBuilder
                            .setMessage("Please Check Network Connection")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    approval_form_2.this.finish();
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
                    
                    EMPTOKEN=EMP_TOKEN[i].toString();


                } catch (JSONException e) {
                    Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
//        lv.setAdapter(itemsAdapter);

        } catch (NullPointerException e) {
            final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                    Builder(approval_form_2.this);
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


        }}

}

