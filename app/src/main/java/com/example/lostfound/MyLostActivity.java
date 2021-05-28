package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MyLostActivity extends AppCompatActivity {

    private static String TAG = "lostDetail_MyLostActivity";

    private static final String TAG_JSON="lostDetail";
    private static final String TAG_LOSTID="lostDetailId";
    private static final String TAG_THEATERID="theaterID";
    private static final String TAG_SEATNO="seatNo";
    private static final String TAG_DETTIME="detectTime";

    private TextView mTextViewReseult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    public String movieName=((MypageActivity)MypageActivity.context_myPage).movieName;
    public String movieTheater=((MypageActivity)MypageActivity.context_myPage).theaId;
    public String movieTime = ((MypageActivity)MypageActivity.context_myPage).movieTime;



    private Button seatA1, seatA2, seatA3, seatA4;
    private EditText set_movie, set_theater, set_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lost);

        mTextViewReseult = (TextView) findViewById(R.id.textView_lost_result);
        mlistView = (ListView) findViewById(R.id.listView_lost_list);
        mArrayList = new ArrayList<>();

        //GetData task = new GetData();
        //task.execute("http://172.23.14.54/loadDBtoJson.php");

        seatA1 = (Button) findViewById(R.id.seatA1);
        seatA2 = (Button) findViewById(R.id.seatA2);
        seatA3 = (Button) findViewById(R.id.seatA3);
        seatA4 = (Button) findViewById(R.id.seatA4);

        set_movie = (EditText) findViewById(R.id.set_movie);
        set_theater = (EditText) findViewById(R.id.set_theater);
        set_time = (EditText) findViewById(R.id.set_time);

        set_movie.setText(movieName);
        set_theater.setText(movieTheater + "관");
        set_time.setText(movieTime);
        View.OnClickListener clickListener;
        clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.seatA1:
                        // A1 좌석을 눌렀을 때의 처리
                        // if(isLostFound())
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되었습니다", Toast.LENGTH_LONG).show();
                        /* else
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되지 않았습니다", Toast.LENGTH_LONG).show();
                         */
                        break;

                    case R.id.seatA2:
                        // A2 좌석을 눌렀을 때의 처리
                        // if(isLostFound())
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되었습니다", Toast.LENGTH_LONG).show();
                        /* else
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되지 않았습니다", Toast.LENGTH_LONG).show();
                         */
                        break;

                    case R.id.seatA3:
                        // A3 좌석을 눌렀을 때의 처리
                        // if(isLostFound())
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되었습니다", Toast.LENGTH_LONG).show();
                        /* else
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되지 않았습니다", Toast.LENGTH_LONG).show();
                         */
                        break;

                    case R.id.seatA4:
                        // A4 좌석을 눌렀을 때의 처리
                        // if(isLostFound())
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되었습니다", Toast.LENGTH_LONG).show();
                        /* else
                        Toast.makeText(getApplicationContext(), "분실물이 탐지되지 않았습니다", Toast.LENGTH_LONG).show();
                         */
                        break;
                    default:
                        break;
                }
            }
        };

        seatA1.setOnClickListener(clickListener);
        seatA2.setOnClickListener(clickListener);
        seatA3.setOnClickListener(clickListener);
        seatA4.setOnClickListener(clickListener);

        // 분실물이 탐지되었는지 확인
        /*
        * public boolean isLostFound(){
        *   boolean isFound;
        *   return found;
        * */


    }



}
