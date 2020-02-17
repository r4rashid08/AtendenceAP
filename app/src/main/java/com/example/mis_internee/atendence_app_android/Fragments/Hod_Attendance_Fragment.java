package com.example.mis_internee.atendence_app_android.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mis_internee.atendence_app_android.Activity.Advance_leave_submission;
import com.example.mis_internee.atendence_app_android.Activity.Popup_Activity_dialog;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeaveModel;
import com.example.mis_internee.atendence_app_android.Model.Leaves.LeavesResult;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.example.mis_internee.atendence_app_android.Utils.Utility;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Call;


public class Hod_Attendance_Fragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "hodAtendance";
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<String> L_ID = new ArrayList<String>();
    ArrayList<String> L_CODE = new ArrayList<String>();
    private static Calendar cal;
    Context mContext;
    ArrayList<String> List = new ArrayList<String>();
    CompactCalendarView compactCalendar;
    String dae;
    ArrayList<String> FLAG = new ArrayList<String>();
    ExpandableRelativeLayout  expandableLayout2;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv111, tv12;
    private SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private ArrayList<String> LEAVE_DESC;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
    private ArrayList<String> LEAVE_BAL;
    String empCode, DEP_CODE, DESIG_CODE, SHIFT_CODE, UNIT_ID, UNIT_CODE, IMEI, DESIG_TYPE, DEP_ID, DESIG_ID,DUEDAYS;
    private ArrayList<String> ARRTIME;
    private ArrayList<String> OffTime;
    private ArrayList<String> Status;
    private CalendarView mCalendar;
    private TextView date1, date2;
    private int day, month, year;
    private Calendar c;
    boolean check1 = false;
    int check = 0;
    CardView mLeaveSummery;
    int cont = 0;
    private int d;
    private JSONObject object;
    private JSONArray jsonArray;
    private int countP = 0,countHU=0,countMTL=0,countPTL=0,countCPL=0, countA = 0, countAL = 0, countNS = 0, countLA = 0, countCL = 0, countWL = 0, countSL = 0, countOD = 0, countEL = 0, countGL = 0, countHF = 0, countSSL = 0;
    private TextView statusPresent;
    private TextView DAYSS, statusAbsent,statusCPL,statusHU,statusMTL, statusLA, statusAL,statusPTL, statusNS, statusWL, statusSL, statusHF, statusEL, statusML, statusCL, statusGL, statusOD, statusSSL;
    private TextView days, MONTHYEAR;
    private int countML = 0;
     Button btn, btn1;

     Event ev;
     List<Event> eventList = new ArrayList<>();
     ProgressDialog progressdialog;
    private int conter = 0;

    private int maxDay = 0;

    private int contee = 0;


    int num;
    String emp_id;
    int co;
    int maxDate;
    Toolbar toolbar;
    private Date dd;
    ProgressDialog progressDialog;
    private AsyncTask mMyTask;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth1 = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth2 = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth3 = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat CURRENTDATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private int max = 0;
    int cd = 0;
    private int DAY_OF_MONTH = 0;
    private String header_month;
    private String actual_month;
    private String Actual_Month;
    private TableLayout stk;
    private TableRow tbrow0;
    private TextView tv00, tv11, tv22, tv33;
    private TableRow tbrow;
    private TextView t1v, t2v, t3v, t4v;
    private String[] CODE, CONS, BAL, ALLOC;
    private String Actual_date;
    private String nDate;
    private String dayDifference;
    private String jsonResp=null;
   // private WifiManager wifi;

    public static String getFirstDateOfMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(cal.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_hod_atendence, parent, false);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         mContext = getActivity().getApplicationContext();
        statusPresent =  getActivity().findViewById(R.id.statusPresent);
        statusAbsent =  getActivity().findViewById(R.id.statusAbsent);
        TextView textView = (TextView) getActivity().findViewById(R.id.text);
        if (!isOnline()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please Check your internet connection...!!!").setNegativeButton("OK", null).create().show();
        }

        setHasOptionsMenu(true);
        String s = "HRIS";
        SpannableString ss1 = new SpannableString(s);
        stk = getActivity().findViewById(R.id.table_main);
        tv1 =  view.findViewById(R.id.LC);
        tv2 =  view.findViewById(R.id.allocation);
        tv3 =  view.findViewById(R.id.consumed);
        tv4 =  view.findViewById(R.id.balance);
        tv5 =  view.findViewById(R.id.LC2);
        tv6 =  view.findViewById(R.id.allocation2);
        tv7 =  view.findViewById(R.id.consumed2);
        tv8 =  view.findViewById(R.id.balance2);
        tv9 =  view.findViewById(R.id.LC3);
        tv10 =  view.findViewById(R.id.allocation3);
        tv11 =  view.findViewById(R.id.consumed3);
        tv12 =  view.findViewById(R.id.balance3);
        statusLA =  view.findViewById(R.id.LA);
        statusNS =  getActivity().findViewById(R.id.NS);
        statusWL =  getActivity().findViewById(R.id.WL);
        statusSL = getActivity().findViewById(R.id.SL);
        statusHF = getActivity().findViewById(R.id.HF);
        statusEL =  getActivity().findViewById(R.id.EL);
        statusML =  getActivity().findViewById(R.id.ML);
        statusCL =  getActivity().findViewById(R.id.CL);
        statusGL =  getActivity().findViewById(R.id.GL);
        statusOD =  getActivity().findViewById(R.id.OD);
        MONTHYEAR =  getActivity().findViewById(R.id.mnth);
        DAYSS =  getActivity().findViewById(R.id.totalDays);
        statusPTL=  getActivity().findViewById(R.id.PTL);
        statusMTL=  getActivity().findViewById(R.id.MTL);
        statusHU= getActivity().findViewById(R.id.HU);
        statusCPL=  getActivity().findViewById(R.id.CPL);
        statusSSL=  getActivity().findViewById(R.id.SSL);
        compactCalendar =  getActivity().findViewById(R.id.calendar);
