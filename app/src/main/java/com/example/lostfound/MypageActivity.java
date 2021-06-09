package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MypageActivity extends AppCompatActivity {


    private Button reserveInfo, lostCheck;
    private EditText identity_name1, identity_name2, identity_id, identity_phone;
    public String movieName, theaterId, seatNum, movieTime;

    public String customerId = ((MainActivity)MainActivity.context_main).customerId;
    public String customerContact=((MainActivity)MainActivity.context_main).customerContact;
    public String customerName=((MainActivity)MainActivity.context_main).customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        reserveInfo = (Button) findViewById(R.id.reserveInfo);
        lostCheck = (Button) findViewById(R.id.lostCheck);

        identity_name1 = (EditText) findViewById(R.id.identity_name1);
        identity_name2 = (EditText) findViewById(R.id.identity_name2);
        identity_id = (EditText) findViewById(R.id.identity_id);
        identity_phone = (EditText) findViewById(R.id.identity_phone);

        identity_id.setText(customerId);
        identity_phone.setText(customerContact);
        identity_name1.setText(customerName);
        identity_name2.setText(customerName);




        Intent intent = getIntent();
        movieName = intent.getStringExtra("reserveMovie");
        theaterId = intent.getStringExtra("theaterId");
        seatNum = intent.getStringExtra("seatNo");
        movieTime = intent.getStringExtra("reserveTime");


        // 예약 확인 버튼 클릭시
        reserveInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MypageActivity.this, MyReservationActivity.class);
                intent.putExtra("reserveMovie", movieName);
                intent.putExtra("theaterId", theaterId);
                intent.putExtra("seatNo", seatNum);
                intent.putExtra("reserveTime", movieTime);
                startActivity(intent);
            }
        });


        // 분실물 확인 버튼 클릭시
        lostCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MypageActivity.this, MyLostActivity.class);
                intent.putExtra("seatNUM", seatNum);
                startActivity(intent);
            }
        });
    }
}