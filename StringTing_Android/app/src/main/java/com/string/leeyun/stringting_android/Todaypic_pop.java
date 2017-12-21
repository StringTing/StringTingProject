package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tsengvn.typekit.TypekitContextWrapper;

public class Todaypic_pop extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_todaypic_pop);
    }

    public void cancle(View v){
        super.onBackPressed();
    }

    public void confirm(View v){
        //코인 사용이 충분할때
        Intent detail = new Intent(this,Personal_profile.class);
        startActivity(detail);

    }
}
