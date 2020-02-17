package com.example.mis_internee.atendence_app_android.Network;

import android.util.Log;

import com.example.mis_internee.atendence_app_android.Utils.Utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rashid on 03/03/2018.
 */
public class ServerTask {

    private static ServerTask serverTask = null;

    private ServerAPI serverAPI;
    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;

    public static ServerTask getInstance() {
        if (serverTask == null) {
            serverTask = new ServerTask();
        }

        return serverTask;
    }

    private ServerTask() {

        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!



        retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtils.SERVER_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

    }

    public <S> S createService(Class<S> serviceClass) {

        return retrofit.create(serviceClass);
    }

    public ServerAPI getServices() {
        if (serverAPI == null) {
            serverAPI = createService(ServerAPI.class);
        }

        return serverAPI;
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Utility.isNetworkAvailable()) {
                throw new IOException("No internet connectivity");
            } else {

                /**
                 * Add Headers to Request
                 */
//                boolean isLoggedIn = AuthManager.getInstance().isUserLoggedIn();
//                if (isLoggedIn) {
//                    request = request.newBuilder()
//                            .header(Constants.AUTHORIZATION, AuthManager.getInstance().getLoginToken())
//                            .method(request.method(), request.body())
//                            .build();
//                }

                String requestLog = String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers());

                if (request.method().compareToIgnoreCase("post") == 0) {
                    requestLog = "\n" + requestLog + "\n" + bodyToString(request);
                }

                if (request.method().compareToIgnoreCase("put") == 0) {
                    requestLog = "\n" + requestLog + "\n" + bodyToString(request);
                }

                Log.d("TAG", "request" + "\n" + requestLog);

                Response response = chain.proceed(request);

                String bodyString = response.body().string();

                Log.d("TAG", "\nResponse Body : " + bodyString);

                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), bodyString))
                        .build();
            }
        }
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
