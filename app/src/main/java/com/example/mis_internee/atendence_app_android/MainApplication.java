package com.example.mis_internee.atendence_app_android;

import android.app.Application;
import android.content.Context;


import com.example.mis_internee.atendence_app_android.Utils.MediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;


/**
 * Created by rahid on 05/20/2019.
 */

public class MainApplication extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
      //  SharedPrefUtility.getInstance(this);

    }

    public static Context getAppContext() {
        return context;
    }
}
