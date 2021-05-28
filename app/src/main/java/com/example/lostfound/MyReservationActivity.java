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
    private EditText set_num, set_movie, set_theater, set_seat, set_time;

    public String customerContact=((MainActivity)MainActivity.context_main).customerContact;
    public String customerName=((MainActivity)MainActivity.context_main).customerName;
    public String reserveMovie, theaterId, seatNo, reserveTime, reserveId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);


        moveMyinfo = (Button) findViewById(R.id.moveMyinfo);

        reserveName = (EditText) findViewById(R.id.reserveName);
        reserveContact = (EditText) findViewById(R.id.reserveContact);
        set_num = (EditText) findViewById(R.id.set_num);
        set_movie = (EditText) findViewById(R.id.set_movie);
        set_theater = (EditText) findViewById(R.id.set_theater);
        set_seat = (EditText) findViewById(R.id.set_seat);
        set_time = (EditText) findViewById(R.id.set_time);

        reserveName.setText(customerName + "님의 예약정보입니다!");
        reserveContact.setText("(H.P : " + customerContact + ")");
        reserveName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        reserveContact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

        Intent intent = getIntent();
        reserveMovie = intent.getStringExtra("reserveMovie");
        theaterId = intent.getStringExtra("theaterId");
        seatNo = intent.getStringExtra("seatNo");
        reserveTime = intent.getStringExtra("reserveTime");
        reserveId = intent.getStringExtra("reserveId");

        set_movie.setText(reserveMovie);
        set_theater.setText(theaterId);
        set_seat.setText(seatNo);
        set_time.setText(reserveTime);
        set_num.setText(reserveId);

    }
}