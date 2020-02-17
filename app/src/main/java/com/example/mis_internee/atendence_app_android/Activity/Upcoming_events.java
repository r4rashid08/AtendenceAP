package com.example.mis_internee.atendence_app_android.Activity;

import android.os.AsyncTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Adapter.Event_Adapter;
import com.example.mis_internee.atendence_app_android.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Upcoming_events extends AppCompatActivity {
    ListView listView;
     String[] EVENTS;
     String FROM_DATE,TO_DATE,DESCRIPTION,PLACE_NAME;
    String[] status;
    Integer[] imgid;
    private RelativeLayout mRelativeLayout;
    private int day, month, year;
    String dae;
    ImageButton closeButton;
    ArrayList<String> EMPID = new ArrayList<String>();
    private Calendar c;
    String[] fr_date,to_date,GUEST;
    RequestQueue requestQueue;
    ImageView img1;
    String fr_date2,to_time;
    TextView tv1,TO_ID,details,plc_name;
    Switch simpleSwitch;
    String Status;
    ListView guest;
    private PopupWindow mPopupWindow;
    ArrayAdapter<String> adapter;

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault());
    private Integer[] Imgar;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Upcoming Events");
        actionBar.getDisplayOptions();
        listView=(ListView)findViewById(R.id.list_event);
        c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        dae = day + "-" + month + "-" + year;
        tv1=(TextView)findViewById(R.id.status12) ;
        img1=(ImageView)findViewById(R.id.gren);
        result=(TextView)findViewById(R.id.fromid);
        TO_ID=(TextView)findViewById(R.id.toid);
        details=(TextView)findViewById(R.id.Detailid);
        plc_name=(TextView)findViewById(R.id.place_name);
        guest=(ListView)findViewById(R.id.lvBs) ;

//        mRelativeLayout = (RelativeLayout) findViewById(R.id.r1);


        imgid = new Integer[]{R.drawable.default12, R.drawable.default12, R.drawable.default12};

        compactCalendarView = (CompactCalendarView) findViewById(R.id.calendarr);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                dae=dateFormatForDisplaying.format(dateClicked);
                Toast.makeText(Upcoming_events.this, dae, Toast.LENGTH_SHORT).show();
                getJSON("http://scorpio.sgroup.pk:8085/atendence/getEvents.php?FROM_DATE="+dae);


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                simpleSwitch = (Switch) findViewById(R.id.switch1);
                Toast.makeText(Upcoming_events.this, "dsadasdasdas", Toast.LENGTH_SHORT).show();
                 fr_date2=fr_date[position].toString();
                 to_time=to_date[position].toString();
                getData();

            }


        });

    }
            private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {

                    loadIntoListView(s);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("ERROR", "Error pasting data "+e.toString());
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        final JSONArray jsonArray = object.getJSONArray("LeavesResult");
        EVENTS = new String[jsonArray.length()];
        status = new String[jsonArray.length()];
        fr_date= new String[jsonArray.length()];
        to_date= new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject obj;

            try {
                obj = jsonArray.getJSONObject(i);
                EVENTS[i] =  (i+1)+"     "+obj.getString("EVENT_NAME");
                status[i] = obj.getString("EVENT_STAUS");
                 fr_date[i]=obj.getString("FROM_DATE");
                 to_date[i]=obj.getString("EVENT_TIME1");


                Status=status[i].toString();

            } catch (JSONException e) {
                Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }
        Event_Adapter adapter=new Event_Adapter(this, EVENTS, status);
        listView.setAdapter(adapter);
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, R.layout.listview_splitter, EVENTS);
//        listView.setAdapter(itemsAdapter);

    }
    public void getData(){
        String showUrl="http://scorpio.sgroup.pk:8085/atendence/getEvent_Time_Date.php"+ "?FROM_DATE=" + fr_date2 + "&&EVENT_TIME1=" + to_time;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        showUrl,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Upcoming_events.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray object = response.getJSONArray("LeavesResult");
                            for (int i = 0; i < object.length(); i++) {
                                JSONObject obj = object.getJSONObject(i);

                                FROM_DATE= obj.getString("FROM_DATE")+" "+obj.getString("EVENT_TIME1");
                                TO_DATE= obj.getString("END_DATE")+" "+obj.getString("EVENT_TIME2");
                                DESCRIPTION=obj.getString("EVENT_DESCRIPTION");
                                PLACE_NAME=obj.getString("PLACE_NAME");
                                result.setText(FROM_DATE + " \n");
                                TO_ID.setText(TO_DATE+"\n");
                                details.setText(DESCRIPTION);
                                plc_name.setText(PLACE_NAME);
                                GUEST[i]=obj.getString("EMP_ID");
//                                EMPID.add(GUEST+"\n");
//                                 String parts = EMPID.get(i);
//                                 guest.append(parts);
//                                 for(int j=0;j<=EMPID.size();j++){
//                                     guest.setText(parts[j]);
//                                 }

//                                Event_Adapter adapter=new Event_Adapter(Upcoming_events.this, GUEST, status);
//                                guest.setAdapter(adapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);

    }

}
