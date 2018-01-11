package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_introduction_qnalist;
import com.string.leeyun.stringting_android.API.post_item;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;

public class Chat_pop extends Activity {

    Retrofit retrofit;
    Rest_ApiService apiService;
    int account_id;
    String sex;
    String token;
    post_item PostItem;
    //item 결제 성공여부를 판단함
    String post_item_result;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat_pop);


        Intent intent=getIntent();
        account_id= (Integer) intent.getSerializableExtra("account_id");
        sex= (String) intent.getSerializableExtra("sex");
        token=(String)intent.getSerializableExtra("token");




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

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();


        apiService= retrofit.create(Rest_ApiService.class);

        PostItem=new post_item();
        PostItem.setItem_id(3);
        Call<post_item> post_itemCall = apiService.post_item(PostItem);
        post_itemCall.enqueue(new Callback<post_item>() {
            @Override
            public void onResponse(Call<post_item> call, Response<post_item> response) {
                    PostItem=response.body();
                    post_item_result=PostItem.getResult();
                    Log.e("post_id 결과값",PostItem.getResult());
            }

            @Override
            public void onFailure(Call<post_item> call, Throwable t) {
                Log.d("post_id 서버연결 실패", t.toString());
            }

        });


    }

    public void cancle(View v){
        super.onBackPressed();
    }

    public void confirm(View v){
        //대화요청후 어떻게..?


        //코인부족하면
        if(post_item_result.equals(false)){
            Intent chargecoin = new Intent(this,Chargecoin_pop.class);
            startActivity(chargecoin);
        }else{
            super.onBackPressed();
        }

    }
}
