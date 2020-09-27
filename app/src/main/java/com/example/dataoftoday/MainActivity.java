package com.example.dataoftoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dot.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="dot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"동작:"+"실행");
        Button imageButton=(Button)findViewById(R.id.btn1);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d(TAG,"동작:"+"버튼 클릭");
                Intent intent = new Intent(getApplicationContext(), com.example.dataoftoday.SubActivity.class);
                startActivity(intent);
            }
        });
    }
}