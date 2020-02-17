package com.example.mis_internee.atendence_app_android.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.example.mis_internee.atendence_app_android.BuildConfig;
import com.example.mis_internee.atendence_app_android.Fragments.Hod_Attendance_Fragment;
import com.example.mis_internee.atendence_app_android.Model.FcmModel.FcmResult;
import com.example.mis_internee.atendence_app_android.Model.Version.VersionModel;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

import static com.example.mis_internee.atendence_app_android.Activity.Splash_Activity.hasPermissions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String leaveUrl = "http://192.168.7.8:8085/atendence/hr_leave.php";
    String EmployeUrl;
    String DEP_CODE, APP_FLAG;
    String name;
//    ArrayList<String> L_ID = new ArrayList<>();
    ArrayList<String> Arrival_time = new ArrayList<>();
    ArrayList<String> Off_time = new ArrayList<>();
    ArrayList<String> Status = new ArrayList<>();
    private ArrayList<String> LEAVE_DESC;
    private ArrayList<String> LEAVE_BAL;
    private ArrayList<String> LEAVE_ID;
    private ArrayList<String> LEAVE_CODE;
     TextView TV;
    private Handler mHandler;
    private int navItemIndex = 0;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private String TAG_HOME = "home";
    private String CURRENT_TAG = TAG_HOME;
    private NavigationView navigationView;
  //  ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;
    private String DESIG_CODE, EMP_CODE, SHIFT_CODE, DEP_ID, DESIG_TYPE, DESIG_ID, UNIT_ID, UNIT_CODE, IMEI;
     String IS_MANAGER,MANAGER_CODE;
    TelephonyManager tel;
    TextView userEmail;
    String EMP_ID, asb;
    private PackageInfo infor;
    String mobileVersion ="",versionName="";
    PackageManager managers;
    private String DUE_DAYS;
    private TextView regAsets;
    String version_no;
    private ProgressDialog getdata;


    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Employee Calendar");
        drawer =  findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        mHandler = new Handler();



         User user = new User(getApplicationContext());
         String ismanager  = user.getIS_MANAGER();
         final String emp_id = user.getEmp_id();
      //  if(ismanager.trim().equals("Y")){

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.d("FCM_TOKEN", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast

                            Log.d("FCM_TOKEN", token);
                           FcmToken(emp_id,token);
                        }
                    });
       // }
        /*else if(ismanager.equals("N")){
            Toast.makeText(this, "not manager ", Toast.LENGTH_SHORT).show();
        }*/


          InsertLeaveBlance(emp_id);


        EMP_ID = Objects.requireNonNull(getIntent().getExtras()).getString("emp_id");
        EmployeUrl = "http://scorpio.sgroup.pk:8085/atendence/select.php" + "?emp_id=" + EMP_ID;

        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        userEmail=(TextView)findViewById(R.id.userEmail);
        String USERNAME = getIntent().getExtras().getString("username");
     //   User user = new User(MainActivity.this);
        asb = user.getUSERNAME();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userEmail = (TextView) headerView.findViewById(R.id.userEmail);
        userEmail.setText(asb);

       /* Task<InstanceIdResult> instanceIdResultTask = FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
               token = instanceIdResult.getToken();
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server
            }
        });
//        Version=getIntent().getExtras().getString("version");*/
        getJSON();
        managers = this.getPackageManager();
        infor = null;
        try {
            infor = managers.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        regAsets=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.update));

        int Permission_All = 1;

        String[] Permissions = {Manifest.permission.READ_PHONE_STATE,};
        if (!hasPermissions(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            IMEI = tel.getDeviceId();
        }

        try {
            name = getIntent().getExtras().getString("emp_id");
        } catch (NullPointerException e) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setMessage("You Need restart Your app").setNegativeButton("OK", null).create().show();

        }

        Intent ii = getIntent();
        String manager = ii.getStringExtra("IS_MANAGER");
        DESIG_CODE = getIntent().getExtras().getString("DESIG_CODE");
        EMP_CODE = getIntent().getExtras().getString("EMP_CODE");
        SHIFT_CODE = getIntent().getExtras().getString("SHIFT_CODE");
        DEP_ID = getIntent().getExtras().getString("DEP_ID");
        DESIG_TYPE = getIntent().getExtras().getString("DESIG_TYPE");
        DESIG_ID = getIntent().getExtras().getString("DESIG_ID");
        DEP_CODE = getIntent().getExtras().getString("DEP_CODE");
        APP_FLAG = getIntent().getExtras().getString("APP_FLAG");
        UNIT_CODE = getIntent().getExtras().getString("UNIT_CODE");
        UNIT_ID = getIntent().getExtras().getString("UNIT_ID");
        DUE_DAYS = getIntent().getExtras().getString("DUE_DAYS");
        IS_MANAGER = getIntent().getExtras().getString("IS_MANAGER");
        MANAGER_CODE = getIntent().getExtras().getString("MANAGER_CODE");

        LEAVE_DESC = getIntent().getExtras().getStringArrayList("LEAVE_DESC");
        LEAVE_ID = getIntent().getExtras().getStringArrayList("LEAVE_ID");
        LEAVE_CODE = getIntent().getExtras().getStringArrayList("LEAVE_CODE");
        LEAVE_BAL = getIntent().getExtras().getStringArrayList("LEAVES");


            if (user.getIS_MANAGER().equals("N")) {
                hideItem();

        }
       /* else{
//            Toast.makeText(this, "Data Not Found\napplication will be restart", Toast.LENGTH_LONG).show();
//            finish();
        }*/
        TV = (TextView) findViewById(R.id.emp_id);
