package com.example.mis_internee.atendence_app_android.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.mis_internee.atendence_app_android.Adapter.emp_approved_list_adapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Emp_Approved_list extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String showUrl;
    emp_approved_list_adapter emp;
    private RequestQueue requestQueue;
    String[] Type,T;
    ListView listv;
    private String[] FROM,TO,TYPE,STATUS;
    private String[] dateee;
    private String[] ID;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;
    private String[] posted;
    private ProgressDialog getdata;


    public Emp_Approved_list() {
        // Required empty public constructor
    }

   public static Emp_Approved_list newInstance(String param1, String param2) {
        Emp_Approved_list fragment = new Emp_Approved_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_emp__approved_list, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        String user_id=getActivity().getIntent().getExtras().getString("user_id");
        User user= new User(getContext());
        String id=user.getEmp_id().toString();
        showUrl="http://scorpio.sgroup.pk:8085/atendence/getNotificationsApproved.php?user_id="+id;
//
        listv=(ListView)getActivity().findViewById(R.id.lv123);
        getData();
    }
    public void getData(){
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
//        getdata = ProgressDialog.show(getContext(), "Collecting Information", "Please Wait", false, false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                getdata.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("Result");
                    ID=new String[jsonArray.length()];
                    FROM = new String[jsonArray.length()];
                    TO = new String[jsonArray.length()];
                    TYPE = new String[jsonArray.length()];
                    STATUS = new String[jsonArray.length()];
                    dateee = new String[jsonArray.length()];
                    posted = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ID[i]=(i+1)+"";
                        FROM[i]=obj.getString("FROM_USER");
                        TO[i]=obj.getString("TO_USER");
                        TYPE[i]=obj.getString("NOTIFICATION_TYPE");
                        STATUS[i]=obj.getString("NOTIFICATION_STATUS");
                        dateee[i]=obj.getString("LEAVE_DATE");
                        posted[i]=obj.getString("POSTED");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emp=new emp_approved_list_adapter(getActivity(),FROM,TO,TYPE,STATUS,dateee,ID,posted);
                listv.setAdapter(emp);

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
