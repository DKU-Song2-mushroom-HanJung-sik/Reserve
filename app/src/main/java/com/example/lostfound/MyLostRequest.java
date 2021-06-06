package com.example.lostfound;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyLostRequest extends StringRequest {

    //서버 URL 설정( PHP 파일 연동)
    final static private String URL = "http://220.149.236.71/updateLost.php";
    private Map<String, String> map;

    public MyLostRequest(String LostId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("lostDetailId",LostId);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}