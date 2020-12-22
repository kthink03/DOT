package com.example.dataoftoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="dot";
    BottomNavigationView bottomNavigationView;
    Calendar calendar;
    More more;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        calendar= new Calendar();
        more=new More();
        settings=new Settings();

        //제일 처음 띄워줄 뷰를 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendar).commitAllowingStateLoss();
        Log.d(TAG,"GETSUPPORT");
        //메뉴바의 아이콘을 선택했을 때 리스너 설정 + 액션 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    //menu에서 설정했던 id 값에 따라서 동작 설정
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,more).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendar).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab3:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,settings).commitAllowingStateLoss();
                        return true;
                    }

                    default: return false;
                }
            }
        });
    }
}
