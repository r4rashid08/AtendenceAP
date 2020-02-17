package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 13-Feb-18.
 */


import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mis_internee.atendence_app_android.R;

public class EMPLOYEE_CALENDAR_ADMIN_ADAPTER extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] achkin_time;
//    private final Integer[] imgid;

    public EMPLOYEE_CALENDAR_ADMIN_ADAPTER(Activity context, String[] itemname, String[] ACHKIN_TIME) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
//        this.imgid=imgid;
        this.achkin_time=ACHKIN_TIME;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.employee_calendar_admin_layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        ImageView stats=(ImageView)rowView.findViewById(R.id.stats);
        ImageView statsRed=(ImageView)rowView.findViewById(R.id.statss);
        if(achkin_time[position].equals("A")) {
            stats.setVisibility(View.GONE);
            statsRed.setVisibility(View.VISIBLE);
            txtTitle.setText(itemname[position]);
//            imageView.setImageResource(imgid[position]);
//            extratxt.setText("Status " + itemname[position]);
        }else{
            stats.setVisibility(View.VISIBLE);
            statsRed.setVisibility(View.GONE);
            txtTitle.setText(itemname[position]);
//            imageView.setImageResource(imgid[position]);
//            extratxt.setText("Status " + itemname[position]);
        }



        return rowView;

    };
}