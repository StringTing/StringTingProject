package com.example.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Tab_Second extends Fragment implements View.OnClickListener {

    public Tab_Second() {
        // Required empty public constructor
    }
    ImageButton btn_change;
    int i=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_tab_second, container, false);

    }

    @Override
    public void onClick(View view) {


    }
}