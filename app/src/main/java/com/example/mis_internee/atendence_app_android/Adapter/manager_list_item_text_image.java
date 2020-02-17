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
import com.example.mis_internee.atendence_app_android.Activity.Manager_List;
import com.example.mis_internee.atendence_app_android.R;

import java.util.HashMap;
import java.util.Map;

public class manager_list_item_text_image extends ArrayAdapter<String> {

    private final Activity context;
    RequestQueue requestQueue;
    private final String[] itemname;
    String EMP_ID;
    SearchView searchView;
    private final String[] EM_ID2;
    ImageView imageView;
//    private final Integer[] imgid;

    public manager_list_item_text_image(final Activity context, final String[] itemname, final String[] EM_ID2) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.EM_ID2=EM_ID2;


    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.manager_item_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
         imageView = (ImageView) rowView.findViewById(R.id.icon123);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
//        imageView.setImageResource(imgid[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Delete entry");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                 EMP_ID=EM_ID2[position].toString();
                 update();
                Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                ((Activity)context).startActivity(new Intent(getContext(), Manager_List.class));
//                ((Activity)context).startActivity(Intent.getIntent());



            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.show();
    }
});
        return rowView;


    };

    public void update(){
        requestQueue = Volley.newRequestQueue(getContext());

        String insertUrl="http://scorpio.sgroup.pk:8085/atendence/delete_manager.php"+ "?EMP_ID="+EMP_ID;
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                return parameters;
            }
        };
        requestQueue.add(request);

    }


}