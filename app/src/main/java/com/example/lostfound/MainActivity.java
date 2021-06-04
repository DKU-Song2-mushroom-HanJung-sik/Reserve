package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

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
    public String IsLost;



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
        //customerName = intent.getStringExtra("IsLost");

        if (customerName != null && customerContact != null){

            identity_name.setText(customerName + " 님, 안녕하세요!");
            identity_contact.setText("(H.P : " + customerContact + ")");
            identity_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            identity_contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    //IsLost = ((MyLostActivity)MyLostActivity.context_main).textView_list_isLost;

                    createNotification();
                }
            };

            Timer timer = new Timer();
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

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("분실물이 탐지되었습니다!");
        builder.setContentText("Dankook Cinema");

        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }

}