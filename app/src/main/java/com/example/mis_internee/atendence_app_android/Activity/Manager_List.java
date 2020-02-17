package com.example.mis_internee.atendence_app_android.Activity;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Adapter.manager_list_item_text_image;
import com.example.mis_internee.atendence_app_android.Model.User;
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
public class Manager_List extends AppCompatActivity {
    TextView add;
    Context context;
    Button delete;
    SearchView searchVie;
    ListView LV;
    String name, id;
    RequestQueue requestQueue;
    ArrayList<String> emp_name = new ArrayList<String>();
    ArrayList<String> EMP_ID = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    String[] EM_NAME2;
    String[] EM_ID2;
    EditText ed;
    manager_list_item_text_image mng;
    private Button add_mngr;
    private ProgressDialog progressdialog;
    private int conter = 0;
    Integer imgid[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
//        getJSON("http://scorpio.sgroup.pk:8085/atendence/hr_manager.php");
        User user=new User(Manager_List.this);
        String adminEmp_id=user.getEmp_id().toString();
        getJSON2("http://scorpio.sgroup.pk:8085/atendence/hr_manager.php?EMP_ID="+adminEmp_id);
        LV = (ListView) findViewById(R.id.lisVtwq);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Manager List");
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

         requestQueue = Volley.newRequestQueue(getApplicationContext());
       LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       }
   });

    }



    private void getJSON2(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(Manager_List.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(false);
                }
                super.onPreExecute();
            }



            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressdialog.hide();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
         mng=new manager_list_item_text_image(this,EM_NAME2,EM_ID2);
//        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, EM_NAME2);
//       mng.notifyDataSetChanged();
        LV.setAdapter(mng);
        mng.notifyDataSetChanged();
        LV.invalidateViews();
        mng.notifyDataSetInvalidated();




    }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.emp_list, menu);
    MenuItem item = menu.findItem(R.id.action_search);
    MenuItem item2=menu.findItem(R.id.add_manager12);
    add_mngr=(Button) item2.getActionView();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    searchVie = (SearchView) item.getActionView();
     searchVie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    mng.getFilter().filter(newText);
                }
                catch (NullPointerException e){

                }
                return false;
            }
        });
    return super.onCreateOptionsMenu(menu);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_manager12) {
            finish();
            Intent i = new Intent(Manager_List.this,add_manager.class);
            startActivity(i);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    }
