package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.userinfo;

import java.io.File;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.R.id.Email;
import static com.string.leeyun.stringting_android.R.id.Email_checkText;
import static com.string.leeyun.stringting_android.R.id.Pw_checkText;
import static com.string.leeyun.stringting_android.R.id.Pw_edit;
import static com.string.leeyun.stringting_android.R.id.Pw_equalText;
import static com.string.leeyun.stringting_android.R.id.check_pw;


/**
 * Created by leeyun on 2017. 9. 8..
 */

public class Membership_form extends Activity {

    ResponseApi responapi =new ResponseApi();
    userinfo Userinfo =new userinfo();
    Rest_ApiService apiService;
    Retrofit retrofit;

    File Postfile;

    private Dialog dialog;








    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);

        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);


   /*
        //입력확인
        Check_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ce.length()>=1){
                    c1.setBackgroundResource(R.drawable.rounded_check_20_edittext);
                } else {
                    c1.setBackgroundResource(R.drawable.rounded_20_edittext);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/

    }

    public void onClick_Basicinfo_Edit(View v) throws MalformedURLException {


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


        responapi.setEmail(Email);





        Call<ResponseApi> comment = apiService.getPostEmailStr1(responapi);
        comment.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {


                ResponseApi gsonresponse=response.body();
                Log.v("onresponse_Imagecheck", gsonresponse.getResult());
                Log.v("onresponse_Imagecheck",gsonresponse.getMessage());
                Log.v("onresponse_Imagecheck", String.valueOf(response.code()));

                if("true".equals(gsonresponse.getResult())){
                    Log.v("onresponse_Imagecheck", "success");

                }
                else{
                    Log.v("onresponse","fail");
                }



            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Log.d("sam", "fail");
            }
        });


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
                intent.putExtra("ID",Email);
                intent.putExtra("PW",PW);
                intent.putExtra("setformat","EMAIL");
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
