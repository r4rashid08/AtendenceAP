package com.example.mis_internee.atendence_app_android.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
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
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.Utility;
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

import static com.example.mis_internee.atendence_app_android.Fragments.Hod_Attendance_Fragment.getFirstDateOfMonth;

public class Admin_Employee_Attendance extends AppCompatActivity {
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
    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;
    String LEAVE_CODE1, Consumed1, Allocation1, Balance1, LEAVE_CODE2, LEAVE_CODE3, Balance2, Balance3, Allocation2, Allocation3, Consumed2, Consumed3;
    private SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private ArrayList<String> LEAVE_DESC;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
    String empCode, DEP_CODE, DESIG_CODE, SHIFT_CODE, UNIT_ID, UNIT_CODE, IMEI, DESIG_TYPE, DEP_ID, DESIG_ID;
    private ArrayList<String> ARRTIME;
    private ArrayList<String> OffTime;
    private ArrayList<String> Status;
    private CalendarView mCalendar;
    private TextView date1, date2;
    private int day, month, year;
    private Calendar c;
    boolean check1 = false;
    boolean check = false;
    RelativeLayout rel;
    int cont = 0;
    private int d;
    private JSONObject object;
    private JSONArray jsonArray;
    private int countP = 0, countA = 0, countAL = 0, countNS = 0, countLA = 0, countCL = 0, countWL = 0, countSL = 0, countOD = 0, countEL = 0, countGL = 0, countHF = 0, countSSL = 0;
    private TextView statusPresent;
    private TextView DAYSS, statusAbsent, statusLA, statusAL, statusNS, statusWL, statusSL, statusHF, statusEL, statusML, statusCL, statusGL, statusOD, statusSSL;
    private TextView days, MONTHYEAR;
    private int countML = 0;
    private Button btn, btn1;

    private Event ev;
    private ProgressDialog progressdialog;
    private int conter = 0;
    private int x = 0;
    private int maxDay = 0;

    private int contee = 0;
    private TableLayout tl;
    private TableRow tr_head;
    int newCounter=0;
    MenuItem item;
    int num;
    String emp_id;
    int co;
    int maxDate;
    Toolbar toolbar;
    private Date dd;
    ProgressDialog progressDialog,progress;
    private AsyncTask mMyTask;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth2 = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth3 = new SimpleDateFormat("yyyy", Locale.getDefault());
    private int max=0;
    private int cd=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__employee__attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statusPresent = (TextView) findViewById(R.id.statusPresent);
        statusAbsent = (TextView)findViewById(R.id.statusAbsent);
        TextView textView = (TextView) findViewById(R.id.text);

        if (!Utility.isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Please Check your internet connection...!!!").setNegativeButton("OK", null).create().show();
        }

        String s = "Hello Everyone";
        SpannableString ss1 = new SpannableString(s);


//        ss1.setSpan(new RelativeSizeSpan(2f), 0,5, 0); // set size
//        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
////        ss1.setSpan(new StyleSpan(Spannable), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////        ss1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 21, ss1.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        TextView tv= (TextView)getActivity(). findViewById(R.id.spinnerText);
//        tv.setText(ss1);
//        tv.setTypeface(Typeface.MONOSPACE);


