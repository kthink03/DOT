package com.example.dataoftoday;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener{
    public static final String TAG="dialog";
    public static final String TAG_EVENT_DIALOG="dialog_event";
    public static final String TABLE_NAME = "Record";//Table 이름

    EditText editText;

    /*DB 저장 관련*/
    DatabaseManager databaseManager;
    String SCategory_value;
    int SYear;
    int SMonth;
    int SDay;
    String SWhat;

    public DialogFragment() {}
    public static DialogFragment getInstance(){
        DialogFragment d=new DialogFragment();

        return d;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.popup,container);
        final Context mContext=inflater.getContext();

        databaseManager=new DatabaseManager(getContext());

        /*카테고리 값 선정 */
        Bundle mArgs=getArguments();
        SCategory_value=mArgs.getString("Category");
        Log.d(TAG,"Category"+SCategory_value);

        /*버튼 선언 + 클릭 리스너 선언*/
        ImageButton Save=(ImageButton)v.findViewById(R.id.button_save);
        Save.setOnClickListener(click);
        ImageButton Cancel=(ImageButton)v.findViewById(R.id.button_cancel);
        Cancel.setOnClickListener(click);
        /*EditText*/
        editText=(EditText)v.findViewById(R.id.edittext_record);

        setCancelable(false); //프래그먼트 밖의 영역을 터치하여도 화면이 dismiss 되는 것을 막음
        return v;
    }

    View.OnClickListener click=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_save:
                    //저장 버튼을 누른 경우
                    getTime();
                    SWhat=checkString();
                    Log.d(TAG,"날짜 값"+SYear+"-"+SMonth+"-"+SDay);
                    if(SWhat!=null){
                        //in.insert(SYear,SMonth,SDay,SCategory_value,SWhat,TABLE_NAME);
                        databaseManager.insert(SYear,SMonth,SDay,SCategory_value,SWhat,TABLE_NAME);
                        sendDATE(SYear,SMonth,SDay);
                    }
                case R.id.button_cancel:
                    //나가기 버튼을 누른 경우
                    dismiss();
                default:
                    break;
            }
        }
    };


    public void onResume() {
        /*다이얼로그 사이즈 맞추는 역할*/
        int width=1000;
        int height=1840;

        getDialog().getWindow().setLayout(width,height);
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }
    private void getTime(){
        Calendar cld=Calendar.getInstance();

        SYear=cld.get(Calendar.YEAR);
        SMonth=cld.get(Calendar.MONTH);
        SDay=cld.get(Calendar.DATE);

    }
    private String checkString(){
        String s=editText.getText().toString();
        if(s.length()==0){ //edittext에 담긴 값이 없는 경우
            Toast.makeText(getContext().getApplicationContext(),"메시지가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
            return null;
        }
        else{
            return s;
        }
    }
    private void sendDATE(int sYear,int sMonth,int sDay){
        com.example.dataoftoday.Calendar cal=new com.example.dataoftoday.Calendar();

        int y=sYear;
        int m=sMonth;
        int d=sDay;

        Bundle args_date=new Bundle();
        args_date.putInt("Year",y); //key 값은 카테고리, value값은 해당 카테고리의 명칭
        cal.setArguments(args_date);
    }
}