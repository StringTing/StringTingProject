package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.leeyun.stringting_android.R.id.Email;
import static com.example.leeyun.stringting_android.R.id.Email_checkText;
import static com.example.leeyun.stringting_android.R.id.Pw_check;
import static com.example.leeyun.stringting_android.R.id.Pw_checkText;
import static com.example.leeyun.stringting_android.R.id.Pw_edit;
import static com.example.leeyun.stringting_android.R.id.Pw_equalText;
import static com.example.leeyun.stringting_android.R.id.Pw_text;
import static com.example.leeyun.stringting_android.R.id.check_pw;

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


        EditText Check_email= (EditText)findViewById(Email);
        TextView Email_check_fail=(TextView)findViewById(Email_checkText);   //이메일과 패스워드가 정규식을 통과하지 못했을때.
        TextView Pw_check_fail=(TextView)findViewById(Pw_checkText);
        TextView Pw_equal_fail=(TextView)findViewById(Pw_equalText);
        EditText Check_pw=(EditText)findViewById(Pw_edit);
        EditText EqualCheck_pw=(EditText)findViewById(check_pw);


        String Email_CheckText="Email이 올바르지않은 형식입니다.";
        String PW_CheckText="문자,숫자,특수문자를 포함해주세요.";
        String Equal_PW="비밀번호가 일치하지 않습니다.";
        String Email=Check_email.getText().toString();
        String PW=Check_pw.getText().toString();
        String EqualPw=EqualCheck_pw.getText().toString();

        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",Email.trim());
        boolean a = Pattern.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])",PW.trim());
        if(!b)
        {
              Email_check_fail.setText(Email_CheckText);

        }

        if(a)
        {
            //pw가 맞는 형식임

            //email이 맞는형식임

            if(PW.equals(EqualPw)) {
                Intent intent = new Intent(getApplicationContext(), Basicinfo_Edit.class);
                startActivity(intent);

            }
            else
            {
                Pw_equal_fail.setText(Equal_PW);
            }
        }

        else{
            Pw_check_fail.setText(PW_CheckText);

        }




    }



    public void onClick_back(View v){
        super.onBackPressed(); // or super.finish();

    }
}
