package com.string.leeyun.stringting_android;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class Coin_White extends AppCompatActivity {

    TextView tv;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_white);
/*

        실제 id 값들
        {R.id.resolution_btn_0, R.id.resolution_btn_1, R.id.resolution_btn_2, R.id.resolution_btn_3, R.id.resolution_btn_4, R.id.resolution_btn_5, R.id.resolution_btn_125}

*/
        for ( int i = 1 ; i<=6;i++){
            String buttonID = "basic_coin" + i ;
            int coin_strike = getResources().getIdentifier(buttonID, "id", this.getPackageName());
            tv= (TextView) findViewById(coin_strike);
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }




    }

    @Override
    public void onBackPressed() {
        Coin_White.this.finish();
    }


    public void onClick_Back(View v){
        super.onBackPressed();
    }
}
