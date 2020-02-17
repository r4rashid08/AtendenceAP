package com.example.mis_internee.atendence_app_android.Adapter;

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


public class emp_approved_list_adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] posted;
    RequestQueue requestQueue;
    private String[] FROM;
    private String[] TO;
    private String[] TYPE;
    private String[] STATUS;
    private String[] datee;
    private String[] ID;
  
    String EMP_ID;
    SearchView searchView;

    ImageView imageView;

    public emp_approved_list_adapter(final Activity context, String[] FROM, String[] TO, String[] TYPE, final String[] STATUS, final String[] dateee, String[] ID, String[] posted) {
        super(context, R.layout.emp_approved_adapter_layout, FROM);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.FROM=FROM;
        this.TO=TO;
        this.STATUS=STATUS;
        this.TYPE=TYPE;
        this.datee=dateee;
        this.ID=ID;
        this.posted=posted;


    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.emp_approved_adapter_layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView DATES = (TextView) rowView.findViewById(R.id.leave_date);
        TextView type = (TextView) rowView.findViewById(R.id.type);
        TextView status = (TextView) rowView.findViewById(R.id.status);
        TextView IDD = (TextView) rowView.findViewById(R.id.id);
        TextView Posted = (TextView) rowView.findViewById(R.id.interfaces);


        DATES.setText(datee[position]);
        type.setText(TYPE[position]);
        status.setText(STATUS[position]);
        IDD.setText(ID[position]);
        Posted.setText(posted[position]);
        return rowView;


    };


}