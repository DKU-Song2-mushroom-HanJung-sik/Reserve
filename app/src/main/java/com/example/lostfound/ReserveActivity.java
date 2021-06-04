package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReserveActivity extends AppCompatActivity {

    private String theaterId;
    private String seatNo;
    private String reserveMovie;

    private String MovieDate; //영화 예약 날짜
    private String MovieTime; //영화 예약 시간. 추후 2개 합쳐서 ReserveTime 됨.

    private Spinner movieSpinner;
    private Spinner theaterSpinner;
    private Spinner timeSpinner;

    private Button date_click;
    private Button seat_A1;
    private Button seat_A2;
    private Button seat_A3;
    private Button seat_A4;


    private Button reserve_check;

    private EditText date;
    private EditText seat;

    private Calendar Cal = Calendar.getInstance();

    //날짜 선택
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Cal.set(Calendar.YEAR, year);
            Cal.set(Calendar.MONTH, month);
            Cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        movieSpinner = findViewById(R.id.movieSpinner);
        theaterSpinner = findViewById(R.id.theaterSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);

        date_click = findViewById(R.id.date_click);
        seat_A1 = findViewById(R.id.seat_A1);
        seat_A2 = findViewById(R.id.seat_A2);
        seat_A3 = findViewById(R.id.seat_A3);
        seat_A4 = findViewById(R.id.seat_A4);


        reserve_check = findViewById(R.id.reserve_check);

        date = findViewById(R.id.date);
        seat = findViewById(R.id.seat);

        //영화 선택
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { reserveMovie = "Harry Potter"; }
                else if (position == 1) { reserveMovie = "The Fast and the Furious"; }
                else { reserveMovie = "LaLaLand"; }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //극장 선택
        theaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { theaterId = "1"; }
                else { theaterId = "2"; }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //시간 선택
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { MovieTime = "11:00:00"; }
                else { MovieTime = "18:00:00"; }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //날짜 선택 버튼
        date_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReserveActivity.this, myDatePicker, Cal.get(Calendar.YEAR),
                        Cal.get(Calendar.MONTH), Cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //좌석 선택 버튼
        seat_A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A1");
                seatNo = "1";
            }
        });

        seat_A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A2");
                seatNo = "2";
            }
        });

        seat_A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A3");
                seatNo = "3";
            }
        });

        seat_A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A4");
                seatNo = "4";
            }
        });

        //예약하기 버튼
        reserve_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customerId = ((MainActivity)MainActivity.context_main).customerId;
                String reserveTime = MovieDate + " " + MovieTime;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "좌석 예약에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ReserveActivity.this, MyReservationActivity.class);

                                intent.putExtra("reserveMovie", reserveMovie);
                                intent.putExtra("theaterId", theaterId);
                                intent.putExtra("seatNo", seatNo);
                                intent.putExtra("reserveTime", reserveTime);

                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "좌석 예약에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ReserveRequest reserveRequest = new ReserveRequest(customerId, theaterId, seatNo, reserveTime, reserveMovie, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReserveActivity.this);
                queue.add(reserveRequest);
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        MovieDate = sdf.format(Cal.getTime());
        date.setText(MovieDate);
        date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
    }
}