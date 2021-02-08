package com.example.dataoftoday;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String TAG="DB_Manager";
    public static final String DB_NAME = "DOT.db"; //DB 이름
    public static final String TABLE_NAME = "Record";//Table 이름
    static final int DB_VERSION = 5; // DB 버전, DB 스키마 변동이 생기면 Version 업그레이드

    //부가적 객체 선언
    public Context context;

    //DB 생성 씨퀄문
    private static final String SQL_CREATE_ENTRIES="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+ "(Year INTEGER, Month INTEGER, Day INTEGER, Category TEXT,Title Text, What Text, Eval INTEGER);";
    private static final String SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS RECORD";

    public DatabaseManager(Context context) {
        super(context,DB_NAME,null,DB_VERSION);

        this.context=context;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(DB_VERSION>1){
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);
        }
    }

    public Cursor select_date(){
        /*DB에서 데이터 조회_날짜*/
        SQLiteDatabase DB=this.getReadableDatabase();
        String sql="SELECT YEAR, MONTH, DAY FROM RECORD;";
        Cursor c=DB.rawQuery(sql,null);

        return c;
    }

    public void insert(int sYear,int sMonth,int sDay,String sCategory, String sTitle, String sWhat,int Eval, String table) {
        /*DB 삽입 문*/
        SQLiteDatabase DB=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        /*각 속성에 해당 값 저장*/
        cv.put("Year",sYear);
        cv.put("Month",sMonth);
        cv.put("Day",sDay);
        cv.put("Category",sCategory);
        cv.put("Title",sTitle);
        cv.put("What",sWhat);
        cv.put("Eval",Eval);

        long result=DB.insert(table,null,cv);

        if(result==-1) {
            Toast.makeText(context,"FAIL ",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"저장되었습니다. ",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor showRecord(String Category,int Year,int Month,int Day){
        /*DB에서 데이터 조회_ 기록*/
        SQLiteDatabase DB=this.getReadableDatabase();
        String c=Category;
        String sql="Select Title,What,Eval From RECORD Where Category = "+"'"+c+"' AND YEAR="
                +Year+" AND Month=" +Month+" AND Day=" +Day+";"; //where 조건절에 문자열인 경우 ' 기호는 반드시 적을 것.

        Cursor cursor=DB.rawQuery(sql,null);
        return cursor;
    }

}