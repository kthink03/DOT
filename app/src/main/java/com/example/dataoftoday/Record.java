package com.example.dataoftoday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dataoftoday.R;

public class Record extends Fragment {
    ViewGroup viewGroup;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        viewGroup=(ViewGroup) inflater.inflate(R.layout.record,container,false);
        return viewGroup;
    }
}