//        TV.setText(L_ID.toString());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setupNevigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            //CURRENT_TAG = TAG_HOME;
            loadHomeFragment();

        }

    }

    private void setupNevigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadHomeFragment() {
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        mHandler.post(mPendingRunnable);

        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
//        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        if (navItemIndex == 0) {
            return new Hod_Attendance_Fragment();
        }
        return new Hod_Attendance_Fragment();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                            MainActivity.super.finish();
                        }
                    }).create().show();
         //   super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.log) {
            logOut();
            Toast.makeText(getApplicationContext(), "Session End ", Toast.LENGTH_LONG).show();
            return true;
        }
       /* if (id == R.id.addLeave) {
            addLeave();
            Toast.makeText(getApplicationContext(), "Add Advance Leave", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hod_atendence) {
            Intent i = new Intent(MainActivity.this, Hod_Attendance_Fragment.class);
            i.putExtra("emp_id", name);
            i.putExtra("DEP_CODE", DEP_CODE);
            i.putExtra("DESIG_CODE", DESIG_CODE);
            i.putExtra("EMP_CODE", EMP_CODE);
            i.putExtra("SHIFT_CODE", SHIFT_CODE);
            i.putExtra("DEP_ID", DEP_ID);
            i.putExtra("DESIG_TYPE", DESIG_TYPE);
            i.putExtra("DESIG_ID", DESIG_ID);
            i.putExtra("UNIT_CODE", UNIT_CODE);
            i.putExtra("UNIT_ID", UNIT_ID);
            i.putExtra("DUE_DAYS", DUE_DAYS);
            i.putExtra("CREATED_BY", IMEI);
            Bundle bndl = new Bundle();
            bndl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
            bndl.putStringArrayList("LEAVE_ID", LEAVE_ID);
            bndl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
            bndl.putStringArrayList("LEAVES", LEAVE_BAL);
            bndl.putStringArrayList("ACHKIN_TIME", Arrival_time);
            bndl.putStringArrayList("ACHKOUT_TIME", Off_time);
            bndl.putStringArrayList("ATTND_FLAG", Status);


            i.putExtras(bndl);

//            startActivity(i);

        } else if (id == R.id.emp_atendence) {
            Intent i = new Intent(MainActivity.this, EmployeeAtendence.class);
            i.putExtra("EMP_ID", name);
            startActivity(i);
        }
     /*   else if (id == R.id.markAtnd) {
            Toast.makeText(getApplicationContext(), "Mark Attendance is pending.", Toast.LENGTH_SHORT).show();


        }*/
//         else if (id == R.id.addEvent) {
//            Intent i = new Intent(MainActivity.this, MapActivity.class);
//            i.putExtra("EMP_ID", empId);
//            startActivity(i);
//
//        } else if (id == R.id.events) {
//            Toast.makeText(this, "Working...", Toast.LENGTH_SHORT).show();
////            Intent i = new Intent(MainActivity.this, Upcoming_events.class);
//////            i.putExtra("EMP_ID",empId);
////            startActivity(i);
//
//        } else if (id == R.id.feedback) {
//            Toast.makeText(this, "Working...", Toast.LENGTH_SHORT).show();
////            Intent i =  new Intent(MainActivity.this, Feedback.class);
////            i.putExtra("EMP_ID",EMP_ID);
////            i.putExtra("USERNAME",asb);
////            startActivity(i);
//

