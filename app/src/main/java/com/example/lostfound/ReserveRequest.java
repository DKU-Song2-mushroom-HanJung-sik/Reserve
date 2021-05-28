package com.example.lostfound;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReserveRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://172.23.14.54//Reserve.php";
    private Map<String, String> map;

    public ReserveRequest(String customerId, String theaterId, String seatNo,
                          String reserveTime, String reserveMovie, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("theaterId", theaterId);
        map.put("seatNo", seatNo);
        map.put("reserveTime", reserveTime);
        map.put("reserveMovie", reserveMovie);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}