        tv1 = (TextView) findViewById(R.id.LC);
        tv2 = (TextView)findViewById(R.id.allocation);
        tv3 = (TextView) findViewById(R.id.consumed);
        tv4 = (TextView) findViewById(R.id.balance);
        tv5 = (TextView)findViewById(R.id.LC2);
        tv6 = (TextView)findViewById(R.id.allocation2);
        tv7 = (TextView)findViewById(R.id.consumed2);
        tv8 = (TextView) findViewById(R.id.balance2);
        tv9 = (TextView)findViewById(R.id.LC3);
        tv10 = (TextView)findViewById(R.id.allocation3);
        tv11 = (TextView)findViewById(R.id.consumed3);
        tv12 = (TextView)findViewById(R.id.balance3);
        statusLA = (TextView)findViewById(R.id.LA);
        statusAL = findViewById(R.id.anvel_leave);
        statusNS = (TextView) findViewById(R.id.NS);
        statusWL = (TextView)findViewById(R.id.WL);
        statusSL = (TextView) findViewById(R.id.SL);
        statusHF = (TextView)findViewById(R.id.HF);
        statusEL = (TextView)findViewById(R.id.EL);
        statusML = (TextView)findViewById(R.id.ML);
        statusCL = (TextView) findViewById(R.id.CL);
        statusGL = (TextView) findViewById(R.id.GL);
        statusOD = (TextView) findViewById(R.id.OD);
        MONTHYEAR = (TextView)findViewById(R.id.mnstha);
        DAYSS = (TextView) findViewById(R.id.days);

        compactCalendar = (CompactCalendarView)findViewById(R.id.calendar);
//        getdata();
        btn = (Button) findViewById(R.id.expandableButton1);
        btn1 = (Button)findViewById(R.id.expandableButton2);
        User user = new User(this);

        emp_id = getIntent().getExtras().getString("EMP_ID");


        if (getIntent().getExtras() != null) {
            empCode = getIntent().getExtras().getString("EMP_CODE");
            DEP_CODE = getIntent().getExtras().getString("DEP_CODE");
            DESIG_CODE = getIntent().getExtras().getString("DESIG_CODE");
            SHIFT_CODE =getIntent().getExtras().getString("SHIFT_CODE");
            DESIG_TYPE =getIntent().getExtras().getString("DESIG_TYPE");
            DEP_ID = getIntent().getExtras().getString("DEP_ID");
            UNIT_CODE = getIntent().getExtras().getString("UNIT_CODE");
            UNIT_ID =getIntent().getExtras().getString("UNIT_ID");
            IMEI = getIntent().getExtras().getString("CREATED_BY");
            DESIG_ID =getIntent().getExtras().getString("DESIG_ID");
            LEAVE_DESC =getIntent().getExtras().getStringArrayList("LEAVE_DESC");
            LEAVE_ID = getIntent().getExtras().getStringArrayList("LEAVE_ID");
            LEAVE_CODE =getIntent().getExtras().getStringArrayList("LEAVE_CODE");
        }
       /* date1 = (TextView) getActivity().findViewById(R.id.d1);
        date2 = (TextView) getActivity().findViewById(R.id.d2);*/
        c = Calendar.getInstance();
        dd = c.getTime();
        SimpleDateFormat local = new SimpleDateFormat("HH:mm:ss");
        String tim = local.format(dd);
        day = c.get(Calendar.DAY_OF_MONTH);

        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        dae = day + "-" + month + "-" + year;

//        toolbar.setTitle(dae.format(String.valueOf(compactCalendar.getFirstDayOfCurrentMonth())));
        maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        String d = getFirstDateOfMonth(new Date());
        Date startDate = null;
        try {
            startDate = date.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int co = 0; co <31; co++) {
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(Calendar.DATE, co);
            Date newDate = null;
            newDate = c.getTime();
            String passdate = date.format(newDate);
            if (passdate.equals(cal)) {
                break;
            } else {

                getJSON_2("http://scorpio.sgroup.pk:8085/atendence/selectDate.php" + "?date=" + passdate + "&&emp_id=" + emp_id, 1);


            }
        }
        getJSON_2("http://scorpio.sgroup.pk:8085/atendence/leavbalance.php" + "?emp_id=" + emp_id, 2);

        compactCalendar.showNextMonth();
        final int asadas = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        compactCalendar.showCalendar();
        compactCalendar.showPreviousMonth();
        compactCalendar.shouldScrollMonth(true);
        MONTHYEAR.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

