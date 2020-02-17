package com.example.mis_internee.atendence_app_android.Activity;

/**
 * Created by MIS-Internee on 29-Dec-17.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://scorpio.sgroup.pk:8085/atendence/insert.php";
    private Map<String, String> params;

    public RegisterRequest( String EMP_ID,String PASSWD,String IMEI_NO, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("EMP_ID",EMP_ID);
        params.put("PASSWD", PASSWD);
        params.put("IMEI_NO",IMEI_NO);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}