package com.example.dataoftoday;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "DOT.db"; //DB 이름
    static final String TABLE_NAME = "RECORD";//Table 이름
    static final int DB_VERSION = 1; // DB 버전, DB 스키마 변동이 생기면 Version 업그레이드
    private static final String TAG = "dataoftoday";

    //DB 관련 객체 선언
    private SQLiteOpenHelper openHelper; //DB OPENER
    private SQLiteDatabase db; // DB CONTROLLER

    //부가적 객체 선언
    private Context context;

    //DB 생성 씨퀄문
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE RECORD (Who TEXT, Location TEXT, What Text)";
    private static final String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS RECORD";
    private static final String SQL_INSERT_ENTRIES="INSERT INTO RECORD VALUES(안녕,내이름은,소영)";

    public DatabaseManager(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VERSION);

    }



    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_INSERT_ENTRIES);
        Toast.makeText(context,"DB IS OPENED",0).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}