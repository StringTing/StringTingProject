package com.example.leeyun.stringtingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.leeyun.stringtingproject.R;

/**
 * Created by leeyun on 2017. 8. 30..
 */

public class Login_firstpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_firstpage);
    }
    public void onClick_Login_local(View v){
        Intent intent = new Intent(getApplicationContext(),Login_local.class);

        startActivity(intent);

    }
}