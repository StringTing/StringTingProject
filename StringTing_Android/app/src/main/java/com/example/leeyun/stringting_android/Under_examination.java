package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by leeyun on 2017. 11. 27..
 */

public class Under_examination extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.under_examination);



    }

    public void onClick_tapbar(){
        Intent intent = new Intent(getApplicationContext(), TabbedBar.class);
        startActivity(intent);
    }
}
