package com.example.dataoftoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity<안녕> extends AppCompatActivity {
    private static final String TAG="dot";

    public SQLiteDatabase sqLiteDatabase=null; //DB 사용 변수
    /*DB 씨퀄 쿼리*/
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE IF NOT EXISTS RECORD (Date TEXT PRIMARY KEY, Who TEXT, Location TEXT, What Text)";
    private static final String SQL_DELETE_ENTRIES="DELETE FROM RECORD";

    BottomNavigationView bottomNavigationView;
    Calendar calendar;
    More more;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        sqLiteDatabase=this.openOrCreateDatabase("RECORD",MODE_PRIVATE,null);

        //테이블이 존재하지 않으면 새로 생성
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        //테이블이 존재하는 경우 기존 데이터를 지우기 위해서 사용
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);

        calendar= new Calendar();
        more=new More();
        settings=new Settings();

        //제일 처음 띄워줄 뷰를 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendar).commitAllowingStateLoss();
        bottomNavigationView.setSelectedItemId(R.id.tab2); //내비게이션 바에서 제일 가운데 메뉴를 default로

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
