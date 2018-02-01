package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.check_login;
import com.string.leeyun.stringting_android.API.userinfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.lang.reflect.Member;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.BLACK;
import static com.string.leeyun.stringting_android.R.id.Email;
import static com.string.leeyun.stringting_android.R.id.Email_checkText;
import static com.string.leeyun.stringting_android.R.id.Pw_checkText;
import static com.string.leeyun.stringting_android.R.id.Pw_edit;
import static com.string.leeyun.stringting_android.R.id.Pw_equalText;
import static com.string.leeyun.stringting_android.R.id.check_pw;
import static com.string.leeyun.stringting_android.R.id.question;


/**
 * Created by leeyun on 2017. 9. 8..
 */

public class Membership_form extends Activity {

    ResponseApi responapi =new ResponseApi();
    userinfo Userinfo =new userinfo();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String token;
    String fcm_token;
    File Postfile;

    private Dialog dialog;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);

        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);


        final EditText email_edit = (EditText) findViewById(R.id.Email);
        final EditText pw_edit = (EditText)findViewById(R.id.Pw_edit);
        final EditText c_pw_edit =(EditText)findViewById(R.id.check_pw);



        email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // 입력이 끝났을 때
            @Override
            public void afterTextChanged(Editable editable) {
                String ch = email_edit.getText().toString();
                ImageView check_email = (ImageView)findViewById(R.id.check_email_img) ;
                if(checkEmail(ch)){
                    check_email.setVisibility(View.VISIBLE);
                }else {
                    check_email.setVisibility(View.GONE);
                }
            }

            // 입력하기 전에
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });


        pw_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // 입력이 끝났을 때
            @Override
            public void afterTextChanged(Editable editable) {
                String ch = pw_edit.getText().toString();
                ImageView check_pw_edit = (ImageView)findViewById(R.id.check_pw_edit_img) ;
                if (checkPW(ch)){
                    check_pw_edit.setVisibility(View.VISIBLE);
                }else{
                    check_pw_edit.setVisibility(View.GONE);
                }
            }

            // 입력하기 전에
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        c_pw_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // 입력이 끝났을 때
            @Override
            public void afterTextChanged(Editable editable) {
                String ch = pw_edit.getText().toString();
                String ch_2 =c_pw_edit.getText().toString();
                ImageView check_pw = (ImageView)findViewById(R.id.check_pw_img) ;
                if (ch.equals(ch_2)){
                    check_pw.setVisibility(View.VISIBLE);
                }else{
                    check_pw.setVisibility(View.GONE);
                }
            }

            // 입력하기 전에
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
    }

    /**
     * 이메일 포맷 체크
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }


    public static boolean checkPW(String pw){

        String regex = "^[A-Za-z0-9_@./#&+-]*.{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pw);
        boolean isNormal = m.matches();
        return isNormal;

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
        final String Email=Check_email.getText().toString();
        final String PW=Check_pw.getText().toString();
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
                get_total_data_save();
                check_login CheckLogin=new check_login();
                CheckLogin.setEmail(Email);
                CheckLogin.setPassword(PW);
                CheckLogin.setFcm_token(fcm_token);
                try {
                    Call<check_login> post_check_login = apiService.post_check_login(CheckLogin);
                    post_check_login.enqueue(new Callback<check_login>() {
                        @Override
                        public void onResponse(Call<check_login> call, Response<check_login> response) {


                            Intent intent_activate = new Intent(Membership_form.this,  TabbedBar.class);
                            Intent intent_ghost= new Intent(Membership_form.this, Basicinfo_Edit.class);
                            Intent intent_interview=new Intent(Membership_form.this, Mediate.class);
                            check_login gsonresponse = response.body();
                            try {
                                Log.e("onresponse_check_login", gsonresponse.getResult());
                                Log.e("onresponse_check_login", gsonresponse.getMessage());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                int account_id= Integer.parseInt(gsonresponse.getId());
                                String sex=gsonresponse.getSex();
                                put_total_data_save(gsonresponse.getToken(), Integer.parseInt(gsonresponse.getId()),gsonresponse.getSex());

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            if (gsonresponse.getStatus()==null) {

                                Log.e("get_status","등록되지않은 이메일입니다");
                                intent_ghost.putExtra("ID",Email);
                                intent_ghost.putExtra("PW",PW);
                                intent_ghost.putExtra("setformat","EMAIL");
                                intent_ghost.putExtra("token",token);
                                startActivity(intent_ghost);
                            }
                            else
                            {
                                Log.e("onresponse", gsonresponse.getStatus());
                                Log.e("onresponse", String.valueOf(response.code()));
                                Log.e("onresponse", "success");

                                if (gsonresponse.getStatus().equals("ACTIVATE"))
                                {

                                    startActivity(intent_activate);
                                }
                                else if(gsonresponse.getStatus().equals("GHOST")){
                                    intent_ghost.putExtra("ID",Email);
                                    intent_ghost.putExtra("PW",PW);
                                    intent_ghost.putExtra("setformat","EMAIL");
                                    intent_ghost.putExtra("token",token);

                                    startActivity(intent_ghost);
                                }
                                else if(gsonresponse.getStatus().equals("INREVIEW")){
                                    startActivity(intent_interview);
                                }
                                else if(gsonresponse.getStatus().equals("REJECTED")){

                                }
                                else{
                                    intent_ghost.putExtra("ID",Email);
                                    intent_ghost.putExtra("PW",PW);
                                    Log.e("id",Email);
                                    intent_ghost.putExtra("setformat","EMAIL");
                                    intent_ghost.putExtra("token",token);

                                    startActivity(intent_ghost);
                                }
                            }




                        }


                        @Override
                        public void onFailure(Call<check_login> call, Throwable t) {
                            Log.d("sam", t.toString());
                        }

                    });
                }catch (Exception e){
                    e.printStackTrace();
                }


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


    public void put_total_data_save(String token,int account_id,String sex){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
            editor.putString("token",token);
            editor.putInt("account_id",account_id);
            editor.putString("sex",sex);
            editor.clear();


        editor.commit();
    }
    public void get_total_data_save(){
        SharedPreferences pref1 = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();

        fcm_token=pref1.getString("fcm_token","fcm_token is null");

        editor.commit();
    }


}
