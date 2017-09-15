package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by leeyun on 2017. 9. 8..
 */

public class Membership_form extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);
    }


    public void onClick_Basicinfo_Edit(View v){
        Intent intent = new Intent(getApplicationContext(),Basicinfo_Edit.class);
        startActivity(intent);
    }

}
