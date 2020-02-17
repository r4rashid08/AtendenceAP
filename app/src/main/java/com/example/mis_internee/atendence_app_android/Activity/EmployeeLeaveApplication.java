package com.example.mis_internee.atendence_app_android.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mis_internee.atendence_app_android.Adapter.EMP_LEAVE_ADAPTER;
import com.example.mis_internee.atendence_app_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EmployeeLeaveApplication extends AppCompatActivity {
    ListView listView;
    Button viewList, noti;
    private ProgressDialog progressdialog;
    private int conter = 0;
    ArrayList<String> selectedItems;
    String[] EMP_NAME;
    ArrayAdapter<String> aa;
    SearchView searchView;
    EMP_LEAVE_ADAPTER ELA;
    boolean[] checkedItems;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_leave_application);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Attendance Pending List");
        listView = (ListView) findViewById(R.id.lv);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        try {
//            checkedItems = new boolean[EMP_NAME.length];
//        }catch (NullPointerException e ){
//            AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeLeaveApplication.this);
//            builder.setMessage("No Application Found...!!!").setNegativeButton("Go back", null).create().show();
//
//        }
//        getJSON("http://192.168.7.121/atendence/emp_leaves_O_A.php" + "?TRN_TYPE=" + "D");\

        getJSON("http://scorpio.sgroup.pk:8085/atendence/emp_leaves_O_A.php");
//        viewList = (Button) findViewById(R.id.VAP);
//        selectedItems=new ArrayList<String>();
//
//        viewList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(EmployeeLeaveApplication.this, EmpApprovedList.class);
//                startActivity(i);
//            }
//        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items = {"English", "Chinese", "French", "German", "Italian", "Khmer"};
        //supply data itmes to ListView

        //set OnItemClickListener

        Advance_leave_submission ad = new Advance_leave_submission();


    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                conter++;

                if (conter == 1) {
                    progressdialog = new ProgressDialog(EmployeeLeaveApplication.this);
                    progressdialog.setMessage("Please Wait Loading data....");
                    progressdialog.show();
                    progressdialog.setCancelable(false);
                }


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
                    return null;
                }
            }

        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);
            final JSONArray jsonArray = object.getJSONArray("LeavesResult");
            if (jsonArray.length()==0) {
                progressdialog.hide();

                final ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.noatendace);
                setContentView(imageView);
            }
            EMP_NAME = new String[jsonArray.length()];
            final String[] EMP_name = new String[jsonArray.length()];
            final String[] leave_date = new String[jsonArray.length()];
            final String[] Dep = new String[jsonArray.length()];
            final String[] Remarks = new String[jsonArray.length()];
            final String[] LEAVE_CODE = new String[jsonArray.length()];
            final String[] EMP_ID = new String[jsonArray.length()];


            int i;
            for (i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;
                try {
                    obj = jsonArray.getJSONObject(i);


                    EMP_NAME[i] = (i + 1) + "      " + obj.getString("EMP_NAME") + "      " + obj.getString("DEP_NAME");
//                      "    "
                    EMP_name[i] = obj.getString("EMP_NAME");
                    leave_date[i] = obj.getString("LEAVE_DATE");
                    Dep[i] = obj.getString("DEP_NAME");
                    Remarks[i] = obj.getString("REMARKS");
                    LEAVE_CODE[i] = obj.getString("LEAVE_CODE");
                    EMP_ID[i] = obj.getString("EMP_ID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                aa = new ArrayAdapter<String>(this, R.layout.row, R.id.txt_title, EMP_NAME);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(EmployeeLeaveApplication.this, approval_form_2.class);
                        i.putExtra("EMP_NAME", EMP_name[position]);
                        i.putExtra("LEAVE_DATE", leave_date[position]);
                        i.putExtra("DEP_NAME", Dep[position]);
                        i.putExtra("REMARKS", Remarks[position]);
                        i.putExtra("LEAVE_CODE", LEAVE_CODE[position]);
                        i.putExtra("EMP_ID", EMP_ID[position]);
                        startActivity(i);
//                        startActivity(getIntent());


                    }
                });
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, EMP_NAME);
//                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                 ELA = new EMP_LEAVE_ADAPTER(this, EMP_NAME);
                listView.setAdapter(ELA);
                ELA.notifyDataSetChanged();
//                searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) EmployeeLeaveApplication.this);



            }
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(EmployeeLeaveApplication.this, "asd", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(EmployeeLeaveApplication.this);
                    mBuilder.setTitle(R.string.dialog_title);
                    mBuilder.setMultiChoiceItems(EMP_NAME, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                            if (isChecked) {
//                                mUserItems.add(position);
                            } else {
//                                mUserItems.remove((Integer.valueOf(position)));
                            }
                        }
                    });
                    mBuilder.setCancelable(false);
                    mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            String item = "";
                            for (int i = 0; i < mUserItems.size(); i++) {
                                item = item + EMP_NAME[mUserItems.get(i)];
                                if (i != mUserItems.size() - 1) {
                                    item = item + ", ";
                                }
                            }
//                            mItemSelected.setText(item);
                        }
                    });

                    mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false;
                                mUserItems.clear();
//                                mItemSelected.setText("");
                            }
                        }
                    });

                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();

                    return false;

                }
            });


//            ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.row,R.id.txt_title,EMP_NAME);
//            listView.setAdapter(aa);

            if (listView.getCount() > 0) {
                Toast.makeText(EmployeeLeaveApplication.this, listView.getAdapter().getCount() + "", Toast.LENGTH_SHORT).show();
                if (listView.getAdapter().getCount() > 0) {
                    notification();
                    progressdialog.hide();
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No Data Found...!!!", Toast.LENGTH_SHORT).show();
            progressdialog.hide();
        }

    }

    public void notification() {
        Intent myIntent = new Intent(EmployeeLeaveApplication.this, EmployeeLeaveApplication.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder notify = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentTitle("New Leave Notification")
                .setContentText("Click here to View");
        NotificationManager notifyMang = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMang.notify(1, notify.build());


    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.viewaprovedlist, menu);
//        MenuItem item = menu.findItem(R.id.ViewLitss);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.ViewLitss) {
//            Intent i = new Intent(EmployeeLeaveApplication.this, EmpApprovedList.class);
//            startActivity(i);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.viewaprovedlist, menu);
    MenuItem item = menu.findItem(R.id.action_search);

    searchView = (SearchView) item.getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            ELA.getFilter().filter(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            try {
            String text = newText;
            ELA.getFilter().filter(text);
            return false;


            }
            catch (NullPointerException e){
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeLeaveApplication.this);
                builder.setMessage("No Record Found....!!!").setNegativeButton("Go back", null).create().show();

            }
            return false;
        }
    });

    return super.onCreateOptionsMenu(menu);
}
    @Override
    public void onBackPressed() {
       super.onBackPressed();
       finish();

      startActivity(new Intent(this,EmployeeAtendence.class));
    }

}