//        getdata();
        btn =view.findViewById(R.id.expandableButton1);
        btn1 =  getActivity().findViewById(R.id.expandableButton2);
        emp_id = getActivity().getIntent().getExtras().getString("emp_id");
        mLeaveSummery = view.findViewById(R.id.atndence_summery_view);
        statusPresent.setText("0");











        if (getActivity().getIntent().getExtras() != null) {
            empCode = getActivity().getIntent().getExtras().getString("EMP_CODE");
            DEP_CODE = getActivity().getIntent().getExtras().getString("DEP_CODE");
            DESIG_CODE = getActivity().getIntent().getExtras().getString("DESIG_CODE");
            SHIFT_CODE = getActivity().getIntent().getExtras().getString("SHIFT_CODE");
            DESIG_TYPE = getActivity().getIntent().getExtras().getString("DESIG_TYPE");
            DEP_ID = getActivity().getIntent().getExtras().getString("DEP_ID");
            UNIT_CODE = getActivity().getIntent().getExtras().getString("UNIT_CODE");
            UNIT_ID = getActivity().getIntent().getExtras().getString("UNIT_ID");
            IMEI = getActivity().getIntent().getExtras().getString("CREATED_BY");
            DESIG_ID = getActivity().getIntent().getExtras().getString("DESIG_ID");
            LEAVE_DESC = getActivity().getIntent().getExtras().getStringArrayList("LEAVE_DESC");
            LEAVE_ID = getActivity().getIntent().getExtras().getStringArrayList("LEAVE_ID");
            LEAVE_CODE = getActivity().getIntent().getExtras().getStringArrayList("LEAVE_CODE");
            LEAVE_BAL = Utility.LEAVE_BAL;

            DUEDAYS = getActivity().getIntent().getExtras().getString("DUE_DAYS");
        }


        c = Calendar.getInstance();
        dd = c.getTime();
        SimpleDateFormat local = new SimpleDateFormat("HH:mm:ss");
        String tim = local.format(dd);
        day = c.get(Calendar.DAY_OF_MONTH);

        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        dae = day + "-" + (month+1) + "-" + year;
        nDate=dae;
