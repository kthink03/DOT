package com.example.dataoftoday;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
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

    EditText editText; //record 적는 부분
    EditText Title; //Title 적는 부분

    /*DB 저장 관련*/
    DatabaseManager databaseManager;
    String SCategory_value;
    int SYear;
    int SMonth;
    int SDay;
    String SWhat, STitle;

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
        Title=(EditText)v.findViewById(R.id.edittext_title);
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
                    SWhat=editText.getText().toString();
                    STitle=Title.getText().toString();
                    Log.d(TAG,"날짜 값"+SYear+"-"+SMonth+"-"+SDay);
                    if(SWhat!=null){
                        openSmallDialog();
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
    private void checkString(){
        if(STitle.length()==0){ //타이틀이 없는 경우
            Toast.makeText(getContext().getApplicationContext(),"타이틀이 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(SWhat.length()==0){
            //레코드가 없는 경우
            Toast.makeText(getContext().getApplicationContext(),"데이터가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }

    }

    private void openSmallDialog(){
        SmallDialogFragment sd=SmallDialogFragment.getInstance();
        Bundle args=new Bundle(); //작은 다이얼 프래그먼트에 전달할 값을 담는 객체 Bunble

        args.putString("Category",SCategory_value); //key 값은 카테고리, value값은 해당 카테고리의 명칭
        args.putInt("Year",SYear);
        args.putInt("Month",SMonth);
        args.putInt("Day",SDay);
        args.putString("Title",STitle);
        args.putString("What",SWhat);
        args.putString("Table",TABLE_NAME);
        sd.setArguments(args); //전송

        sd.show(getFragmentManager(),SmallDialogFragment.TAG_EVENT_DIALOG);
    }
}