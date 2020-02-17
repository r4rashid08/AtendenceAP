package com.example.mis_internee.atendence_app_android.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.widget.SearchView;
import android.graphics.Typeface;
import android.os.AsyncTask;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// There Are Two GSON FUNCTIONS LOADVIEW
public class add_manager extends AppCompatActivity {
    TextView add;
    Context context;
    Button delete;
    private ProgressDialog progressdialog;
    private int conter = 0;
    SearchView searchVie;
    ListView LV;
    String name, id;
    String employeeUrl = "http://192.168.7.121/atendence/hr_manager.php";
    String deleteURL="http://192.168.6.98/atendence/delete.php";
    RequestQueue requestQueue;
    ArrayList<String> emp_name = new ArrayList<String>();
    ArrayList<String> EMP_ID = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    String[] EM_NAME2,EM_ID2;
    EditText ed;
    private String showUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
//        getJSON("http://scorpio.sgroup.pk:8085/atendence/hr_employe.php");
        getJSON2("http://scorpio.sgroup.pk:8085/atendence/hr_employe.php");
        LV = (ListView) findViewById(R.id.lisVtwq);
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Add Manager");
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           String asid=EM_ID2[position].toString();
          Intent i = new Intent(add_manager.this,add_manager_approval.class);
          i.putExtra("EMP_ID",asid);
                startActivity(i);
            }
        });


    }



    private void getJSON2(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(add_manager.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(false);
                }
                super.onPreExecute();
            }



            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                progressdialog.hide();
                try {

                    loadIntoListView2(s);
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

    private void loadIntoListView2(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);
            final JSONArray jsonArray = object.getJSONArray("LeavesResult");
            EM_NAME2 = new String[jsonArray.length()];
            EM_ID2 = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);

                    EM_NAME2[i] = (i + 1) + "                 " + obj.getString("EMP_NAME");
                    EM_ID2[i] = obj.getString("EMP_ID");
                    id = EM_ID2[i].toString();
                    name = EM_NAME2[i].toString();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EM_NAME2);
            LV.setAdapter(arrayAdapter);

        }
        catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(), "Check Network or Restart your Application", Toast.LENGTH_SHORT).show();
        }


    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                add_manager.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                emp_name.remove(deletePosition);
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter.notifyDataSetInvalidated();


            }
        });
    }
    private void delete(){

        arrayAdapter.remove(EM_NAME2.toString());
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
    }
//        else {
//            Toast.makeText(getApplicationContext(),"Nothing to Deleted",Toast.LENGTH_LONG).show();
//        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_manager_list, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        searchVie = (SearchView) item.getActionView();

        searchVie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    arrayAdapter.getFilter().filter(newText);
                }
                catch (NullPointerException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(add_manager.this);
                    builder.setMessage("No Record Found....!!!").setNegativeButton("Go back", null).create().show();

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}

