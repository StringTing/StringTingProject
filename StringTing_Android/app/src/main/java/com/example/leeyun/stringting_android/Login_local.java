package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by leeyun on 2017. 9. 8..
 */

/**
 * Created by leeyun on 2017. 8. 24..
 */
public class Login_local extends Activity {



        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_local);


        }


    public void onClick_login_check(View v){

        String Test_ID="StringTing";
        String Test_PW="wearecro123";

        EditText test_ID= (EditText)findViewById(R.id.id_Edit);
        String Edit_id=test_ID.getText().toString();



        if(Edit_id.equals(Test_ID)){
            Toast.makeText(Login_local.this, "로그인 성공", Toast.LENGTH_SHORT).show();


        }
        else{
            Toast.makeText(Login_local.this, "로그인실패", Toast.LENGTH_SHORT).show();
        }


    }
    public void onClick_back(View v){
        super.onBackPressed(); // or super.finish();

    }

}
