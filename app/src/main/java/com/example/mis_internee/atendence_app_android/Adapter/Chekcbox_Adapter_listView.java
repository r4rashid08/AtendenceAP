package com.example.mis_internee.atendence_app_android.Adapter;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Chekcbox_Adapter_listView extends AppCompatActivity {
    ArrayList<String> selectedItems;
    String[] EMP_NAME;
    ListView listView;
    ArrayAdapter<String> aa;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekcbox__adapter_list_view);
        listView = (ListView) findViewById(R.id.lv);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        selectedItems = new ArrayList<String>();
        getJSON("http://192.168.7.121/atendence/emp_leaves_O_A.php" + "?TRN_TYPE=" + "D");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem);
                else
                    selectedItems.add(selectedItem);

            }
        });


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
//                    progressdialog.setCancelable(true);
//                }
//

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

            EMP_NAME = new String[jsonArray.length()];
            final String[] EMP_name = new String[jsonArray.length()];
            final String[] leave_date = new String[jsonArray.length()];
            final String[] Dep = new String[jsonArray.length()];
            final String[] Remarks = new String[jsonArray.length()];


            int i;
            for (i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);


                    EMP_NAME[i] = (i + 1) + "      " + obj.getString("EMP_NAME") + "      " + obj.getString("TRN_TYPE") + "      " + obj.getString("DEP_NAME") + "    " + obj.getString("LEAVE_DATE");
                    EMP_name[i] = obj.getString("EMP_NAME");
                    leave_date[i] = obj.getString("LEAVE_DATE");
                    Dep[i] = obj.getString("DEP_NAME");
                    Remarks[i] = obj.getString("REMARKS");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aa = new ArrayAdapter<String>(this, R.layout.row, EMP_NAME);
                listView.setAdapter(aa);
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Check Connection...!!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void showSelectedItems(View view){
        String selItems="";
        for(String item:selectedItems){
            if(selItems=="")
                selItems=item;
            else
                selItems+="/"+item;
        }
        Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();
    }
}