package com.example.mis_internee.atendence_app_android.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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
import com.example.mis_internee.atendence_app_android.Model.hr_notification_model;
import com.example.mis_internee.atendence_app_android.Adapter.hr_notification_adapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class hr_leave_notification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String emp_id;
    private String showUrl;
    hr_notification_model emp;
    private RequestQueue requestQueue;
    ArrayList<hr_notification_model> arrayList = new ArrayList<hr_notification_model>();
    String[] Type,T;
    ListView lv;
    private String[] FROM,TO,TYPE,STATUS;
    private String[] Dateee;
    private String[] ID;
    private ProgressDialog progressDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String [] a={"sdas","dasdsa","dsad"};

    private OnFragmentInteractionListener mListener;
    private String[] LEAVE_DATE,LEAVE_DESC,HR_REMARKS,HR_DATE;
    private hr_notification_adapter adapter;

    private ArrayList<String[]> list=new ArrayList<String[]>();
//    private ArrayAdapter adapter;
   String [] LEAVE_DESCC,LEAVE_BAL,LEAVE_CODE,LEAVE_ID;

    public hr_leave_notification() {
        // Required empty public constructor
    }

    public static hr_leave_notification newInstance(String param1, String param2) {
        hr_leave_notification fragment = new hr_leave_notification();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        lv=(ListView)view.findViewById(R.id.hrlv);
        User user= new User(getContext());
        emp_id=user.getEmp_id().toString();
        showUrl="http://scorpio.sgroup.pk:8085/atendence/HR_REMARKS_4_EMP.php?USER_ID="+emp_id;
        getData();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hr_leave_notification, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void getData(){
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("LeavesResult");
                    ID=new String[jsonArray.length()];
                    LEAVE_DATE = new String[jsonArray.length()];
                    LEAVE_DESC = new String[jsonArray.length()];
                    HR_REMARKS = new String[jsonArray.length()];
                    HR_DATE = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ID[i]=(i+1)+"";
                        LEAVE_DATE[i]=obj.getString("LEAVE_DATE");
                        LEAVE_DESC[i]=obj.getString("LEAVE_DESC");
                        HR_REMARKS[i]=obj.getString("HR_REMARKS");
                        HR_DATE[i]=obj.getString("HR_TRN_DATE");

                        emp = new hr_notification_model( LEAVE_DATE[i],LEAVE_DESC[i],HR_REMARKS[i],HR_DATE[i]);
                        arrayList.add(emp);
                    }

                    adapter = new hr_notification_adapter(getContext(), arrayList);
//                    adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1 ,a);
                    lv.setAdapter(adapter);
                    Collections.sort(arrayList, new ListComparator());
                }
                catch (JSONException e) {
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
    public class ListComparator implements Comparator<hr_notification_model>
    {
        @Override
        public int compare(hr_notification_model left, hr_notification_model right) {
            return left.getLeave_date().compareTo(right.getLeave_date());
        }
    }
}