//        Toast.makeText(getContext(), dae, Toast.LENGTH_SHORT).show();
        maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        DAYSS.setText(maxDay + "");
        final String d = getFirstDateOfMonth(new Date());
        Date startDate = null;
        try {
            startDate = date.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date newDate = new Date();
        CharSequence tempDate  = DateFormat.format("d-MMM-yyyy ", newDate.getTime());
        String dk = tempDate.toString();

        Log.d("testing_date",dk);

        if(Utility.isNetworkAvailable()) {
            hod_attendance(emp_id, dk);
            getJSON_2("http://scorpio.sgroup.pk:8085/atendence/leavbalance.php" + "?emp_id=" + emp_id, 2);
        } else {
            Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
        }


        compactCalendar.showNextMonth();
        compactCalendar.showCalendar();
        compactCalendar.showPreviousMonth();
        compactCalendar.shouldScrollMonth(true);
        header_month = dateFormatForMonth1.format(compactCalendar.getFirstDayOfCurrentMonth());
        MONTHYEAR.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

        final Date finalStartDate = startDate;

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Bundle bndl = new Bundle();
                dae = date.format(dateClicked);

                try{
                    Date date1;
                    Date date2;

//                    SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

//Setting dates
                    date1 = CURRENTDATE.parse(nDate);
                    date2 = CURRENTDATE.parse(dae);

//Comparing dates
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);

//Convert long to String
                    dayDifference = Long.toString(differenceDates);

                    Log.e("HERE","HERE: " + dayDifference);
//                    Toast.makeText(getContext(), "days:"+dayDifference, Toast.LENGTH_SHORT).show();
                }
                catch (Exception exception) {
                    Log.e("DIDN'T WORK", "exception " + exception);
                    Toast.makeText(getContext(), "Error:"+exception, Toast.LENGTH_SHORT).show();
                }
             //  Toast.makeText(getContext(), dae+"", Toast.LENGTH_SHORT).show();
              //  List<Event> events = compactCalendar.getEvents(dateClicked);
                Utility.countLA = countSL;
                Intent i = new Intent(getActivity(), Popup_Activity_dialog.class);
                i.putExtra("date", dae);
                i.putExtra("countSl",countSL);
                i.putExtra("EMP_ID", emp_id);
                bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
                bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
                bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
                bndl.putStringArrayList("LEAVES", LEAVE_BAL);
                i.putExtra("DEP_CODE", DEP_CODE);
                i.putExtra("DESIG_CODE", DESIG_CODE);
                i.putExtra("SHIFT_CODE", SHIFT_CODE);
                i.putExtra("DESIG_TYPE", DESIG_TYPE);
                i.putExtra("DEP_ID", DEP_ID);
                i.putExtra("DESIG_ID", DESIG_ID);
                i.putExtra("EMP_CODE", empCode);
                i.putExtra("UNIT_CODE", UNIT_CODE);
                i.putExtra("UNIT_ID", UNIT_ID);
                i.putExtra("CREATED_BY", IMEI);
                i.putExtra("ACHKIN_TIME", ARRTIME);
                i.putExtra("ACHKOUT_TIME", OffTime);
                i.putExtra("ATTND_FLAG", Status);
                i.putExtra("DUE_DAYS", DUEDAYS);
                i.putExtra("DUE_mDATE", dayDifference);
                i.putExtras(bndl);
                startActivity(i);

            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                cd--;
                String abvd = dateFormatForMonth2.format(compactCalendar.getFirstDayOfCurrentMonth());
                String year1 = dateFormatForMonth3.format(compactCalendar.getFirstDayOfCurrentMonth());

                int iMonthh = 0;
                int iMonth = 0;

                iMonthh = Integer.parseInt(abvd);
                iMonth = iMonthh - 1;
                int iYear = Integer.parseInt(year1);
                int iDay = 1;
                Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, iYear);
                cal.set(Calendar.MONTH, iMonth);
                DAY_OF_MONTH = cal.getActualMaximum(cal.DAY_OF_MONTH);

                actual_month = dateFormatForMonth.format(firstDayOfNewMonth);
                Actual_Month = dateFormatForMonth1.format(firstDayOfNewMonth);








                MONTHYEAR.setText(actual_month);
                compactCalendar.removeAllEvents();
                statusNS.setText("0");
                statusAbsent.setText("0");
                statusCL.setText("0");
                statusEL.setText("0");
                statusGL.setText("0");
                statusHF.setText("0");
                statusLA.setText("0");
                statusML.setText("0");
                statusOD.setText("0");
                statusPresent.setText("0");
                statusSL.setText("0");
                statusWL.setText("0");
                statusSSL.setText("0");
                statusHU.setText("0");
                statusMTL.setText("0");
                statusPTL.setText("0");
                statusCPL.setText("0");


                countNS = 0;
                countA = 0;
                countAL = 0;
                countCL = 0;
                countEL = 0;
                countGL = 0;
                countHF = 0;
                countLA = 0;
                countML = 0;
                countOD = 0;
                countP = 0;
                countSL = 0;
                countWL = 0;
                countSSL = 0;
                countHU = 0;
                countMTL = 0;
                countPTL = 0;
                countCPL = 0;

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 0);
                max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                DAYSS.setText((DAY_OF_MONTH) + "");


                Date createDate = compactCalendar.getFirstDayOfCurrentMonth();
                CharSequence tempDate  = DateFormat.format("d-MMM-yyyy ", createDate.getTime());
                String dk = tempDate.toString();

                Log.d("testing_date",dk);

                hod_attendance(emp_id,dk);



            }

        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   expandableButton1();
                if (check==0) {
                      btn.setBackgroundColor(Color.BLUE);
                     btn.setTextColor(Color.WHITE);
                    mLeaveSummery.setVisibility(View.VISIBLE);
                    check =1;

                } else if(check==1) {
                    mLeaveSummery.setVisibility(View.GONE);
                      btn.setBackgroundColor(Color.GRAY);
                     btn.setTextColor(Color.WHITE);
                    check = 0;
                }

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableButton2();

                if (check1) {
                    btn1.setBackgroundColor(Color.BLUE);
                    btn1.setTextColor(Color.WHITE);
                    check1 = false;

                } else {
                    btn1.setBackgroundColor(Color.GRAY);
                    btn1.setTextColor(Color.WHITE);
                    check1 = true;
                }

            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }






   /* public void expandableButton1() {
        expandableLayout1 = (ExpandableRelativeLayout) getActivity().findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle();

    }*/

    public void expandableButton2() {
        expandableLayout2 = (ExpandableRelativeLayout) getActivity().findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse
    }


    private void getJSON_2(final String urlWebService, final int i) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                conter++;
                if (conter == 1) {
                    progressdialog = new ProgressDialog(getContext());
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(true);
                }

                super.onPreExecute();

            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                int  value = 0;
                if(s!=null){
                    jsonResp = s.toString();
                }else {

                    try {
                        progressDialog.dismiss();
                    }catch (Exception e){e.printStackTrace();}
//                    Toast.makeText(getContext(), "WHoops...!!! Network Disabled.", Toast.LENGTH_SHORT).show();


             /*       AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Internet Not Found :-( ");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Refresh",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if(isNetworkAvailable(getContext())==true) {
                                      //  if (wifi.isWifiEnabled() == true) {
                                            Intent intent = getActivity().getIntent();
                                            getActivity().finish();
                                            startActivity(intent);
                                       // }
                                     //   else{
                                   //         Toast.makeText(getActivity(), "Internet Is Disabled.!!!", Toast.LENGTH_SHORT).show();
                                     //   }
                                    }


                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();*/
}

                try {
                    if (i == 2) {

                        loadIntoListView2(s);
                    } else {
                    //    loadIntoListView(s);
                    }
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
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    @SuppressLint("NewApi")
    private void loadIntoListView2(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);
            final JSONArray jsonArray = object.getJSONArray("Result");
            CODE = new String[jsonArray.length()];
            CONS = new String[jsonArray.length()];
            ALLOC = new String[jsonArray.length()];
            BAL = new String[jsonArray.length()];
            TableLayout stk = (TableLayout) getActivity().findViewById(R.id.table_main);
            TableRow tbrow0 = new TableRow(getContext());
            TextView tv0 = new TextView(getContext());
            tv0.setText(" Leave code ");
            tv0.setTextColor(Color.WHITE);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(getContext());
            tv1.setText("  Allocation ");
            tv1.setTextColor(Color.WHITE);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(getContext());
            tv2.setText("   Consumed ");
            tv2.setTextColor(Color.WHITE);
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(getContext());
            tv3.setText("   Balance ");
            tv3.setTextColor(Color.WHITE);
            tbrow0.addView(tv3);
            stk.addView(tbrow0);
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;

                try {
                    obj = jsonArray.getJSONObject(i);
                    CODE[i] = obj.getString("LEAVE_CODE");
                    CONS[i] = obj.getString("CONSUMED");
                    ALLOC[i] = obj.getString("ALLOCATION");
                    BAL[i] = obj.getString("BALANCE");

//toString


//                for (int a = 0; a < 6; a++) {
                    TableRow tbrow = new TableRow(getContext());
                    TextView t1v = new TextView(getContext());
                    t1v.setText(  CODE[i].toString());
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(getContext());
                    t2v.setText( ALLOC[i].toString());
                    t2v.setTextColor(Color.WHITE);
                    t2v.setGravity(Gravity.LEFT);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(getContext());
                    t3v.setText(  CONS[i].toString());
                    t3v.setTextColor(Color.WHITE);
                    t3v.setGravity(Gravity.LEFT);
                    tbrow.addView(t3v);
                    TextView t4v = new TextView(getContext());
                    t4v.setText( BAL[i].toString());
                    t4v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t4v.setTextColor(Color.WHITE);
                    t4v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);
                    stk.addView(tbrow);

                    progressdialog.dismiss();



                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EMP_NAME);
