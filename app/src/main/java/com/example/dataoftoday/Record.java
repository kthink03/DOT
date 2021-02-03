package com.example.dataoftoday;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Record extends Fragment {
    //*메인 프래그먼트*
    ViewGroup viewGroup;
    Context mContext;
    Dialog popup;
    //화면이 붙을때 작동하는 메서드
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.record, container, false); //xml과 연결
        ImageButton button = (ImageButton) viewGroup.findViewById(R.id.im_book);

        button.setOnClickListener(click);
        return viewGroup;
    }

        View.OnClickListener click =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SCategory_value;

                switch (view.getId()){
                    case R.id.im_book:
                        SCategory_value="책";
                        openDialog(SCategory_value);
                        break;
                    default:
                        break;
            }
        }


    };

    private void openDialog(String s){
       DialogFragment d=DialogFragment.getInstance();
       Bundle args=new Bundle(); //다이얼 프래그먼트에 전달할 값을 담는 객체 Bunble
        args.putString("Category",s); //key 값은 카테고리, value값은 해당 카테고리의 명칭
        d.setArguments(args); //전송

       d.show(getFragmentManager(),DialogFragment.TAG_EVENT_DIALOG);
    }

}

