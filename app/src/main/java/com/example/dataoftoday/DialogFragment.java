package com.example.dataoftoday;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
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



public class DialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener{
    public static final String TAG_EVENT_DIALOG="dialog_event";
    public static final String TABLE_NAME = "Record";//Table 이름
    InsertDB in;
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
        in=(InsertDB)getActivity(); //MainActivity 받아오기

        /*버튼 선언 + 클릭 리스너 선언*/
        ImageButton Save=(ImageButton)v.findViewById(R.id.button_save);
        Save.setOnClickListener(click);
        ImageButton Cancel=(ImageButton)v.findViewById(R.id.button_cancel);
        Cancel.setOnClickListener(click);
        /*EditText*/
        EditText Text_Record=(EditText)v.findViewById(R.id.edittext_record);
        setCancelable(false); //프래그먼트 밖의 영역을 터치하여도 화면이 dismiss 되는 것을 막음

        return v;
    }

    View.OnClickListener click=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_save:
                    //in.insert("안녕",TABLE_NAME);
                    Toast.makeText(getContext().getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
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
}