//        lv.setAdapter(itemsAdapter);

        } catch (NullPointerException e) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                    Builder(getContext());
            alertDialogBuilder.setTitle("Network Error");
            alertDialogBuilder
                    .setMessage("Please Check Network..!!!")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    public void addLeave() {
        Intent i = new Intent(getContext(), Advance_leave_submission.class);
        i.putExtra("EMP_ID", emp_id);
        i.putExtra("DEP_CODE", DEP_CODE);
        i.putExtra("DESIG_CODE", DESIG_CODE);
//        i.putExtra("EMP_CODE",EMP_CODE);
        i.putExtra("SHIFT_CODE", SHIFT_CODE);
        i.putExtra("DEP_ID", DEP_ID);
        i.putExtra("DESIG_TYPE", DESIG_TYPE);
        i.putExtra("DESIG_ID", DESIG_ID);
        i.putExtra("countSl",countSL);

        Bundle bndl = new Bundle();
        bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
        bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
        bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
//        bndl.putStringArrayList("ACHKIN_TIME", Arrival_time);
//        bndl.putStringArrayList("ACHKOUT_TIME", Off_time);
        bndl.putStringArrayList("ATTND_FLAG", Status);

        startActivity(i);
    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void hod_attendance(String emp_id, final String date) {


     //   showProgress();
        //JsonObject param = RequestUtil.getLoginRequest(mEmail.getText().toString(),mPassword.getText().toString());
        final Call<LeaveModel> call = ServerTask.getInstance().getServices().hod_attandance(emp_id,date);

        call.enqueue(new ServerCallback<LeaveModel>() {
            @Override
            public void onFailure(ServerError restError) {
            //    hideProgress();
            }

            @Override
            public void onSuccess(retrofit2.Response<LeaveModel> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<LeaveModel> response) {


                try{

                assert response.body() != null;
                List<LeavesResult> modelList  = response.body().getLeavesResult();
                FLAG.clear();



                compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);

                for( int i = 0 ; i<modelList.size(); i++) {


                    LeavesResult leavesResult = modelList.get(i);
                    String value = leavesResult.getLEAVESTATUS();
                    FLAG.add(value);
                    Log.d("leaves", value + " :" + "values : " + modelList.size());

                    StringTokenizer stringTokenizer = new StringTokenizer(value, ",");
                    String first = stringTokenizer.nextToken();
                    String second = stringTokenizer.nextToken();

                    String myDate = second;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                    Date date = null;
                    try {
                        date = sdf.parse(myDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millis = date.getTime();


                    if (first.equals("P")) {

                        ev = new Event(getResources().getColor(R.color.green), millis, "Present");
                        compactCalendar.addEvent(ev, true);
                        countP++;
                        statusPresent.setText(String.valueOf(countP));

                    }
                    if (first.equals("A")) {
                        ev = new Event(getResources().getColor(R.color.red), millis, "Absent");
                        compactCalendar.addEvent(ev, true);
                        countA++;
                        statusAbsent.setText(String.valueOf(countA));
                    }
                    if (first.equals("LA")) {
                        ev = new Event(getResources().getColor(R.color.absent), millis, "Late Arrival");
                        compactCalendar.addEvent(ev, true);
                        countLA++;
                        statusLA.setText(String.valueOf(countLA));
                    }
                    if (first.equals("NS")) {
                        try {
                            ev = new Event(getResources().getColor(R.color.black), millis, "Not Swap");
                            compactCalendar.addEvent(ev, true);
                            countNS++;
                            statusNS.setText(String.valueOf(countNS));
                        } catch (Exception e) {
                            Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (first.equals("AL")) {
                        try {
                            ev = new Event(getResources().getColor(R.color.colorAccent), millis, "Late Arrival");
                            compactCalendar.addEvent(ev, true);
                            countAL++;
                            statusAL.setText(String.valueOf(countAL));
                        } catch (Exception e) {
//                            Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (first.equals("CL")) {
                        ev = new Event(getResources().getColor(R.color.cl), millis, "Not Swap");
                        compactCalendar.addEvent(ev, true);
                        countCL++;
                        statusCL.setText(String.valueOf(countCL));
                    }
                    if (first.equals("WL")) {
                        try {
                            ev = new Event(getResources().getColor(R.color.darkbrown), millis, "Week End");
                            compactCalendar.addEvent(ev, true);
                            countWL++;
                            statusWL.setText(String.valueOf(countWL));
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Data Not loaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (first.equals("SL")) {
                        ev = new Event(Color.YELLOW, millis, "Short Leave");
                        compactCalendar.addEvent(ev, true);
                        countSL++;
                        statusSL.setText(String.valueOf(countSL));
                    }
                    if (first.equals("OD")) {
                        ev = new Event(getResources().getColor(R.color.OD), millis, "out door duty Leave");
                        compactCalendar.addEvent(ev, true);
                        countOD++;
                        statusOD.setText(String.valueOf(countOD));
                    }
                    if (first.equals("ML")) {
                        ev = new Event(Color.BLUE, millis, "Short Leave");
                        compactCalendar.addEvent(ev, true);
                        countML++;
                        statusML.setText(String.valueOf(countML));
                    }
                    if (first.equals("EL")) {
                        ev = new Event(getResources().getColor(R.color.EL), millis, "Short Leave");
                        compactCalendar.addEvent(ev, true);
                        countEL++;
                        statusEL.setText(String.valueOf(countEL));
                    }
                    if (first.equals("GL")) {
                        ev = new Event(getResources().getColor(R.color.GL), millis, "Gesture Leave");
                        compactCalendar.addEvent(ev, true);
                        countGL++;
                        statusGL.setText(String.valueOf(countGL));
                    }
                    if (first.equals("HF")) {
                        ev = new Event(getResources().getColor(R.color.Cyan), millis, "half  Leave");
                        compactCalendar.addEvent(ev, true);
                        countHF++;
                        statusHF.setText(String.valueOf(countHF));
                    }
                    if (first.equals("SSL")) {
                        ev = new Event(getResources().getColor(R.color.lightgrey), millis, "Special Short Leave");
                        compactCalendar.addEvent(ev, true);
                        countSSL++;
                        statusSSL.setText(String.valueOf(countSSL));
                    }
                    if (first.equals("PTL")) {
                        ev = new Event(getResources().getColor(R.color.light), millis, "Peternity Leave");
                        compactCalendar.addEvent(ev, true);
                        countPTL++;
                        statusPTL.setText(String.valueOf(countPTL));
                    }
                    if (first.equals("MTL")) {
                        ev = new Event(getResources().getColor(R.color.ML), millis, "Maternity Leave");
                        compactCalendar.addEvent(ev, true);
                        countMTL++;
                        statusMTL.setText(String.valueOf(countMTL));
                    }
                    if (first.equals("CPL")) {
                        ev = new Event(getResources().getColor(R.color.PL), millis, "Compensatory Leave");
                        compactCalendar.addEvent(ev, true);
                        countCPL++;
                        statusCPL.setText(String.valueOf(countCPL));
                    }
                    if (first.equals("H/U")) {
                        ev = new Event(getResources().getColor(R.color.colorAccent), millis, "Compensatory Leave");
                        compactCalendar.addEvent(ev, true);
                        countHU++;
                        statusHU.setText(String.valueOf(countHU));
                    }

                  /*  StringTokenizer st = new StringTokenizer(second, "-");
                    String ST = st.nextToken();
                    num = Integer.parseInt(ST);


                    String CurDate = new SimpleDateFormat("dd-MMM-yy").format(new Date());
                    String curDate = CurDate.toUpperCase();

                    if (num == DAY_OF_MONTH) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                    } else if (second.equals(curDate)) {

                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }*/


//DAYSS.setText(num+"");
                }

                }catch (Exception e){e.printStackTrace();}









            }



        });

    }
}