package com.example.mis_internee.atendence_app_android.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mis_internee.atendence_app_android.Activity.approval_form_2;
import com.example.mis_internee.atendence_app_android.Adapter.emp_notification_adapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Emp_Pending_notification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    ListView lv ;
    private RequestQueue requestQueue;
//    private String showUrl="http://scorpio.sgroup.pk:8085/atendence/emp_leaves_O_A.php";
    emp_notification_adapter ena;
    private String[] LEAVE_CODE,leave_date;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private ProgressDialog progressDialog;
    private ProgressDialog getdata;

    public Emp_Pending_notification() {

    }


    public static Emp_Pending_notification newInstance(String param1, String param2,String param3) {
        Emp_Pending_notification fragment = new Emp_Pending_notification();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_emp__pending_notification, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please wait Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setIndeterminate(true);
//        progressDialog.show();
        lv=view.findViewById(R.id.notification_lv);
        getdata = ProgressDialog.show(getContext(), "Collecting Information", "Please Wait", false, false);
        User user= new User(Objects.requireNonNull(getContext()));
        String adminEmp_id=user.getEmp_id();
        getJSON("http://scorpio.sgroup.pk:8085/atendence/leave_data.php?EMP_ID="+adminEmp_id);

    }
    private void getJSON(final String urlWebService) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                getdata.dismiss();
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
            final JSONArray jsonArray = object.getJSONArray("Result");
            if (jsonArray.length()==0) {
//
//                final ImageView imageView = new ImageView(getActivity());
//                imageView.setImageResource(R.drawable.noatendace);
//                getActivity().setContentView(imageView);
            }

            final String[] EMP_name = new String[jsonArray.length()];
            final String[] leave_date = new String[jsonArray.length()];
            final String[] Dep = new String[jsonArray.length()];
            final String[] Remarks = new String[jsonArray.length()];
            final String[] LEAVE_CODE = new String[jsonArray.length()];
            final String[] LEAVE_DESC = new String[jsonArray.length()];
            final String[] EMP_ID = new String[jsonArray.length()];
            final String[] LEAVE_REMARKS = new String[jsonArray.length()];
            final String[] EMP_TRN_DATE = new String[jsonArray.length()];



            int i;
            for (i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);

                    EMP_name[i] = obj.getString("EMP_NAME");
                    leave_date[i] = obj.getString("LEAVE_DATE");
                    Remarks[i] = obj.getString("REMARKS");
                    LEAVE_DESC[i] = obj.getString("LEAVE_DESC");
                    EMP_ID[i] = obj.getString("USER_ID");
                    LEAVE_CODE[i] = obj.getString("LEAVE_CODE");
                    Dep[i] = obj.getString("DEP_NAME");
                    LEAVE_REMARKS[i] = obj.getString("LEAVE_REMARKS");
                    EMP_TRN_DATE[i]=obj.getString("EMP_TRN_DATE");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ena=new emp_notification_adapter(getActivity(),EMP_name,LEAVE_DESC,leave_date,EMP_TRN_DATE);
                lv.setAdapter(ena);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String idd =EMP_ID[position].toString();
                        Toast.makeText(getActivity(), idd, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), approval_form_2.class);
//                        i.putExtra("EMP_NAME", EMP_name[position]);
                        i.putExtra("LEAVE_DATE", leave_date[position]);
                        i.putExtra("DEP_NAME", Dep[position]);
                        i.putExtra("REMARKS", Remarks[position]);
                        i.putExtra("LEAVE_CODE", LEAVE_DESC[position]);
                        i.putExtra("EMP_ID", EMP_ID[position]);
                        i.putExtra("LEAVE_CODE",LEAVE_CODE[position]);
                        i.putExtra("LEAVE_REMARKS",LEAVE_REMARKS[position]);
                        startActivity(i);
                    }
                });
           }

        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "No Data Found...!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Make sure network is working...!!!", Toast.LENGTH_SHORT).show();

            getdata.dismiss();
//            progressdialog.hide();
        }

    }
}