//        }



      /*
        else if (id == R.id.reimb) {
            Intent i =  new Intent(MainActivity.this, ReimbursementForm.class);
            i.putExtra("EMP_ID",EMP_ID);
            i.putExtra("FROM_USER",asb);
            i.putExtra("NOTIFICATION_TYPE", "REIMBURSEMENT");
            i.putExtra("DEP_CODE", DEP_CODE);
            i.putExtra("DESIG_CODE", DESIG_CODE);
            i.putExtra("SHIFT_CODE", SHIFT_CODE);
            i.putExtra("DESIG_TYPE", DESIG_TYPE);
            i.putExtra("DEP_ID", DEP_ID);
            i.putExtra("DESIG_ID", DESIG_ID);
            i.putExtra("EMP_CODE", EMP_CODE);
            i.putExtra("UNIT_CODE", UNIT_CODE);
            i.putExtra("UNIT_ID", UNIT_ID);
            i.putExtra("CREATED_BY", IMEI);
            i.putExtra("ATTND_FLAG", Status);
            i.putExtra("TO_USER_ID", MANAGER_CODE);

            startActivity(i);

        }*/
        else if (id == R.id.update) {
//            Intent i = new Intent(MainActivity.this, Update_Application.class);
//            startActivity(i);
            if(mobileVersion.trim().equals(versionName)){
                Toast.makeText(getApplicationContext(), "Sorry already Version Updated", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent i = new Intent(this, Update_Application.class);
                startActivity(i);
            }

        }
        else if(id==R.id.emp_leave_status){
            Intent i = new Intent(MainActivity.this, Attendance_status.class);
            Bundle bndls=new Bundle();
            bndls.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
            bndls.putStringArrayList("LEAVE_ID", LEAVE_ID);
            bndls.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
            bndls.putStringArrayList("LEAVES", LEAVE_BAL);
            i.putExtras(bndls);
            i.putExtra("user_id",name);
            startActivity(i);
        }
//        else if(id==R.id.admin_leave_view){
//            Intent i = new Intent(MainActivity.this, Emp_notification.class);
////            startActivity(i);
//
//
//        }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void logOut() {
        User user = new User(MainActivity.this);
        user.removeUser();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

  /*  public void addLeave() {
        Intent i = new Intent(MainActivity.this, Advance_leave_submission.class);
        i.putExtra("EMP_ID", name);
        Bundle bdl = new Bundle();
        i.putExtra("EMP_CODE", EMP_CODE);
        i.putExtra("DESIG_CODE", DESIG_CODE);
        i.putExtra("SHIFT_CODE", SHIFT_CODE);
        i.putExtra("DEP_ID", DEP_ID);
        i.putExtra("DESIG_TYPE", DESIG_TYPE);
        i.putExtra("DESIG_ID", DESIG_ID);
        i.putExtra("DEP_CODE", DEP_CODE);
        i.putExtra("UNIT_CODE", UNIT_CODE);
        i.putExtra("UNIT_ID", UNIT_ID);
        i.putExtra("CREATED_BY", IMEI);
        bdl.putStringArrayList("LEAVE_ID", LEAVE_ID);
        bdl.putStringArrayList("LEAVE_CODE", LEAVE_CODE);
        bdl.putStringArrayList("LEAVE_DESC", LEAVE_DESC);
        bdl.putStringArrayList("LEAVES", LEAVE_BAL);
        i.putExtras(bdl);

        startActivity(i);

    }*/

    public void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.addEvent).setVisible(false);
        nav_Menu.findItem(R.id.emp_atendence).setVisible(false);
    }

 /*   public void getAlldata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                EmployeUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                Log.d("temp_temp",response.toString());
                try {
                    JSONArray employee = response.getJSONArray("Result");
                    for (int i = 0; i < employee.length(); i++) {
                        JSONObject empobj = employee.getJSONObject(i);
                        EMP_CODE = empobj.getString("EMP_CODE");
                        APP_FLAG = empobj.getString("APP_FLAG");
                        DEP_CODE = empobj.getString("DEP_CODE");
                        DESIG_CODE = empobj.getString("DESIG_CODE");
                        SHIFT_CODE = empobj.getString("SHIFT_CODE");
                        DESIG_TYPE = empobj.getString("DESIG_TYPE");
                        DEP_ID = empobj.getString("DEP_ID");
                        DESIG_ID = empobj.getString("DESIG_ID");
                        UNIT_ID = empobj.getString("UNIT_ID");
                        UNIT_CODE = empobj.getString("UNIT_CODE");
                        IS_MANAGER = empobj.getString("IS_MANAGER");
                        MANAGER_CODE = empobj.getString("MANAGER_CODE");
                        String Arrivl = empobj.getString("ACHKIN_TIME");
                        Arrival_time.add(Arrivl);
                        String status = empobj.getString("ATTND_FLAG");
                        Status.add(status);
                        String offTime = empobj.getString("ACHKOUT_TIME");
                        Off_time.add(offTime);

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


    }*/


      private void initializeCountDrawer(){
    //Gravity property aligns the spinnerText
    regAsets.setGravity(Gravity.CENTER_VERTICAL);
    regAsets.setTypeface(null, Typeface.BOLD);
    regAsets.setTextColor(getResources().getColor(R.color.colorAccent));
    regAsets.setText("1");
//        regAsets.setBackground(getResources().getDrawable(R.drawable.circle));

}

    private void getJSON() {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getdata = ProgressDialog.show(MainActivity.this, "Application Update Info", "Please Wait", false, false);
                getdata.setMax(500);


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getdata.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


                try {
                    if (s.equals("{\"Result\":[]}")) {

                        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                                Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Data Not Found");
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        MainActivity.this.finish();
                                    }
                                });
                        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (NullPointerException e) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                            Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Network Error...!!!");
                    alertDialogBuilder
                            .setMessage("Please Check Network Connection")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
