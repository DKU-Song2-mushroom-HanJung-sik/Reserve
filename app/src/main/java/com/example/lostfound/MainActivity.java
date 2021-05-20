package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Button move_login;
    Button move_reserve;
    //Button move_lostfound;

    //ImageButton image_harry;
   // ImageButton image_run;
    //ImageButton image_land;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //move_reserve = (Button) findViewById(R.id.move_login);
        move_reserve = (Button) findViewById(R.id.move_reserve);
        //move_reserve = (Button) findViewById(R.id.move_lostfound);
/*
        image_harry = (ImageButton) findViewById(R.id.image_harry);
        image_run = (ImageButton) findViewById(R.id.image_run);
        image_land = (ImageButton) findViewById(R.id.image_land);


        image_harry.setOnClickListener(this);
        image_run.setOnClickListener(this);
        image_land.setOnClickListener(this);
*/
        move_reserve.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.move_login:

            case R.id.move_reserve:
                Intent intent = new Intent(this, MovieReserve.class);
                startActivity(intent);
                break;

            //case R.id.move_lostfound:


        }
    }
}