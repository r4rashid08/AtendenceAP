package com.example.mis_internee.atendence_app_android.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.hr_notification_model;
import com.example.mis_internee.atendence_app_android.Activity.subIsue;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class hr_notification_adapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<hr_notification_model> modellist;
    ArrayList<hr_notification_model> arrayList;
    private String mDate;
    private RequestQueue requestQueue;
    private String showUrl;
    private String LeaveData;
    private String leave_desc;
    private String leave_id,leave_code;
    private ArrayList<String> L_Desc= new ArrayList<String>();
    private ArrayList<String> L_code= new ArrayList<String>();
    private ArrayList<String> L_ID= new ArrayList<String>();
    private String emp_id;
    private String leave_bal;
    private ArrayList<String> L_bal= new ArrayList<String>();;

    //constructor
    public hr_notification_adapter(Context context, List<hr_notification_model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<hr_notification_model>();
        this.arrayList.addAll(modellist);
    }



    public class ViewHolder {
        TextView leave_date, leave_desc, remarks, hr_date;
        ImageView mIconIv;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.hr_notification_adapter_layout, null);

            //locate the views in row.xml
            holder.leave_date = view.findViewById(R.id.leave_date);
            holder.leave_desc = view.findViewById(R.id.leave_desc);
            holder.remarks = view.findViewById(R.id.remarks);
            holder.hr_date = view.findViewById(R.id.hr_date);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.leave_date.setText(modellist.get(postition).getLeave_date() );
        holder.leave_desc.setText(modellist.get(postition).getLeave_desc().trim());
        holder.remarks.setText(modellist.get(postition).getRemarks());
        holder.hr_date.setText(modellist.get(postition).getHr_date());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //code later
                String remarks = modellist.get(postition).getRemarks().toString();
                 mDate=modellist.get(postition).getLeave_date().toString();
                User user=new User(mContext);
                emp_id=user.getEmp_id().toString();
                getLeaveData(emp_id);
//                String Flag = modellist.get(postition).getFlag().toString();
//                Intent i = new Intent(mContext, Asset_List_Details.class);
//                i.putExtra("Bar_code", barcode);
//                i.putExtra("Flag", Flag);
//                mContext.startActivity(i);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                        Builder(mContext);
                    alertDialogBuilder.setTitle("HR REMARKS...!!!");
                    alertDialogBuilder.setIcon(R.drawable.alerticon);
                alertDialogBuilder
                        .setMessage(remarks)
                        .setCancelable(false).setPositiveButton("Resubmit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle bdl=new Bundle();
                        Intent i = new Intent(mContext, subIsue.class);

                        i.putExtra("date",mDate);
                        i.putExtra("EMP_ID",emp_id);
                        bdl.putStringArrayList("LEAVE_ID",L_ID);
                        bdl.putStringArrayList("LEAVE_CODE",L_code);
                        bdl.putStringArrayList("LEAVE_DESC",L_Desc);
                        bdl.putStringArrayList("LEAVES",L_bal);
                        i.putExtras(bdl);
                        mContext.startActivity(i);


                    }
                })
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });


        return view;
    }

    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (hr_notification_model model : arrayList) {
                if (model.getHr_date().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void getLeaveData(String id){
        showUrl="http://scorpio.sgroup.pk:8085/atendence/hr_leave.php" + "?emp_id=" + id;
        requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                LeaveData=response.toString();
//                Toast.makeText(Splash_Activity.this, response.toString()+"", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray Leaves = response.getJSONArray("LeavesResult");
                    for (int i = 0; i < Leaves.length(); i++) {
                        JSONObject leave = Leaves.getJSONObject(i);

                        leave_desc = leave.getString("LEAVE_DESC");
                        L_Desc.add(leave_desc);
                        leave_id = leave.getString("LEAVE_ID");
                        L_ID.add(leave_id);
                        leave_code = leave.getString("LEAVE_CODE");
                        L_code.add(leave_code);
                        leave_bal = leave.getString("LEAVES");
                        L_bal.add(leave_bal);

//                        result.append(leave_desc + " " + leave_id + " " + leave_code + " \n");
                    }
//                    result.append("===\n");

                } catch (JSONException e) {
                    ProgressDialog pDialog = new ProgressDialog(mContext);
                    pDialog.setMessage("Check Internet Connection...!!!");
                    pDialog.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                        Builder(mContext);
                alertDialogBuilder.setTitle("Network Error...!!!");
                alertDialogBuilder
                        .setMessage("Please Check Network Connection")
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
//                                    Main2Activity.this.finish();
                            }
                        });
                androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });
        requestQueue.add(jsonObjectRequest);



    }

}