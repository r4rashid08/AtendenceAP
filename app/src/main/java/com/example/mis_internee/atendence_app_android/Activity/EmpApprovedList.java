package com.example.mis_internee.atendence_app_android.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mis_internee.atendence_app_android.Adapter.EMP_APPROVED_ADAPTER_LIST;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmpApprovedList extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_approved_list);
        listView = (ListView) findViewById(R.id.approvedList);
        final ActionBar actionBar = getSupportActionBar();
        getJSON("http://scorpio.sgroup.pk:8085/atendence/approvedList.php" + "?ABH=" + "A");
        actionBar.setTitle("Approved List");
        actionBar.getDisplayOptions();


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
        if (json == null) {
            Intent i = new Intent(EmpApprovedList.this, MainActivity.class);
            startActivity(i);

        } else {
            JSONObject object = new JSONObject(json);
            final JSONArray jsonArray = object.getJSONArray("LeavesResult");
            final String[] EMP_NAME = new String[jsonArray.length()];
            final String[] EMP_name = new String[jsonArray.length()];
            final String[] leave_date = new String[jsonArray.length()];
            final String[] Dep = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);


                    EMP_NAME[i] = (i + 1) + "      " + obj.getString("EMP_NAME")+ "      " + obj.getString("DEP_NAME") + "    " + obj.getString("LEAVE_DATE");
                    EMP_name[i] = obj.getString("EMP_NAME");
                    leave_date[i] = obj.getString("LEAVE_DATE");
                    Dep[i] = obj.getString("DEP_NAME");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent i = new Intent(EmpApprovedList.this, Approval_Form.class);
//                        i.putExtra("EMP_NAME", EMP_name[position]);
//                        i.putExtra("LEAVE_DATE", leave_date[position]);
//                        i.putExtra("DEP_NAME", Dep[position]);
//                        startActivity(i);
//                    }
//                });

            }
            EMP_APPROVED_ADAPTER_LIST adapter= new EMP_APPROVED_ADAPTER_LIST(EmpApprovedList.this,EMP_NAME,EMP_name);
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
            listView.setAdapter(adapter);


        }
    }
}
