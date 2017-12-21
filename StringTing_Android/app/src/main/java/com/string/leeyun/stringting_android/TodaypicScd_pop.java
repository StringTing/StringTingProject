package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tsengvn.typekit.TypekitContextWrapper;

public class TodaypicScd_pop extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_todaypic_scd_pop);
    }


    public void cancle(View v){
        super.onBackPressed();
    }

    public void confirm(View v){
/*
        //코인이 충분할때
        Intent detail = new Intent(this,Personal_profile.class);
        startActivity(detail);
*/

        //코인이 부족할때
        Intent chargecoin = new Intent(this,Chargecoin_pop.class);
        startActivity(chargecoin);
    }
}