//                                    Main2Activity.this.finish();
                                }
                            });
                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


                try {

                    loadIntoListView(s);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://scorpio.sgroup.pk:8085/atendence/getLatestVersion.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilders = new androidx.appcompat.app.AlertDialog.
                            Builder(MainActivity.this);
                    alertDialogBuilders.setTitle("Network Error");
                    alertDialogBuilders
                            .setMessage("Please Check Network..!!!")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
//                    AlertDialog alertDialogs = alertDialogBuilders.create();
//                    alertDialogs.show();

                }
                return  null;
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView(String json) throws JSONException {
        try {


            Gson g = new Gson();
            VersionModel versionModel = g.fromJson(json, VersionModel.class);

             versionName= versionModel.getVersionResult().get(0).getVERSION();
             mobileVersion= String.valueOf(BuildConfig.VERSION_CODE);

            if(versionName.equals(mobileVersion)){

                Toast.makeText(MainActivity.this, "Application already updated", Toast.LENGTH_SHORT).show();
            }else {
                initializeCountDrawer();

            }




        } catch (NullPointerException e) {
            final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.
                    Builder(MainActivity.this);
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
            androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }



    private void FcmToken(String emp_id, String token) {

        Call<FcmResult> call = ServerTask.getInstance().getServices().FcmToken(emp_id,token);

        call.enqueue(new ServerCallback<FcmResult>() {
            @Override
            public void onFailure(ServerError restError) {
                //   hideProgress();

            }

            @Override
            public void onSuccess(retrofit2.Response<FcmResult> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<FcmResult> response) {

                assert response.body() != null;

                try {
                    String status = response.body().getStatus();

                    String result = response.body().getResult();

                    Log.d("FCM_RESULT", result);
                }catch (Exception e){e.printStackTrace();}

               // Toast.makeText(MainActivity.this, status+ result, Toast.LENGTH_SHORT).show();




            }



        });

    }



    private void InsertLeaveBlance(String emp_id) {

        Call<FcmResult> call = ServerTask.getInstance().getServices().InsertLeaveBalance(emp_id);

        call.enqueue(new ServerCallback<FcmResult>() {
            @Override
            public void onFailure(ServerError restError) {
                //   hideProgress();

            }

            @Override
            public void onSuccess(retrofit2.Response<FcmResult> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<FcmResult> response) {

                assert response.body() != null;

                try {

                    String result = response.body().getResult();

                    Log.d("FCM_RESULT", result);
                }catch (Exception e){e.printStackTrace();}
                // Toast.makeText(MainActivity.this, status+ result, Toast.LENGTH_SHORT).show();




            }



        });

    }



}


