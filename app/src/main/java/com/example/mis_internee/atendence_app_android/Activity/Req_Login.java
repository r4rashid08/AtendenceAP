package com.example.mis_internee.atendence_app_android.Activity;

/**
 * Created by MIS-Internee on 29-Dec-17.
 */


import android.widget.EditText;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class Req_Login extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://scorpio.sgroup.pk:8085/atendence/logTest.php";
    private Map<String, String> params;

    public Req_Login(String USERNAME, String PASSWORD, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("USERNAME", USERNAME);
        params.put("PASSWORD",PASSWORD);

//        params.put("IMEI_NO",imei);
//        params.put("username", username);
//        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}