        final Date finalStartDate = startDate;
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Bundle bndl = new Bundle();
                dae = date.format(dateClicked);
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Intent i = new Intent(getApplicationContext(), Popup_Activity_dialog.class);
                i.putExtra("date", dae);
                i.putExtra("EMP_ID", emp_id);
                bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
                bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
                bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
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
                i.putExtras(bndl);
                startActivity(i);

            }



            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                DAYSS.setText(maxDay+"");
                cd--;
                String abvd=dateFormatForMonth2.format(compactCalendar.getFirstDayOfCurrentMonth());
                String year1=dateFormatForMonth3.format(compactCalendar.getFirstDayOfCurrentMonth());

                int iMonthh=0;
                int iMonth=0;

                iMonthh= Integer.parseInt(abvd);
                iMonth=iMonthh-1;
                int iYear = Integer.parseInt(year1);
                // 1 (months begin with 0)
                int iDay = 1;
                Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, iYear);
                cal.set(Calendar.MONTH, iMonth);
                int DAY_OF_MONTH = cal.getActualMaximum(cal.DAY_OF_MONTH);


                //GregorianCalendar calendar = new GregorianCalendar();
                //Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                //int asdas = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

//                             int newCounter=0;
//                newCounter++;
//                if (newCounter == 1) {
//                    progressdialog = new ProgressDialog(getActivity());
//                    progressdialog.setMessage("Please Wait Loading data....");
//                    progressdialog.show();
//                    progressdialog.setCancelable(false);
//                }

                progress = new ProgressDialog(Admin_Employee_Attendance.this);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setIndeterminate(true);
                progress.show();
                String sad = dateFormatForMonth.format(firstDayOfNewMonth);

                MONTHYEAR.setText(sad);
                compactCalendar.removeAllEvents();
                compactCalendar.removeEvents(1523300400);
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
//                statusSSL.setText("0");


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

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 0);
                max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                DAYSS.setText((DAY_OF_MONTH)+"");

                for (co = 0; co < max; co++) {
                    c.setTime(firstDayOfNewMonth);
                    c.add(Calendar.DATE, co);
                    Date newDate = null;
                    newDate = c.getTime();

                    String passdatee = date.format(newDate);
                    if (passdatee.equals(cal)) {
                        break;
                    } else {

                        getJSON_2("http://scorpio.sgroup.pk:8085/atendence/selectDate.php" + "?date=" + passdatee + "&&emp_id=" + emp_id, 1);

                    }

                }


            }

        });


    }

    public void getdata() {
        String leaveUrl = "http://192.168.7.8:8085/atendence/hr_leave.php";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                leaveUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                try {
                    JSONObject job = new JSONObject(response);
                    JSONArray students = job.getJSONArray("LeavesResult");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject LEAVE = students.getJSONObject(i);

                        String LID = LEAVE.getString("LEAVE_ID"); //123456
                        L_ID.add(LID);
                        String LC = LEAVE.getString("LEAVE_CODE"); //LCDAWSAS
                        L_CODE.add(LC);
                        String LD = LEAVE.getString("LEAVE_DESC"); //CAS LIVE AMAZ
                        mylist.add(LD);


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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(strReq);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadIntoListView(String json) throws JSONException, ParseException {

        try {
            object = new JSONObject(json);
            jsonArray = object.getJSONArray("LeavesResult");
            if(jsonArray.length()!=0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    contee++;
                    String date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                    if (contee == Integer.parseInt(date)) {
//                    progressdialog.dismiss();
//                    Toast.makeText(getActivity(), "Data Loaded Successfully", Toast.LENGTH_SHORT).show();

                    }
                    final JSONObject obj;
                    try {
                        obj = jsonArray.getJSONObject(i);
                        String flage = obj.getString("LEAVESTATUS");
                        FLAG.clear();
                        FLAG.add(flage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                for (int i = 0; i < FLAG.size(); i++) {

                    String[] parts = FLAG.get(i).split(",");
                    String first = parts[0];
                    String second = parts[1];
                    String myDate = second;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                    Date date = sdf.parse(myDate);
                    long millis = date.getTime();


                    if (first.equals("P")) {

                        ev = new Event(Color.GREEN, millis, "Present");
                        compactCalendar.addEvent(ev, true);
                        countP++;
                        statusPresent.setText(String.valueOf(countP));
                    }
                    if (first.equals("A")) {
                        ev = new Event(Color.RED, millis, "Absent");
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
                            ev = new Event(getResources().getColor(R.color.colorAccent), millis, "annual leave ");
                            compactCalendar.addEvent(ev, true);
                            countAL++;
                            statusAL.setText(String.valueOf(countAL));
                        } catch (Exception e) {
                            Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (first.equals("CL")) {
                        ev = new Event(getResources().getColor(R.color.cl), millis, "caual leave");
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
                        ev = new Event(getResources().getColor(R.color.OD), millis, "out door duty");
                        compactCalendar.addEvent(ev, true);
                        countOD++;
                        statusOD.setText(String.valueOf(countOD));
                    }
                    if (first.equals("ML")) {
                        ev = new Event(Color.BLUE, millis, "medical Leave");
                        compactCalendar.addEvent(ev, true);
                        countML++;
                        statusML.setText(String.valueOf(countML));
                    }
                    if (first.equals("EL")) {
                        ev = new Event(getResources().getColor(R.color.EL), millis, "early left");
                        compactCalendar.addEvent(ev, true);
                        countEL++;
                        statusEL.setText(String.valueOf(countEL));
                    }
                    if (first.equals("GL")) {
                        ev = new Event(getResources().getColor(R.color.GL), millis, "general  Leave");
                        compactCalendar.addEvent(ev, true);
                        countGL++;
                        statusGL.setText(String.valueOf(countGL));
                    }
                    if (first.equals("HF")) {
                        ev = new Event(getResources().getColor(R.color.Cyan), millis, "half Leave");
                        compactCalendar.addEvent(ev, true);
                        countHF++;
                        statusHF.setText(String.valueOf(countHF));
                    }
                    if (first.equals("SSL")) {
                        ev = new Event(getResources().getColor(R.color.lightgrey), millis, "Special Short Leave");
                        compactCalendar.addEvent(ev, true);
                        countHF++;
                        statusHF.setText(String.valueOf(countHF));
                    }
                    StringTokenizer st = new StringTokenizer(second, "-");
                    String ST = st.nextToken();
                    num = Integer.parseInt(ST);
                    String CurDate = new SimpleDateFormat("dd-MMM-yy").format(new Date());
                    String curDate = CurDate.toUpperCase();
                    if (num >= 28) {
                        if (progress != null) {
                            progress.dismiss();
                        }
                    } else if (second.equals(curDate)) {
                        if (progress != null) {
                            progress.dismiss();
                        }
                    } else {
                        break;
                    }

//DAYSS.setText(num+"");

                }
            }

        } catch (NullPointerException e) {
            int i = 0;
            i++;
            if (i == 2) {
                Toast.makeText(getApplicationContext(), "Slow Network...!!!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage("Please Check your internet connection...!!!").setNegativeButton("OK", null).create().show();
                progressDialog.hide();

            } else {
//                System.exit(0);
            }

        }


//        btn.setBackgroundColor(Color.BLUE);
//        btn.setTextColor(Color.WHITE);
//        btn1.setBackgroundColor(Color.BLUE);
//        btn1.setTextColor(Color.WHITE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableButton1();
                if (check) {
                    btn.setBackgroundColor(Color.BLUE);
                    btn.setTextColor(Color.WHITE);
                    check = false;

                } else {
                    btn.setBackgroundColor(Color.GRAY);
                    btn.setTextColor(Color.WHITE);
                    check = true;
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

    public void expandableButton1() {
        expandableLayout1 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle();

    }

    public void expandableButton2() {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse
    }


    private void getJSON_2(final String urlWebService, final int i) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                newCounter++;
                if (newCounter == 1) {
                    progressdialog = new ProgressDialog(Admin_Employee_Attendance.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(false);
                }
                super.onPreExecute();

            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                try {
                    if (i == 2) {

                        loadIntoListView2(s);
                    } else {
                        loadIntoListView(s);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (ParseException e) {

//                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Network Failed", Toast.LENGTH_SHORT).show();
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

    private void loadIntoListView2(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);


            JSONArray jsonArray = object.getJSONArray("LeavesResult");
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj = jsonArray.getJSONObject(i);
                String code = obj.getString("LEAVE_CODE");
                String cons = obj.getString("CONSUMED");
                String Alloc = obj.getString("ALLOCATION");
                String Bal = obj.getString("BALANCE");
                List.add(code);
                List.add(cons);
                List.add(Alloc);
                List.add(Bal);
            }
            for (int i = 0; i < List.size(); i++) {
                if (List.get(i) != null) {
                    LEAVE_CODE1 = List.get(i);
                }
                if (List.get(i + 1) != null) {
                    Consumed1 = List.get(i + 1);
                }
                if (List.get(i + 2) != null) {
                    Allocation1 = List.get(i + 2);
                }
                if (List.get(i + 3) != null) {
                    Balance1 = List.get(i + 3);
                }
                if (List.get(i + 4) != null) {
                    LEAVE_CODE2 = List.get(i + 4);
                }
                if (List.get(i + 5) != null) {
                    Consumed2 = List.get(i + 5);
                }
                if (List.get(i + 6) != null) {
                    Allocation2 = List.get(i + 6);
                }
                if (List.indexOf(i + 7) != 0) {
                    Balance2 = List.get(i + 7);
                }

                if (List.size() > 8) {
                    LEAVE_CODE3 = List.get(i + 8);
                }
                if (List.size() > 9) {
                    Consumed3 = List.get(i + 9);
                }
                if (List.size() > 10) {
                    Allocation3 = List.get(i + 10);
                }
                if (List.size() > 11) {
                    Balance3 = List.get(i + 11);
                }
                i = i + 7;
                tv1.setText(LEAVE_CODE1);
                tv2.setText(Consumed1);
                tv3.setText(Allocation1);
                tv4.setText(Balance1);
                tv5.setText(LEAVE_CODE2);
                tv6.setText(Consumed2);
                tv7.setText(Allocation2);
                tv8.setText(Balance2);
                tv9.setText(LEAVE_CODE3);
                tv10.setText(Consumed3);
                tv11.setText(Allocation3);
                tv12.setText(Balance3);

                progressdialog.dismiss();
                progressdialog.hide();

                Toast.makeText(getApplicationContext(), "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
//            Toast.makeText(getActivity(), "Network error...!!!", Toast.LENGTH_SHORT).show();
        }


    }
/*

    public void addLeave() {
        Intent i = new Intent(getApplicationContext(), Advance_leave_submission.class);
        i.putExtra("EMP_ID", emp_id);
        i.putExtra("DEP_CODE", DEP_CODE);
        i.putExtra("DESIG_CODE", DESIG_CODE);
//        i.putExtra("EMP_CODE",EMP_CODE);
        i.putExtra("SHIFT_CODE", SHIFT_CODE);
        i.putExtra("DEP_ID", DEP_ID);
        i.putExtra("DESIG_TYPE", DESIG_TYPE);
        i.putExtra("DESIG_ID", DESIG_ID);

        Bundle bndl = new Bundle();
        bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
        bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
        bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
//        bndl.putStringArrayList("ACHKIN_TIME", Arrival_time);
//        bndl.putStringArrayList("ACHKOUT_TIME", Off_time);
        bndl.putStringArrayList("ATTND_FLAG", Status);

        startActivity(i);
    }

*/

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
