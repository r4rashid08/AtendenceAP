package com.example.mis_internee.atendence_app_android.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Adapter.Chekcbox_Adapter_listView;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Approval_Form extends AppCompatActivity {
    Spinner spin;
    Button btn;
    String selectedItemPosition, SPIN, insertUrl="http://scorpio.sgroup.pk:8085/atendence/updateEmpApp.php";
    RequestQueue requestQueue;
    TextView Name,Date,dep,TV,Remarks;
    ArrayAdapter<String> arrayAdapter ;
    ListView listView;

    String EMP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_form);
//        DisplayMetrics dm=new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        spin=(Spinner)findViewById(R.id.spinner2);
        btn=(Button)findViewById(R.id.submit);
        listView=(ListView)findViewById(R.id.lvS);
        EMP_ID=getIntent().getExtras().getString("EMP_ID");

        getJSON("http://scorpio.sgroup.pk:8085/atendence/Single_employee_leaves.php"+ "?EMP_ID=" + EMP_ID);
//        int width= dm.widthPixels;
//        int height=dm.heightPixels;


//        getWindow().setLayout((int)(width*.8),(int)(height*.5));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
//                Toast.makeText(getApplicationContext(), "Selected", Toast.LENGTH_LONG).show();
//                selectedItemPosition=spin.getSelectedItem().toString();
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//
//            }
//        });






        final String date=getIntent().getExtras().getString("LEAVE_DATE");
        final String name=getIntent().getExtras().getString("EMP_NAME");
        final String Dep=getIntent().getExtras().getString("DEP_NAME");
        final String Rem=getIntent().getExtras().getString("REMARKS");
        final String emp_id=getIntent().getExtras().getString("EMP_ID");
        final String leave_code=getIntent().getExtras().getString("LEAVE_CODE");

        Name=(TextView)findViewById(R.id.name);
        Date=(TextView)findViewById(R.id.textView15);
        dep=(TextView)findViewById(R.id.textView19);
        Remarks=(TextView)findViewById(R.id.remarks);

//
//        Name.setText(empId);
//        Date.setText(date);
//        dep.setText(Dep);
//        Remarks.setText(Rem);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(getApplication(),"Cjaskhdcuas",Toast.LENGTH_LONG).show();
//
//   Request request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
//
//
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.trim().equals("success")) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Approval_Form.this);
//                            builder.setMessage("Application "+selectedItemPosition+" Successfully...!!!").setNegativeButton("Go back", null).create().show();
////                            remarks.getText().clear();
//
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Approval_Form.this);
//                            builder.setMessage("Error...!!!").setNegativeButton("Go back", null).create().show();
//
//                        }
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
//                    parameters.put("LEAVE_DATE",date);
//                    parameters.put("TRN_TYPE",selectedItemPosition);
//                    parameters.put("REMARKS",Rem);
//                    parameters.put("ATTND_FLAG",leave_code);
//                    parameters.put("ATTND_FLAG2",leave_code);
//                    parameters.put("EMP_ID",emp_id);
//
//
//
//
//
//                        return parameters;
//                    }
//                };
//                requestQueue.add(request);
//            }
//
//        });


        }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
//                conter++;
//                if (conter == 1) {
//                    progressdialog = new ProgressDialog(EmployeeLeaveApplication.this);
//                    progressdialog.setMessage("Please Wait Loading data....");
//                    progressdialog.show();
//                    progressdialog.setCancelable(false);
//                }


                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
            final JSONArray jsonArray = object.getJSONArray("LeavesResult");

            final String[] EMP_NAME = new String[jsonArray.length()];
            final String[] EMP_name = new String[jsonArray.length()];
            final String[] leave_date = new String[jsonArray.length()];
            final String[] Dep = new String[jsonArray.length()];
            final String[] Remarks = new String[jsonArray.length()];
            final String[] LEAVE_CODE = new String[jsonArray.length()];
            final String[] EMP_ID = new String[jsonArray.length()];


            int i;
            for (i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);


                    EMP_NAME[i] = (i + 1) + "      " + obj.getString("EMP_NAME")+ "      " + obj.getString("TRN_TYPE")  + "      " + obj.getString("DEP_NAME");
//                      "    "
                    EMP_name[i] = obj.getString("EMP_NAME");
                    leave_date[i] = obj.getString("LEAVE_DATE");
                    Dep[i] = obj.getString("DEP_NAME");
                    Remarks[i] = obj.getString("REMARKS");
                    LEAVE_CODE[i]=obj.getString("LEAVE_CODE");
                    EMP_ID[i]=obj.getString("EMP_ID");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                aa = new ArrayAdapter<String>(this, R.layout.row, R.id.txt_title, EMP_NAME);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(Approval_Form.this, approval_form_2.class);
                        i.putExtra("EMP_NAME", EMP_name[position]);
                        i.putExtra("LEAVE_DATE", leave_date[position]);
                        i.putExtra("DEP_NAME", Dep[position]);
                        i.putExtra("REMARKS", Remarks[position]);
                        i.putExtra("LEAVE_CODE",LEAVE_CODE[position]);
                        i.putExtra("EMP_ID",EMP_ID[position]);
                        startActivity(i);


                    }
                });
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView.setAdapter(arrayAdapter);
            }
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(Approval_Form.this, Chekcbox_Adapter_listView.class);
                    startActivity(i);
                    return false;
                }
            });


//            ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.row,R.id.txt_title,EMP_NAME);
//            listView.setAdapter(aa);

//            if (listView != null) {
//                Toast.makeText(Approval_Form.this, listView.getAdapter().getCount() + "", Toast.LENGTH_SHORT).show();
//                if (listView.getAdapter().getCount() > 0) {
//                    notification();
//                    progressdialog.hide();
//                }
//            }
//        } catch (NullPointerException e) {
//            Toast.makeText(getApplicationContext(), "Date Fetching Error...!!!", Toast.LENGTH_SHORT).show();

    }catch (NullPointerException e){

        }
    }
}

