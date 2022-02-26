package com.example.quickfetch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        new Handler().postDelayed(() -> {
            Intent i=new Intent(MainActivity.this,
                    BankSelectActivity.class);
            //Intent is used to switch from one activity to another.
            startActivity(i);
            //invoke the SecondActivity.
            finish();
            //the current activity will get finished.
        },1500);
    }
}