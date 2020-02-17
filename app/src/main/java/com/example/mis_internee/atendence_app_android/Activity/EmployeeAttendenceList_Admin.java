package com.example.mis_internee.atendence_app_android.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeAttendenceList_Admin extends AppCompatActivity {
    ListView listView;
    EditText editText;
    SearchView sv;
    ArrayList<String> listItems;
    String[] Emp_cod, Emp_id;
    ArrayAdapter<String> adapter;
    String emp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_attendence_list__admin);
        listView = (ListView) findViewById(R.id.list_item);
        getJSON("http://192.168.6.98/atendence/hr_employe.php");

        sv=(SearchView)findViewById(R.id.e_txt);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Employee List");


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(EmployeeAttendenceList_Admin.this, Employee_Calendar_Admin.class);
                 i.putExtra("EMP_ID",emp_id);
                startActivity(i);
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
            Emp_cod = new String[jsonArray.length()];
            Emp_id = new String[jsonArray.length()];
            String[] DEmp_cod = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);

                    Emp_cod[i] = (i + 1) + "      " + obj.getString("EMP_ID") + "      " + obj.getString("EMP_NAME") + "      " + obj.getString("DEP_NAME");
                    DEmp_cod[i] = obj.getString("EMP_NAME");
                    Emp_id[i] = obj.getString("EMP_ID");
                    emp_id = Emp_id[i].toString();
                    listItems = new ArrayList<>(Arrays.asList(Emp_cod[i]));

//                adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DEmp_cod);
                    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Emp_cod);
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
        catch (NullPointerException e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeAttendenceList_Admin.this);
            builder.setMessage("Employees Not Found...!!!").setNegativeButton("Go back", null).create().show();
        }
    }


}
