package com.example.mis_internee.atendence_app_android.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Adapter.emp_pending_list_adapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Emp_Pending_list extends Fragment {

    private String showUrl;
    private emp_pending_list_adapter emp;

    private ListView lv;
    private String[] FROM,TO,TYPE,STATUS;
    private String[] Dateee;
    private String[] ID;
     ProgressDialog progressDialog;

    public Emp_Pending_list() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_emp__pending_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        getData();
        User user= new User(Objects.requireNonNull(getContext()));
        String id= user.getEmp_id().toString();

        showUrl="http://scorpio.sgroup.pk:8085/atendence/getNotificationData.php?user_id="+id;
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setIndeterminate(true);
//        progressDialog.show();
        lv= view.findViewById(R.id.lv);
        getData();

    }
    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                try {

                    JSONArray jsonArray = response.getJSONArray("Result");
                        ID=new String[jsonArray.length()];
                        FROM = new String[jsonArray.length()];
                        TO = new String[jsonArray.length()];
                        TYPE = new String[jsonArray.length()];
                        STATUS = new String[jsonArray.length()];
                        Dateee = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ID[i]=(i+1)+"";
                        FROM[i]=obj.getString("FROM_USER");
                        TO[i]=obj.getString("TO_USER");
                        TYPE[i]=obj.getString("NOTIFICATION_TYPE");
                        STATUS[i]=obj.getString("NOTIFICATION_STATUS");
                        Dateee[i]=obj.getString("LEAVE_DATE");
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emp=new emp_pending_list_adapter(getActivity(),FROM,TO,TYPE,STATUS,Dateee,ID);
                lv.setAdapter(emp);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
