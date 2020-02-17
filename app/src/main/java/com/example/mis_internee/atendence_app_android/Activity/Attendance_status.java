package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Adapter.LeavePending.LeavePendingAdopter;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeavePendingResult;
import com.example.mis_internee.atendence_app_android.Model.Leaves.Result;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.Utils.Utility;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.R;
import com.google.gson.Gson;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Attendance_status extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ImageView imageView;
    private ArrayList<String> LEAVE_DESC,LEAVE_BAL,LEAVE_CODE,LEAVE_ID;
    List<Result> mPendingResultList = new ArrayList<>();
    private String showUrl;
    LeavePendingAdopter mAdopter;
    RecyclerView mRecycleview;
   // Spinner mSpinner;
    int year= 0 ,value =0;
    SwipeRefreshLayout mRefresh;
    String empId, mMonthFinal ="" ;
    TextView mDatePicker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

      //  setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionbar = getSupportActionBar();

        actionbar.setTitle("EXCEPTION STATUS");
        mRecycleview = findViewById(R.id.pending_leave_recycleview);
        mRefresh =findViewById(R.id.swipe_refresh);
        mDatePicker = findViewById(R.id.leave_pending_date);
        User user= new User(Objects.requireNonNull(getApplicationContext()));
        empId = user.getEmp_id().toString();








       mDatePicker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new RackMonthPicker(Attendance_status.this)
                       .setLocale(Locale.ENGLISH)
                       .setPositiveButton(new DateMonthDialogListener() {
                           @Override
                           public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {

                               String tempMonth ;
                               int length = String.valueOf(month).length();
                               if(length==1){
                                   tempMonth = "0"+month;
                               }else {
                                   tempMonth =""+month;
                               }

                               Log.d("lenght", length+"");

                               mDatePicker.setText(tempMonth +"-"+year);
                                 mMonthFinal = tempMonth;
                                getData();

                           }
                       })
                       .setNegativeButton(new OnCancelMonthDialogListener() {
                           @Override
                           public void onCancel(AlertDialog dialog) {

                           }
                       }).show();


           }
       });



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);;
        mRecycleview.setLayoutManager(gridLayoutManager);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());


        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
                if (Utility.isNetworkAvailable()) {


                        GregorianCalendar date = new GregorianCalendar();
                        year = date.get(Calendar.YEAR);
                        Calendar cal = Calendar.getInstance();
                        mMonthFinal = (new SimpleDateFormat("MM").format(cal.getTime()));
                        Log.d("month_year", mMonthFinal +"-"+year);
                        mDatePicker.setText(mMonthFinal +"-"+year);

                        getData();


                } else {
                    mRefresh.setRefreshing(false);
                    Toast.makeText(Attendance_status.this, "No InterNet Available", Toast.LENGTH_SHORT).show();


                }
            }
        });





    }

    private void getData(){
        showUrl="http://scorpio.sgroup.pk:8085/atendence/getNotificationData.php?user_id="+empId+"&DATE="+ mMonthFinal +"-"+year;
        Log.d("year_month",showUrl);

        try {
            mPendingResultList.clear();
            mAdopter.updateList(mPendingResultList);
         //   mAdopter.notifyDataSetChanged();

        }catch (Exception e){e.printStackTrace();}

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                mRefresh.setRefreshing(false);

                Gson g = new Gson();
                LeavePendingResult p = g.fromJson(response.toString(), LeavePendingResult.class);

                mPendingResultList.addAll(p.getResult());
                mAdopter = new LeavePendingAdopter(mPendingResultList);
                mRecycleview.setAdapter(mAdopter);
                mAdopter.notifyDataSetChanged();






//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


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
    public void onRefresh() {


            GregorianCalendar date = new GregorianCalendar();
            int tempMOnth = date.get(Calendar.MONTH);
            year = date.get(Calendar.YEAR);
            Calendar cal = Calendar.getInstance();
            mMonthFinal = (new SimpleDateFormat("MM").format(cal.getTime()));

            mDatePicker.setText(mMonthFinal +"-"+year);
            Log.d("month_year", mMonthFinal +"-"+year);
            getData();


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}