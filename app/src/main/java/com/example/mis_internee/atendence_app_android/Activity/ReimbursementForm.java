package com.example.mis_internee.atendence_app_android.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mis_internee.atendence_app_android.Adapter.Adapter;
import com.example.mis_internee.atendence_app_android.Model.RefReimbursementModel;
import com.example.mis_internee.atendence_app_android.Model.User;
import com.example.mis_internee.atendence_app_android.Network.ServerCallback;
import com.example.mis_internee.atendence_app_android.Network.ServerError;
import com.example.mis_internee.atendence_app_android.Network.ServerTask;
import com.example.mis_internee.atendence_app_android.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;
import com.yanzhenjie.album.widget.divider.Api21ItemDivider;
import com.yanzhenjie.album.widget.divider.Divider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReimbursementForm extends AppCompatActivity  implements AlbumLoader {
    String[] Place={"Select Place of Visit","H.O","RSML","ASML","WAREHOUSE","91F","Others"};
    String[] Nature={"Select Nature","O/T","Medical","Fuel/Vehicle Maintenance","Entertainment","Others"};
    Spinner sp_place,sp_nature;
    ArrayAdapter<String> arrayAdapter,arrayAdapter1;
    private EditText ed_incaseofothers,ed_medbils,ed_otherbils,ed_totlbills;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
     ImageView camera;
    private int ScanConstants;
    private TextView tv_ico;
    private Button submit;
    private DatabaseHelper dbH;
    private String status;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private int RC;
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private boolean check;
    private File file;
    private Uri file_uri;
    private Bitmap bitmap;
    private String encodedImages;
    private Handler handler;
     ProgressBar pb;
    private ProgressDialog progressDialog;
    String st_nature,st_place,st_incaseofothers,st_medbils,st_otherbils,st_totlbills;
    private ArrayList<AlbumFile> mAlbumFiles;
    RecyclerView mSelectedRecyclewView;
    Adapter mAdapter;
    private String file_path;
    private String empCode,DEP_CODE,DESIG_CODE,SHIFT_CODE,DESIG_TYPE,DEP_ID,UNIT_CODE,UNIT_ID,IMEI,DESIG_ID,LEAVE_DESC;
    User user;
    String user_id,username;
    int to_user_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reimbursement_form2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp_nature=findViewById(R.id.nature);
        sp_place=findViewById(R.id.place);
        mSelectedRecyclewView = findViewById(R.id.show_selected_images);
        tv_ico=(TextView)findViewById(R.id.tv_ico);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        submit=(Button)findViewById(R.id.submit);
        dbH=new DatabaseHelper(this);
        arrayAdapter= new ArrayAdapter<String>(ReimbursementForm.this,android.R.layout.simple_list_item_1,Place);
        arrayAdapter1= new ArrayAdapter(this,android.R.layout.simple_list_item_1,Nature);
        sp_place.setAdapter(arrayAdapter);
        sp_nature.setAdapter(arrayAdapter1);
        ed_incaseofothers=(EditText)findViewById(R.id.ed_ico);
        ed_medbils=(EditText)findViewById(R.id.amb);
        ed_otherbils=(EditText)findViewById(R.id.aob);
        ed_totlbills=(EditText)findViewById(R.id.allbills);
        radioGroup=(RadioGroup) findViewById(R.id.radio);
        camera=(ImageView) findViewById(R.id.img);
        user=new User(ReimbursementForm.this);
        user_id=user.getEmp_id().toString();
        username=user.getUSERNAME().toString();
        empCode = getIntent().getExtras().getString("EMP_CODE");
        DEP_CODE =  getIntent().getExtras().getString("DEP_CODE");
        DESIG_CODE = getIntent().getExtras().getString("DESIG_CODE");
        SHIFT_CODE = getIntent().getExtras().getString("SHIFT_CODE");
        DESIG_TYPE = getIntent().getExtras().getString("DESIG_TYPE");
        DEP_ID = getIntent().getExtras().getString("DEP_ID");
        UNIT_CODE =  getIntent().getExtras().getString("UNIT_CODE");
        UNIT_ID = getIntent().getExtras().getString("UNIT_ID");
        IMEI = getIntent().getExtras().getString("CREATED_BY");
        DESIG_ID =  getIntent().getExtras().getString("DESIG_ID");
//        to_user_id = getIntent().getExtras().getInt("MANAGER_CODE");
        ed_totlbills.setEnabled(false);
       ed_medbils.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               try{
                   String A=ed_medbils.getText().toString();
                   String B=ed_otherbils.getText().toString();
                   int a=Integer.parseInt(A);
                   int b=Integer.parseInt(B);
                   int TotalBills=     a + b;
                   ed_totlbills.setText(TotalBills+"");}
               catch (Exception e){
                   String A=ed_medbils.getText().toString();
                   ed_totlbills.setText(A+"");
               }
           }
       });
       ed_otherbils.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               try{
               String A=ed_medbils.getText().toString();
               String B=ed_otherbils.getText().toString();
               int a=Integer.parseInt(A);
               int b=Integer.parseInt(B);
               int TotalBills=     a + b;
               ed_totlbills.setText(TotalBills+"");}
               catch (Exception e){
                   String B=ed_otherbils.getText().toString();
                   ed_totlbills.setText(B+"");
               }
           }
       });


        mSelectedRecyclewView.setLayoutManager(new GridLayoutManager(this, 3));
        Divider divider = new Api21ItemDivider(Color.TRANSPARENT, 10, 10);
        mSelectedRecyclewView.addItemDecoration(divider);

        mAdapter = new Adapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                previewAlbum(position);
            }
        });
        mSelectedRecyclewView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mAlbumFiles);
        if(mAdapter!=null){
            mSelectedRecyclewView.setVisibility(View.VISIBLE);
        }
        sp_nature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String a=parent.getSelectedItem().toString();
                if(a.equals("Others")){
            ed_incaseofothers.setVisibility(View.VISIBLE);
            tv_ico.setVisibility(View.VISIBLE);
        }
                else {
                    ed_incaseofothers.setVisibility(View.GONE);
                    tv_ico.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                status=radioButton.getText().toString();
//                Toast.makeText(ReimbursementForm.this,
//                        radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               selectAlbum();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st_nature=sp_nature.getSelectedItem().toString();
                String st_place=sp_place.getSelectedItem().toString();
                String st_incaseofothers =ed_incaseofothers.getText().toString();
                String st_medbils=ed_medbils.getText().toString();
                String st_otherbils=ed_otherbils.getText().toString();
                String st_totlbills=ed_totlbills.getText().toString();
                uploadDatatoServer(st_nature,st_place,st_incaseofothers,Integer.parseInt(st_medbils),Integer.parseInt(st_otherbils),st_totlbills,status);
//                addData(st_nature,st_place,st_incaseofothers,st_medbils,st_otherbils,st_totlbills,status);
            }
        });

    }
    private void selectAlbum() {
        Album.album(this)
                .multipleChoice()
                .columnCount(2)
                .selectCount(6)
                .camera(true)
                .cameraVideoQuality(1)
                .cameraVideoLimitDuration(Integer.MAX_VALUE)
                .cameraVideoLimitBytes(Integer.MAX_VALUE)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                .title("Add Incident ")
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                        mSelectedRecyclewView.setVisibility(View.VISIBLE);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(ReimbursementForm.this, "cancel", Toast.LENGTH_LONG).show();
//
//                        Toast.makeText(ReimbursementForm.this, T, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }


    private void previewAlbum(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(this, "No Selected", Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("Add Incident")
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            mAdapter.notifyDataSetChanged(mAlbumFiles);
                            if(mAdapter!=null){
                                mSelectedRecyclewView.setVisibility(View.VISIBLE);
                            }

                        }
                    })
                    .start();
        }
    }

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.cap)
                .placeholder(R.drawable.add)
                .into(imageView);
    }
    public void addData(String item,String item2,String item3,String item4,String item5,String item6,String item7){
        boolean insertData=dbH.addData(item,item2,item3,item4,item5,item6,item7);
        if(insertData){
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Failed toast", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void uploadFile(File file, String id) {

        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("myfile", file.getName(), mFile);
           RequestBody filename = RequestBody.create(MediaType.parse("spinnerText/plain"), file.getName());
        RequestBody id_temp = RequestBody.create(MediaType.parse("multipart/form-data"), id);

        Call<RefReimbursementModel> fileUpload = ServerTask.getInstance().getServices().uploadFile(fileToUpload, id_temp);
        fileUpload.enqueue(new Callback<RefReimbursementModel>() {
            @Override
            public void onResponse(@NonNull Call<RefReimbursementModel> call, @NonNull Response<RefReimbursementModel> response) {

                assert response.body() != null;
                String data = response.body().toString();

                if (data.equals("sucess")) {

                    try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                                Builder(ReimbursementForm.this);
                        alertDialogBuilder.setTitle("Uploading File...!!!");
                        alertDialogBuilder
                                .setMessage("File Upload successfully")
                                .setCancelable(false)
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        ReimbursementForm.this.finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ReimbursementForm.this, "File Upload Successfully ", Toast.LENGTH_LONG).show();


                    //   Toast.makeText(AddIncidentActivity.this, "File Upload Successfully ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ReimbursementForm.this, "File Not Upload", Toast.LENGTH_LONG).show();

                }
                //   hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<RefReimbursementModel> call, @NonNull Throwable t) {
                Toast.makeText(ReimbursementForm.this, "failed to upload file to server", Toast.LENGTH_SHORT).show();
                //   hideProgress();
                Log.d("api_error", "Error " + t.getMessage());
            }

        });
    }

    public void uploadDatatoServer(String st_nature,String st_place,String st_incaseofothers,int st_medbils,int st_otherbils,String st_totlbills,String status) {

        if (to_user_id!=0) {
            to_user_id=123;
        }

        Call<RefReimbursementModel> call = ServerTask.getInstance().getServices().insertData(st_nature,st_place,st_medbils,
                st_otherbils,st_totlbills,username,user_id,to_user_id,status);

        call.enqueue(new ServerCallback<RefReimbursementModel>() {
            @Override
            public void onFailure(ServerError restError) {
                Toast.makeText(ReimbursementForm.this, "Failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(Response<RefReimbursementModel> response) {

                String value = response.body().getResult();

                if(value.equals("success")){

                        for(int i= 0; i<mAlbumFiles.size();i++) {
                            file_path= mAlbumFiles.get(i).getPath();
                            file= new File(file_path);
                            uploadFile(file,"1");

                        }



                }else {
                    Toast.makeText(ReimbursementForm.this, "ddd", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Response<RefReimbursementModel> response) {
            String Response=response.body().getResult();


                Toast.makeText(ReimbursementForm.this, Response, Toast.LENGTH_SHORT).show();
                if(Response.equals("Success")){
                    uploadFile(file,"1");
                }

            }
        });
    }


}
