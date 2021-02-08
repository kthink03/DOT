package com.example.dataoftoday;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static java.lang.Boolean.FALSE;

public class SmallDialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener {
    public static final String TAG="SmallDialog";
    public static final String TAG_EVENT_DIALOG="Small_Dialog";
    public static final String TABLE_NAME="RECORD";
    /*DB 저장 관련*/
    DatabaseManager databaseManager;
    int Year,Month,Day=0;
    int Eval=0; //선택안함: 0 좋아요:1 싫어요:2
    String Category,What,Table_name,Title="";

    public SmallDialogFragment(){} //생성자

    public static SmallDialogFragment getInstance() {
        SmallDialogFragment sd=new SmallDialogFragment();
        return sd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.smallpopup,container);
        final Context mContext=inflater.getContext();

        databaseManager=new DatabaseManager(getContext());

        /*저장할 데이터 bundle로 받기*/
        Bundle mArgs=getArguments();
        Year=mArgs.getInt("Year");
        Month=mArgs.getInt("Month");
        Day=mArgs.getInt("Day");
        Category=mArgs.getString("Category");
        Title=mArgs.getString("Title");
        What=mArgs.getString("What");
        Table_name=mArgs.getString("Table");

        /*버튼 선언 + 클릭 리스너 선언*/
        ImageButton button_like=(ImageButton)v.findViewById(R.id.Button_like);
        button_like.setOnClickListener(click);
        ImageButton button_hate=(ImageButton)v.findViewById(R.id.Button_hate);
        button_hate.setOnClickListener(click);

        setCancelable(false); //프래그먼트 밖의 영역을 터치하여도 화면이 dismiss 되는 것을 막음
        return v;
    }

    View.OnClickListener click=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Button_like:
                    //좋아요 버튼을 누른 경우
                   Eval=1;
                   databaseManager.insert(Year,Month,Day,Category,Title,What,Eval,TABLE_NAME);
                    Log.d(TAG,"Category:"+Category+" What:"+What+" Title:"+Title+" Table_name:"+Table_name+" Year:"+Year+" Eval:"+Eval);
                   dismiss();
                   break;
                case R.id.Button_hate:
                    Eval=2;
                    databaseManager.insert(Year,Month,Day,Category,Title,What,Eval,TABLE_NAME);
                    Log.d(TAG,"Category:"+Category+" What:"+What+" Title:"+Title+" Table_name:"+Table_name+" Year:"+Year+" Eval:"+Eval);
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) { }
}
