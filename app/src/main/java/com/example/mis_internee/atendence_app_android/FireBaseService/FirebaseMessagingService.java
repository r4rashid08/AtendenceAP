package com.example.mis_internee.atendence_app_android.FireBaseService;


/**
 * Created by MIS-Internee on 06-Jun-18.
 */


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.mis_internee.atendence_app_android.Activity.Attendance_status;
import com.example.mis_internee.atendence_app_android.Activity.Emp_notification;
import com.example.mis_internee.atendence_app_android.Activity.MainActivity;
import com.example.mis_internee.atendence_app_android.R;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageReceived: \n" +
                        "Extra Information: " + jsonMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);

            assert click_action != null;

            sendNotification(title, message,click_action);
//        notification();
//        showNotification();
    }
    }
    public void notification() {
        Intent myIntent = new Intent(FirebaseMessagingService.this, Emp_notification.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder NotifyMe = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentTitle("New Leave Notification")
                .setContentText("Click here to View");
        NotificationManager notifyMang = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        notifyMang.notify(random.nextInt(), NotifyMe.build());


    }
    public void showNotification() {

        Intent myIntent = new Intent(FirebaseMessagingService.this, Emp_notification.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder notify = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentTitle("Second Notifications")
                .setContentText("Click here to View");
        NotificationManager notifyMang = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMang.notify(2, notify.build());

    }


    private void sendNotification(String title,String messageBody, String click_action) {
        Intent intent;
        if(click_action.equals("OPEN_ACTIVITY_1")){
            intent = new Intent(this,Attendance_status .class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        else if(click_action.equals("OPEN_ACTIVITY_2")){
            intent = new Intent(this, Emp_notification.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }else{
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

         Random random = new Random();
        if (notificationManager != null) {
            notificationManager.notify(random.nextInt() /* ID of notification */, notificationBuilder.build());
        }
    }
}
