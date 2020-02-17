package com.example.mis_internee.atendence_app_android.Adapter;

/**
 * Created by MIS-Internee on 13-Feb-18.
 */


import android.app.Activity;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mis_internee.atendence_app_android.R;

public class Event_Adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] status;
//    private final Integer[] imgar;

    public Event_Adapter(Activity context, String[] itemname, String[] status) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.status=status;
//        this.imgar=imgar;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.eventlistview, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        TextView imageView = (TextView) rowView.findViewById(R.id.textView1);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        ImageView image=(ImageView)rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[position]);
//        imageView.setText(status[position]);
//        extratxt.setText("Status "+itemname[position]);
//        image.setImageResource(imgar[position]);
        return rowView;

    };
}