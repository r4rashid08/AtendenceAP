package com.example.mis_internee.atendence_app_android.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.Adapter.Emp_Approved_notification_Adapter;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Emp_Approved_notification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Emp_Approved_notification_Adapter ena;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView list;
    private ProgressDialog getdata;

    public Emp_Approved_notification() {
        // Required empty public constructor
    }

    public static Emp_Approved_notification newInstance(String param1, String param2) {
        Emp_Approved_notification fragment = new Emp_Approved_notification();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       list=(ListView)getActivity().findViewById(R.id.approved);
        getdata = ProgressDialog.show(getContext(), "Collecting Information", "Please Wait", false, false);
        User user= new User(getContext());
        String adminEmp_id=user.getEmp_id().toString();
       getJSON("http://scorpio.sgroup.pk:8085/atendence/closed_notification.php?EMP_ID="+adminEmp_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emp__approved_notification, container, false);
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
                getdata.dismiss();
//                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
            final String[] TRN_DATE = new String[jsonArray.length()];


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
                    TRN_DATE[i] = obj.getString("TRN_DATE");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ena=new Emp_Approved_notification_Adapter(getActivity(),EMP_name,LEAVE_DESC,leave_date,TRN_DATE);
                list.setAdapter(ena);
//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String idd =EMP_ID[position].toString();
//                        Toast.makeText(getActivity(), idd, Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getActivity(),approval_form_2.class);
////                        i.putExtra("EMP_NAME", EMP_name[position]);
//                        i.putExtra("LEAVE_DATE", leave_date[position]);
//                        i.putExtra("DEP_NAME", Dep[position]);
//                        i.putExtra("REMARKS", Remarks[position]);
//                        i.putExtra("LEAVE_CODE", LEAVE_DESC[position]);
//                        i.putExtra("EMP_ID", EMP_ID[position]);
//                        i.putExtra("LEAVE_CODE",LEAVE_CODE[position]);
//                        startActivity(i);
//                    }
//                });
            }

        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "No Data Found...!!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Make sure network is working...!!!", Toast.LENGTH_SHORT).show();
            getdata.dismiss();
//            progressdialog.hide();
        }

    }
}
