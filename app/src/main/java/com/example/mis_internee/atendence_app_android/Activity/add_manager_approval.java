package com.example.mis_internee.atendence_app_android.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class add_manager_approval extends AppCompatActivity {
    RequestQueue requestQueue;
    Switch aSwitch;
    String emp_id;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Make Manager");
        actionBar.getThemedContext();
        Intent i = getIntent();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .4));
         emp_id=getIntent().getExtras().getString("EMP_ID");
         aSwitch=(Switch)findViewById(R.id.switch2);





    }
    public void getData(){
     String showUrl="http://scorpio.sgroup.pk:8085/atendence/update_manager.php?EMP_ID="+emp_id;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        showUrl,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        Toast.makeText(add_manager_approval.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray students = response.getJSONArray("students");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject student = students.getJSONObject(i);

                                String firstname = student.getString("EMP_NAME");

//                                result.append(firstname + " " + lastname + " " + age + " \n");
                            }
//                            result.append("===\n");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_manager_aproval, menu);
        MenuItem item = menu.findItem(R.id.gud);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.gud) {
if(aSwitch.isChecked()){
    getData();
    AlertDialog.Builder builder = new AlertDialog.Builder(add_manager_approval.this);
    builder.setMessage("Operation successfull..!!!").setNegativeButton("Go back", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
            Intent i = new Intent(add_manager_approval.this,Manager_List.class);
            startActivity(i);
        }
    }).create().show();


}
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    }



