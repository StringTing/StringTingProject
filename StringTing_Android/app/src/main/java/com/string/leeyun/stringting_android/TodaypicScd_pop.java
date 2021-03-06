package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.open_id;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.id.sex;
import static com.string.leeyun.stringting_android.Tab_First.openselected;
import static com.string.leeyun.stringting_android.Tab_First.openselected_count;
import static com.string.leeyun.stringting_android.Tab_First.openselected_count_second;
import static com.string.leeyun.stringting_android.Tab_First.openselected_second;


public class TodaypicScd_pop extends Activity {
    int matching_account;
    String matching_sex;
    String sex;
    int account_id;
    String token;
    String what_pic;
    open_id OpenId;

    Rest_ApiService apiService;
    Retrofit retrofit;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_todaypic_scd_pop);


        Intent intent=getIntent();
        matching_account= (int) intent.getSerializableExtra("matching_account");
        matching_sex= (String) intent.getSerializableExtra("matching_sex");
        what_pic=(String)intent.getSerializableExtra("what_pic");

    }

    public void cancle(View v){

        super.onBackPressed();

    }


    public void confirm(View v){
        //코인 사용이 충분할때
        Intent detail = new Intent(this,Personal_profile.class);

        detail.putExtra("matching_account",matching_account);
        detail.putExtra("matching_sex",matching_sex);
        OkHttpClient.Builder client1 =setting_local_data_and_client();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();


        apiService= retrofit.create(Rest_ApiService.class);

        open_id OpenId=new open_id();
        OpenId.setOpen_id(matching_account);
        //추가팝업개발
        try {
            Call<open_id> post_open_id = apiService.post_open_id(OpenId);
            post_open_id.enqueue(new Callback<open_id>() {

                @Override
                public void onResponse(Call<open_id> call5day, retrofit2.Response<open_id> response) {
                    open_id response_open=new open_id();
                    response_open=response.body();
                    Log.e("response_open_result",response_open.getResult());

                }

                @Override
                public void onFailure(Call<open_id> call, Throwable t) {
                    Log.e("open_id 실패", t.toString());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            Log.e("last_introduction","null");
        }



        startActivity(detail);
        finish();
    }

    public OkHttpClient.Builder setting_local_data_and_client(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);


        try {

            sex=pref.getString("sex","notfound");
            Log.e("sex",sex);
            account_id = pref.getInt("account_id",0);
            Log.e("local_account", String.valueOf(account_id));
            token=pref.getString("token","?");
            Log.e("loacal_token",String.valueOf(token));
            if (account_id==0){
                Log.e("localid is null","fail");

            }
            else if(token.equals(null)){
                Log.e("local_token is null","fail");

            }
            else if(sex.equals(null)){
                Log.e("local_sex is null","fail");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;


                newRequest = builder.newBuilder()
                        .addHeader("access-token",token)
                        .addHeader("account-id", String.valueOf(account_id))
                        .addHeader("account-sex",sex)
                        .addHeader("Content-Type","application/json")
                        .build();


                return chain.proceed(newRequest);

            }
        });

       if (what_pic.equals("first")){
           openselected="true";
           openselected_count="true";
       }
        else if(what_pic.equals("second")){
            openselected_count_second="true";
            openselected_second="true";
        }


        return client1;
    }
}
