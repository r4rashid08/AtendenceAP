package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 13-Feb-18.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class emp_pending_list_adapter extends ArrayAdapter<String> {

    private final Activity context;
    RequestQueue requestQueue;
    private String[] FROM,TO,TYPE,STATUS,Datee,ID;
    String EMP_ID;
    SearchView searchView;

    ImageView imageView;

    public emp_pending_list_adapter(final Activity context, String[] FROM, String[] TO, String[] TYPE, final String[] STATUS, final String[] Dateee, String[] ID) {
        super(context, R.layout.emp_pending_list_adapter_layout, FROM);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.FROM=FROM;
        this.TO=TO;
        this.STATUS=STATUS;
        this.TYPE=TYPE;
        this.Datee=Dateee;
        this.ID=ID;




    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.emp_pending_list_adapter_layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView type = (TextView) rowView.findViewById(R.id.type);
        TextView status = (TextView) rowView.findViewById(R.id.status);
        TextView IDD = (TextView) rowView.findViewById(R.id.id);


        date.setText(Datee[position]);
        type.setText(TYPE[position]);
        status.setText(STATUS[position]);
        IDD.setText(ID[position]);
        return rowView;


    };


}