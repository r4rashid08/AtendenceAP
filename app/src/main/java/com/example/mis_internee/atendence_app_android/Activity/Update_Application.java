package com.example.mis_internee.atendence_app_android.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dcastalia.localappupdate.DownloadApk;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class Update_Application extends AppCompatActivity {

    ProgressDialog bar;
    private static String TAG = "MainActivity";
    private String AppVersion;
    private PackageInfo infor;
    private PackageManager managers;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_application);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update New Version");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView heading    = (TextView) findViewById(R.id.heading);
        Button   update_btn = (Button) findViewById(R.id.upDatebtn);
//        pakageInfo=infor.packageName.toString();
        managers = this.getPackageManager();
        infor = null;
        try {
            infor = managers.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AppVersion= String.valueOf(infor.versionCode);
//        versionName=infor.versionName.toString();
        heading.setText("App Version: " + AppVersion);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isNetworkAvailable()) {

                    DownloadApk downloadApk = new DownloadApk(Update_Application.this);

                    downloadApk.startDownloadingApk("http://scorpio.sgroup.pk:8085/atendence/EMP_ATTENDANCE.apk");

                }else{
                    Toast.makeText(Update_Application.this, "No Internet available", Toast.LENGTH_SHORT).show();
                }

             //   new DownloadNewVersion().execute();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                //File write logic here
                return;
            }
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= 15) {
            //do your check here
        }

    }

    /*
    class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(Update_Application.this);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if(progress[0]>99){

                msg="Finishing... ";

            }else {

                msg="Downloading... "+progress[0]+"%";
            }
            bar.setMessage(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if(result){

                Toast.makeText(getApplicationContext(),"Download Done Now installing...",
                        Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getApplicationContext(),"Go To Download Directory and Install",
                        Toast.LENGTH_LONG).show();

            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {
                URL url = new URL("http://scorpio.sgroup.pk:8085/atendence/EMP_ATTENDANCE.apk");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"EMP_ATTENDANCE.apk");

                if(outputFile.exists()){
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Log.e(TAG, "Update Error: " + e.getMessage());
                flag = false;
            }
            return flag;

        }

    }

    void OpenNewVersion(String location) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(location + "EMP_ATTENDANCE.apk")),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}