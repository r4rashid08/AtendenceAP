package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 31-May-18.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mis_internee.atendence_app_android.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mis_internee.atendence_app_android.R;


public class emp_notification_adapter extends ArrayAdapter<String> {

    private final Activity context;
    RequestQueue requestQueue;
    private String[] NAME,FLAG,DATE,EMP_TRN_DATE;
    String EMP_ID;
    SearchView searchView;

    ImageView imageView;

    public emp_notification_adapter(final Activity context, String[] NAME, String[] LEAVE_DESC, String[] leave_date,String[] emp_trn_date) {
        super(context, R.layout.emp_notification_adapter_layout, NAME);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.NAME=NAME;
        this.FLAG=LEAVE_DESC;
        this.DATE=leave_date;
        this.EMP_TRN_DATE=emp_trn_date;




    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.emp_notification_adapter_layout, null,true);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView flag = (TextView) rowView.findViewById(R.id.flags);
        TextView date = (TextView) rowView.findViewById(R.id.date12);
        TextView onDate = (TextView) rowView.findViewById(R.id.onDate);


        name.setText(NAME[position]);
        flag.setText(FLAG[position]);
        date.setText(DATE[position]);
        onDate.setText(EMP_TRN_DATE[position]);
        return rowView;


    };


}