package com.example.mis_internee.atendence_app_android.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    ListView lv;
    RequestQueue requestQueue;
    ImageView img;
    EditText cmnt;
    String feedback,emp_id,emp_name;
    String [] EMP_ID,EMP_NAME,FEEDBACK;
    String [] MyDATA={"ASDSA","sadasd","ASD","fgdsf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        lv=(ListView)findViewById(R.id.fbList);
        img=(ImageView)findViewById(R.id.send);
        cmnt=(EditText)findViewById(R.id.cmt);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Feedback");

        getData();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=cmnt.getText().toString();
                emp_id=getIntent().getExtras().getString("EMP_ID");
                emp_name=getIntent().getExtras().getString("USERNAME");
                postData();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());


}

    public void getData(){

        String showUrl="http://scorpio.sgroup.pk:8085/atendence/hr_feedbacks_comments.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Feedback.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray students = response.getJSONArray("students");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);
                        EMP_ID[i] = student.getString("EMP_ID");
                        EMP_NAME[i]= student.getString("EMP_NAME");
                        FEEDBACK[i]= student.getString("FEEDBACK");

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
      ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,MyDATA);
      lv.setAdapter(arrayAdapter);
        requestQueue.add(jsonObjectRequest);


        //

    }
    public void postData(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String insertUrl="http://scorpio.sgroup.pk:8085/atendence/EMP_FEEDBACK.php";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("EMP_ID",emp_id);
                parameters.put("EMP_NAME",emp_name);
                parameters.put("FEEDBACK",feedback);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
