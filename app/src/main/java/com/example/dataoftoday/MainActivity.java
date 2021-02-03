package com.example.dataoftoday;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.AlphabeticIndex;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String TAG="dot";

    /*DB 씨퀄 쿼리*/
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE IF NOT EXISTS RECORD (Date TEXT PRIMARY KEY, Who TEXT, Location TEXT, What Text)";
    private static final String SQL_DELETE_ENTRIES="DELETE FROM RECORD";

    /*DB 관련*/
    public SQLiteDatabase sqLiteDatabase=null; //DB 사용 변수
    private DatabaseManager helper;
    String DB_Name="Record";
    int dbVersion=1;

    private FragmentManager fm;
    private FragmentTransaction tran;

    private BottomNavigationView bottomNavigationView;
    private Calendar calendar;
    private Record record;
    private Settings settings;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        //sqLiteDatabase=this.openOrCreateDatabase("RECORD",MODE_PRIVATE,null);
        helper=new DatabaseManager(this);
        try{
            sqLiteDatabase=helper.getWritableDatabase();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.e(TAG,"데이터 베이스 없음");
            finish();
        }

        record=new Record();
        calendar= new Calendar();
        settings=new Settings();

        fm=getFragmentManager();
        tran=fm.beginTransaction();

        //제일 처음 띄워줄 뷰를 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,record).commitAllowingStateLoss();
        bottomNavigationView.setSelectedItemId(R.id.tab2); //내비게이션 바에서 제일 가운데 메뉴를 default로

        //메뉴바의 아이콘을 선택했을 때 리스너 설정 + 액션 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    //menu에서 설정했던 id 값에 따라서 동작 설정
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendar).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,record).commitAllowingStateLoss();
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