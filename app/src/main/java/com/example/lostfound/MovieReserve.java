package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MovieReserve extends AppCompatActivity {

    String TheaterID;
    String SeatNo;
    String ReserveMovie;

    String MovieDate; //영화 예약 날짜
    String MovieTime; //영화 예약 시간. 추후 2개 합쳐서 ReserveTime 됨.

    Spinner movieSpinner;
    Spinner theaterSpinner;
    Spinner timeSpinner;

    Button date_click;
    Button seat_A1;
    Button seat_A2;
    Button seat_A3;
    Button seat_A4;
    Button reserve_check;

    EditText date;
    EditText seat;
    EditText check; //변수 확인 용

    Calendar Cal = Calendar.getInstance();

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

        movieSpinner = (Spinner)findViewById(R.id.movieSpinner);
        theaterSpinner = (Spinner)findViewById(R.id.theaterSpinner);
        timeSpinner = (Spinner)findViewById(R.id.timeSpinner);

        date_click = (Button) findViewById(R.id.date_click);
        seat_A1 = (Button) findViewById(R.id.seat_A1);
        seat_A2 = (Button) findViewById(R.id.seat_A2);
        seat_A3 = (Button) findViewById(R.id.seat_A3);
        seat_A4 = (Button) findViewById(R.id.seat_A4);
        reserve_check = (Button) findViewById(R.id.reserve_check);

        date = (EditText) findViewById(R.id.date);
        seat = (EditText) findViewById(R.id.seat);
        check = (EditText) findViewById(R.id.check);

        //영화 선택
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { ReserveMovie = "해리포터와 불의 잔"; }
                else if (position == 1) { ReserveMovie = "분노의 질주"; }
                else { ReserveMovie = "라라랜드"; }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //극장 선택
        theaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { TheaterID = "1"; }
                else { TheaterID = "2"; }
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
                new DatePickerDialog(MovieReserve.this, myDatePicker, Cal.get(Calendar.YEAR),
                        Cal.get(Calendar.MONTH), Cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //좌석 선택 버튼
        seat_A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A1");
                SeatNo = "1";
            }
        });

        seat_A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A2");
                SeatNo = "2";
            }
        });

        seat_A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A3");
                SeatNo = "3";
            }
        });

        seat_A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.setText("A4");
                SeatNo = "4";
            }
        });

        //예약하기 버튼
        reserve_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CustomerID = "1111";
                String ReserveTime = MovieDate + " " + MovieTime;
                String ReserveID = CustomerID + TheaterID + SeatNo;

                check.setText(ReserveID + " " + CustomerID + " " + TheaterID + " " + SeatNo + " " + ReserveTime + " " + ReserveMovie);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "좌석 예약에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                /*
                                Intent intent = new Intent(MovieReserve.this, MainActivity.class);
                                startActivity(intent);
                                 */
                            } else {
                                Toast.makeText(getApplicationContext(), "좌석 예약에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ReserveRequest reserveRequest = new ReserveRequest(ReserveID, CustomerID, TheaterID, SeatNo, ReserveTime, ReserveMovie, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MovieReserve.this);
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