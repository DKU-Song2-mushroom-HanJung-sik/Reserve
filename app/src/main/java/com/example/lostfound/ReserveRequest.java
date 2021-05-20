package com.example.lostfound;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReserveRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.25.26//Reserve.php";
    private Map<String, String> map;

    public ReserveRequest(String reserveId, String customerId, String theaterId, String seatNo,
                          String reserveTime, String reserveMovie, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reserveId", reserveId);
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