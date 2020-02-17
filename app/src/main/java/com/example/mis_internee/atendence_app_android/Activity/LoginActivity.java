package com.example.mis_internee.atendence_app_android.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.atendence_app_android.Model.Login.LoginModel;
import com.example.mis_internee.atendence_app_android.Model.Login.LoginResult;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.example.mis_internee.atendence_app_android.R;
import com.example.mis_internee.atendence_app_android.Utils.Utility;

import java.util.List;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    EditText username, Passwd;
    TelephonyManager tel;
    Button login ;
    TextView tv,reg;
    private ProgressDialog progressdialog;
    private int STORAGE_PERMISSION_CODE = 1;
    private int conter = 0;
    private ProgressDialog getdata;
    private String USERNAME,PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        TextView fontAwesomeAndroidIcon = (TextView) findViewById(R.id.font_awesome_android_icon);
        TextView key = (TextView) findViewById(R.id.font_awesome_android_key);
//        fontAwesomeAndroidIcon.setTypeface(fontAwesomeFont);
//        key.setTypeface(fontAwesomeFont);

        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(LoginActivity.this, "You have already granted this permission!",
//                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
        username = (EditText) findViewById(R.id.username);
        Passwd = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbtn);
//        tv = (TextView) findViewById(R.id.textView);
//        reg=(TextView)findViewById(R.id.regBtn);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!validate()) {
                        onLoginFailed();
                        return;
                    }
                    if(Utility.isNetworkAvailable()){
                        getdata = ProgressDialog.show(LoginActivity.this, "Checking Details...", "Please Wait", false, false);

                        signIn();
                        //  Toast.makeText(getApplicationContext(),"Internet Connection Available",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),"No Internet Connection Available",Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE )) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("User Permission Needed to continue")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[] {Manifest.permission.READ_PHONE_STATE}, STORAGE_PERMISSION_CODE);
                        }
                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_PHONE_STATE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public boolean validate() {
        boolean valid = true;

        USERNAME = username.getText().toString();
        PASSWORD = Passwd.getText().toString();


        if (USERNAME.isEmpty() /*|| !android.util.Patterns.EMAIL_ADDRESS.matcher(mCurrentEmail).matches()*/) {
            username.setError("enter a valid email address ");
            valid = false;
        } else {
            username.setError(null);
        }

        if (PASSWORD.isEmpty() ) {
            Passwd.setError("enter a password");
            valid = false;
        } else {
            Passwd.setError(null);
        }


        return valid;
    }


    private void signIn() {



        //JsonObject param = RequestUtil.getLoginRequest(mEmail.getText().toString(),mPassword.getText().toString());
        Call<LoginModel> call = ServerTask.getInstance().getServices().loginApi(USERNAME,PASSWORD);

        call.enqueue(new ServerCallback<LoginModel>() {
            @Override
            public void onFailure(ServerError restError) {
             //   hideProgress();
                getdata.dismiss();
            }

            @Override
            public void onSuccess(retrofit2.Response<LoginModel> response) {

            }

            @Override
            public void onResponse(retrofit2.Response<LoginModel> response) {
              getdata.dismiss();


              try {
                  assert response.body() != null;
                  List<LoginResult> mResultLIst = response.body().getLoginResult();

                  if (mResultLIst != null) {
                      LoginResult result = mResultLIst.get(0);
                      String id = result.getID();
                      String email = result.getEMPNAME();
                      String password = result.getPASSWORD();
                      String hod = result.getISHOD();
                      String manager = result.getISMANAGER();

                    /* Intent i = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(i);
                     finish();

                    */

                      User user = new User(LoginActivity.this);
                      Intent intent = new Intent(LoginActivity.this, fakeSplash.class);
                      intent.putExtra("username", USERNAME.trim());
                      intent.putExtra("empId", email);
                      intent.putExtra("emp_id", id);
                      user.setUSERNAME(USERNAME.trim());
                      user.setEmp_id(id);
                      user.setName(email);
                      user.setIS_MANAGER(manager);
                      // user.set


                      Toast.makeText(getApplication(), "Successfully Login...!!!", Toast.LENGTH_LONG).show();
                      LoginActivity.this.startActivity(intent);
                      finish();


                  }else {
                      Toast.makeText(LoginActivity.this, "invalid user name password", Toast.LENGTH_SHORT).show();
                  }

              }catch (Exception e){e.printStackTrace();}



            }



        });

    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();

    }
}
