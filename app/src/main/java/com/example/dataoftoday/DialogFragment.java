package com.example.dataoftoday;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener{

    public static final String TAG_EVENT_DIALOG="dialog_event";
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

        ImageButton Save=(ImageButton)v.findViewById(R.id.button_save);
        Save.setOnClickListener(this);
        setCancelable(false); //프래그먼트 밖의 영역을 터치하여도 화면이 dismiss 되는 것을 막음
        return v;
    }
    @Override
    public void onClick(View view) {

    }

    public void onResume() {
        int width=getResources().getDimensionPixelOffset(R.dimen.dialog_width);
        super.onResume();
    }
}