package com.example.dataoftoday;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    protected void oncreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), com.example.dataoftoday.MainActivity.class);
        startActivity(intent);
    }
}
