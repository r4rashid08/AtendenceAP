package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 13-Feb-18.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.mis_internee.atendence_app_android.Activity.Feedback;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONObject;

public class feedback_adapter extends ArrayAdapter<String> {

    private final Activity context;
//    private final String[] emp_name;
    private final String[] emp_feedback;


    public feedback_adapter(Feedback context, int emp_name, String[] emp_feedback) {
        super(context, R.layout.feedback_adapter, emp_name);
        // TODO Auto-generated constructor stub

        this.context=context;
//        this.emp_name=emp_name;
        this.emp_feedback=emp_feedback;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.feedback_adapter, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
//        ImageView stats=(ImageView)rowView.findViewById(R.id.stats);

//        txtTitle.setText(emp_name[position]);
        extratxt.setText(emp_feedback[position]);
        return rowView;

    };
}