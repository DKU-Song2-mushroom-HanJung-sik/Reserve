package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
    public static Context context_main;

    private static String TAG = "lostDetail_MyLostActivity";

    private static final String TAG_JSON="lostDetail";
    private static final String TAG_LOSTID="lostDetailId";
    private static final String TAG_THEATERID="theaterId";
    private static final String TAG_SEATNO="seatNo";
    private static final String TAG_DETTIME="detectTime";
    private static final String TAG_ISLOST="isLost";


    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    //String mJsonString;

    //EditText set_theater;
    //EditText set_movie;


    public String theaterID, seatNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lost);


        //Intent intent = getIntent();
        //seatNo = intent.getStringExtra("seatNUM");
        String customerId = ((MainActivity)MainActivity.context_main).customerId;
        mlistView = (ListView) findViewById(R.id.listView_lost_list);
        mArrayList = new ArrayList<>();

        // GetData task = new GetData();
        // task.execute("http://220.149.236.71/loadDBtoJson.php");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject item = jsonArray.getJSONObject(i);

                        String LostId = item.getString(TAG_LOSTID);
                        String TheaterId = item.getString(TAG_THEATERID) + "관";
                        String SeatNO = "A" + item.getString(TAG_SEATNO);
                        String DetTime = item.getString(TAG_DETTIME);
                        String IsLost = item.getString(TAG_ISLOST);

                        if(IsLost.equals("1")){
                            IsLost = "X";

                        }
                        else{ IsLost = "O"; }


                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(TAG_LOSTID, LostId);
                        hashMap.put(TAG_THEATERID, TheaterId);
                        hashMap.put(TAG_SEATNO, SeatNO);
                        hashMap.put(TAG_DETTIME, DetTime);
                        hashMap.put(TAG_ISLOST, IsLost);

                        mArrayList.add(hashMap);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(
                            MyLostActivity.this, mArrayList, R.layout.item_list,
                            new String[]{TAG_LOSTID,TAG_THEATERID, TAG_SEATNO, TAG_DETTIME, TAG_ISLOST},
                            new int[]{R.id.textView_list_id, R.id.textView_list_theater, R.id.textView_list_seat, R.id.textView_list_detectTime, R.id.textView_list_isLost}
                    ){
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View v = super.getView(position, convertView, parent);

                            Button btn_list_Retrieve =(Button)v.findViewById(R.id.btn_list_Retrieve);
                            TextView textView_list_id =(TextView) v.findViewById(R.id.textView_list_id);
                            TextView textView_list_isLost =(TextView) v.findViewById(R.id.textView_list_isLost);
                            btn_list_Retrieve.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    String ListLostId = textView_list_id.getText().toString();
                                    // TODO Auto-generated method stub
                                    if (textView_list_isLost.getText().toString().equals("O")){
                                        Toast.makeText(getApplicationContext(),"이미 회수된 분실물입니다.",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        //set_theater.setText(ListLostId);
                                        retrieveLost(ListLostId);
                                    }
                                }
                            });
                            return v;
                        }
                    };

                    mlistView.setAdapter(adapter);



                } catch (JSONException e) {
                    Log.d(TAG, "showResult : ", e);
                }
            }
        };
        LostSeatRequest MyLostRequest = new LostSeatRequest(customerId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyLostActivity.this);
        queue.add(MyLostRequest);
    }

/*
    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        // 백그라운드 스레드를 실행하기전 준비 단계, 변수의 초기화나 네트워크 통신전 셋팅해야 할 것들을 메서드 공간에 작성
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MyLostActivity.this,
                    "Please Wait", null, true, true);
        }


        // background 스레드가 일을 마치고 리턴값으로 result 넘겨줌
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
          //  mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

             //  mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        // background 스레드로 일처리를 해주는 곳, 네트워크, 병행일처리 등을 작성
        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String LostId = item.getString(TAG_LOSTID);
                String TheaterId = item.getString(TAG_THEATERID) + "관";
                String SeatNO = "A" + item.getString(TAG_SEATNO);
                String DetTime = item.getString(TAG_DETTIME);
                String IsLost = item.getString(TAG_ISLOST);

                if(IsLost.equals("1")){
                    IsLost = "X";

                }
                else{ IsLost = "O"; }


                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_LOSTID, LostId);
                hashMap.put(TAG_THEATERID, TheaterId);
                hashMap.put(TAG_SEATNO, SeatNO);
                hashMap.put(TAG_DETTIME, DetTime);
                hashMap.put(TAG_ISLOST, IsLost);

                mArrayList.add(hashMap);



            }

            SimpleAdapter adapter = new SimpleAdapter(
                    MyLostActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_LOSTID,TAG_THEATERID, TAG_SEATNO, TAG_DETTIME, TAG_ISLOST},
                    new int[]{R.id.textView_list_id, R.id.textView_list_theater, R.id.textView_list_seat, R.id.textView_list_detectTime, R.id.textView_list_isLost}
            ){
                @Override
                public View getView (int position, View convertView, ViewGroup parent)
                {
                    View v = super.getView(position, convertView, parent);

                    Button btn_list_Retrieve =(Button)v.findViewById(R.id.btn_list_Retrieve);
                    TextView textView_list_id =(TextView) v.findViewById(R.id.textView_list_id);
                    TextView textView_list_isLost =(TextView) v.findViewById(R.id.textView_list_isLost);
                    btn_list_Retrieve.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            String ListLostId = textView_list_id.getText().toString();
                            // TODO Auto-generated method stub
                            if (textView_list_isLost.getText().toString().equals("O")){
                                Toast.makeText(getApplicationContext(),"이미 회수된 분실물입니다.",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //set_theater.setText(ListLostId);
                                retrieveLost(ListLostId);
                            }
                        }
                    });
                    return v;
                }
            };

            mlistView.setAdapter(adapter);



        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

*/
    private void retrieveLost(String LostId){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 회수 성공
                        Toast.makeText(getApplicationContext(),"분실물을 회수하였습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyLostActivity.this, MyLostActivity.class);
                        startActivity(intent);
                    } else { // 회수 실패
                        Toast.makeText(getApplicationContext(), "분실물 회수에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MyLostRequest MyLostRequest = new MyLostRequest(LostId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyLostActivity.this);
        queue.add(MyLostRequest);
    }

}
