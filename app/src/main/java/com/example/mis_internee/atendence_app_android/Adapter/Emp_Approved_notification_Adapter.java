package com.example.mis_internee.atendence_app_android.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.example.mis_internee.atendence_app_android.R;

public class Emp_Approved_notification_Adapter extends ArrayAdapter<String> {

    private final Activity context;
    RequestQueue requestQueue;
    private String[] NAME,FLAG,DATE,TRN_DATE;
    String EMP_ID;
    SearchView searchView;

    ImageView imageView;

    public Emp_Approved_notification_Adapter(final Activity context, String[] NAME, String[] LEAVE_DESC, String[] leave_date,String[] trn_date) {
        super(context, R.layout.emp_approved_notification_adapter_layout, NAME);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.NAME=NAME;
        this.FLAG=LEAVE_DESC;
        this.DATE=leave_date;
        this.TRN_DATE=trn_date;




    }

    @SuppressLint("ViewHolder")
    @NonNull
    public View getView(final int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.emp_approved_notification_adapter_layout, null,true);

        TextView name =  rowView.findViewById(R.id.name);
        TextView flag =  rowView.findViewById(R.id.flags);
        TextView date =  rowView.findViewById(R.id.date12);
        TextView ondate =  rowView.findViewById(R.id.onDate);


        name.setText(NAME[position]);
        flag.setText(FLAG[position]);
        date.setText(DATE[position]);
        ondate.setText(TRN_DATE[position]);
        return rowView;


    };


}