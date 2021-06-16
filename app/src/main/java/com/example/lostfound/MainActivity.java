package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static Context context_main;

    Button move_login;
    Button move_reserve;
    Button move_myinfo;

    EditText identity_name, identity_contact;

    //ImageButton image_harry;
    // ImageButton image_run;
    // ImageButton image_land;

    public String customerId;
    public String customerContact;
    public String customerName;

    public String lostCnt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;
/*
        image_harry = (ImageButton) findViewById(R.id.image_harry);
        image_run = (ImageButton) findViewById(R.id.image_run);
        image_land = (ImageButton) findViewById(R.id.image_land);

        image_harry.setOnClickListener(this);
        image_run.setOnClickListener(this);
        image_land.setOnClickListener(this);
*/


        identity_name = (EditText) findViewById(R.id.identity_name);
        identity_contact = (EditText) findViewById(R.id.identity_contact);

        Intent intent = getIntent();
        customerId = intent.getStringExtra("customerId");
        customerContact = intent.getStringExtra("customerContact");
        customerName = intent.getStringExtra("customerName");

        //로그인 성공했다면
        if (customerName != null && customerContact != null){

            identity_name.setText(customerName + " 님, 안녕하세요!");
            identity_contact.setText("(H.P : " + customerContact + ")");
            identity_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            identity_contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            TimerTask tt = new TimerTask() {
                // TimerTask 추상 클래스를 선언하자마자 run()을 강제로 정의하도록 함
                @Override
                public void run() {


                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success) {

                                    lostCnt = jsonObject.getString("lostCnt");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    MainRequest mainRequest = new MainRequest(customerId, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(mainRequest);

                    if(lostCnt != null && !lostCnt.equals("0"))
                        createNotification();
                }
            };

            // 주기, Timer 생성
            Timer timer = new Timer();
            // tt라는 timertask를 선언과 동시에 시작하되 (딜레이 0초), 30초마다 반복
            timer.schedule(tt, 0, 30000);
            //timer.cancel();
        }

        move_login = (Button) findViewById(R.id.move_login);
        move_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_log = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_log);
            }
        });

        move_reserve = (Button) findViewById(R.id.move_reserve);
        move_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerId != null){
                    Intent intent_res = new Intent(MainActivity.this, ReserveActivity.class);
                    intent_res.putExtra("customerId", customerId);
                    intent_res.putExtra("customerContact", customerContact);
                    startActivity(intent_res);
                }
                else{
                    Intent intent_loglog = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent_loglog);
                }
            }
        });

        move_myinfo = (Button) findViewById(R.id.move_myinfo);
        move_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerId != null){
                    Intent intent_info = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent_info);
                }
                else{
                    Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent_login);
                }
            }
        });

    }

    // 알림 및 내용 설정
    private void createNotification() {
        // Notification을 클릭했을 때 MyLostActivity를 시작할 수 있는 Implicit Intent 생성
        Intent intent_notify = new Intent(MainActivity.this, MyLostActivity.class);
        // Noitification을 감싸주고 전달해주는 PendingIntent 정의
        PendingIntent intent_notifyPending = PendingIntent.getActivity
                (MainActivity.this, 0, intent_notify, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 채널 만들기
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("PRIMARY_CHANNEL_ID", "PRIMARY_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "PRIMARY_CHANNEL_ID")
                                            .setChannelId("PRIMARY_CHANNEL_ID")
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("분실물 " + lostCnt + "개가 탐지되었습니다!")  // 알림 제목
                                            .setContentText("Dankook Cinema [나의시네마>분실물확인]에서 체크하세요!")    // 알림 세부 텍스트
                                            .setContentIntent(intent_notifyPending) // MyLostActivity로 넘어가도록
                                            .setAutoCancel(true);   // 슬라이딩하여 제거


        // 알림 표시, 알림을 표시하려면 NotificationManage.notify()를 호출하고 고유id와 결과를 호출결과에 전하면 됨.
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("PRIMARY_CHANNEL_ID", "PRIMARY_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(0, builder.build());
    }

}