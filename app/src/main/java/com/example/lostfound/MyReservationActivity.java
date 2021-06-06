package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.TypedValue;
import java.util.ArrayList;
import android.os.Bundle;
import android.content.Intent;

public class MyReservationActivity extends AppCompatActivity {


    private Button moveMyinfo;
    private EditText reserveName, reserveContact;
    private EditText set_movie, set_theater, set_seat, set_time;


    public String customerContact=((MainActivity)MainActivity.context_main).customerContact;
    public String customerName=((MainActivity)MainActivity.context_main).customerName;


    public String movieName;
    public String theaId;
    public String seatNum;
    public String movieTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);


        moveMyinfo = (Button) findViewById(R.id.moveMyinfo);

        reserveName = (EditText) findViewById(R.id.reserveName);
        reserveContact = (EditText) findViewById(R.id.reserveContact);

        set_movie = (EditText) findViewById(R.id.set_movie);
        set_theater = (EditText) findViewById(R.id.set_theater);
        set_seat = (EditText) findViewById(R.id.set_seat);
        set_time = (EditText) findViewById(R.id.set_time);

        Intent intent = getIntent();
        movieName = intent.getStringExtra("reserveMovie");
        theaId = intent.getStringExtra("theaterId");
        seatNum = intent.getStringExtra("seatNo");
        movieTime = intent.getStringExtra("reserveTime");


        reserveName.setText(customerName + "님의 예약정보입니다!");
        reserveContact.setText("(H.P : " + customerContact + ")");
        reserveName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        reserveContact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        set_movie.setText(movieName);
        set_theater.setText(theaId + "관");
        set_seat.setText("A" + seatNum);
        set_time.setText(movieTime);






        // 나의 시네마 버튼 클릭
        moveMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservationActivity.this, MypageActivity.class);
                intent.putExtra("reserveMovie", movieName);
                intent.putExtra("theaterId", theaId);
                intent.putExtra("seatNo", seatNum);
                intent.putExtra("reserveTime", movieTime);
                startActivity(intent);
            }
        });
    }
}