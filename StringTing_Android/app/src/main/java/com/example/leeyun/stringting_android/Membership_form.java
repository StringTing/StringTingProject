package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leeyun on 2017. 9. 8..
 */

public class Membership_form extends Activity {

    private Dialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);



    }







    public void onClick_Basicinfo_Edit(View v){


        EditText Check_email= (EditText)findViewById(R.id.Email);
        String Email=Check_email.getText().toString();

        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",Email.trim());
        if(b)
        {
            //email이 맞는형식임

        }
        else
        {
            Toast.makeText(Membership_form.this, "Email이 올바르지않은 형식입니다", Toast.LENGTH_SHORT).show();

        }

        Intent intent = new Intent(getApplicationContext(),Basicinfo_Edit.class);
        startActivity(intent);

    }


}
