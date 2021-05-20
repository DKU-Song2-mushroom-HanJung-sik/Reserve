package com.example.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Context context_main;

    Button move_login;
    Button move_reserve;
    //Button move_lostfound;

    EditText identity_name, identity_contact;


    // ImageButton image_harry;
    // ImageButton image_run;
    // ImageButton image_land;

    public String customerId;
    public String customerContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;

        move_login = (Button) findViewById(R.id.move_login);
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
        move_login.setOnClickListener(this);


        identity_name = (EditText) findViewById(R.id.identity_name);
        identity_contact = (EditText) findViewById(R.id.identity_contact);

        Intent intent = getIntent();
        customerId = intent.getStringExtra("customerId");
        customerContact = intent.getStringExtra("customerContact");

        if (customerId != null && customerContact != null){
            identity_name.setText(customerId + " 님, 안녕하세요!");
            identity_contact.setText("(H.P : " + customerContact + ")");
            identity_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            identity_contact.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_login:
                Intent intent_log = new Intent(this, LoginActivity.class);
                startActivity(intent_log);
                break;

            case R.id.move_reserve:

                if (customerId != null){
                    Intent intent_res = new Intent(this, ReserveActivity.class);
                    startActivity(intent_res);
                    break;
                }
                else{
                    Intent intent_loglog = new Intent(this, LoginActivity.class);
                    startActivity(intent_loglog);
                    break;
                }
            //case R.id.move_lostfound:


        }
    }
}