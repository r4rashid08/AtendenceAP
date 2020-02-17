package com.example.mis_internee.atendence_app_android.FireBaseService;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }



    private void registerToken(String token) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://scorpio.sgroup.pk:8085/atendence/regToken.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
