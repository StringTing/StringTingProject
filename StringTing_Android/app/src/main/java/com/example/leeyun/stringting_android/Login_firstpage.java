package com.example.leeyun.stringting_android;

/**
 * Created by leeyun on 2017. 9. 8..
 */



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by leeyun on 2017. 8. 24..
 */

public class Login_firstpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_firstpage);
    }
    public void onClick_login_local(View v){
        Intent intent = new Intent(getApplicationContext(),Login_local.class);

        startActivity(intent);

    }
}
