package com.string.leeyun.stringting_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_message_list;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.okhttp_intercepter_token;
import com.string.leeyun.stringting_android.API.register_message;

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
import static com.string.leeyun.stringting_android.R.mipmap.t;


public class Chatting extends AppCompatActivity  {

    ListView m_ListView;
    ChattingCustom m_Adapter;
    int group_id;
    Rest_ApiService apiService;
    Retrofit retrofit;
    register_message RegisterMessage= new register_message();

    ArrayList<message> messages=new ArrayList<message>();
    String regist_message_result;
    String inputValue = null;

    int account_id;
    String sex;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        //그룹아이디를 받아와 메시지 리스트생성
        group_id = (int)getIntent().getSerializableExtra("group_id");
        get_local_data();
        // 커스텀 어댑터 생성
        m_Adapter = new ChattingCustom();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        Gson gson = new GsonBuilder()
                .create();

        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;


                newRequest = builder.newBuilder()
                        .addHeader("access-token","KS1FVQENJADAOCXA74KUDQMP4KI69BXE")
                        .addHeader("account-id", String.valueOf(115))
                        .addHeader("account-sex","female")
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

        Log.e("sex",sex);


        Call<get_message_list> get_message = apiService.get_message_list_api(2);
        get_message.enqueue(new Callback<get_message_list>() {
            @Override
            public void onResponse(Call<get_message_list> call, Response<get_message_list> response) {

                messages=response.body().getMessages();

                if (response.body()!=null){
                    Log.e("ongetmessage",String.valueOf(messages.get(0).getContents()));
                    Log.e("onresponse", String.valueOf(response.code()));
                    Log.e("onresponse", "success");


                try {
                    for (int i=0;i<messages.size();i++){
                        if (sex.equals(messages.get(i).getSex())){
                            m_Adapter.add(messages.get(i).getContents(),1);
                            m_Adapter.notifyDataSetChanged();

                        }
                        else{
                            m_Adapter.add(messages.get(i).getContents(),0);

                        }

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
                }

            }

            @Override
            public void onFailure(Call<get_message_list> call, Throwable t) {
                Log.d("connectfail", t.toString());
            }

        });



    }
    public void send_message(View v){

        ArrayList<String>arrayList=new ArrayList<>();





        EditText editText = (EditText) findViewById(R.id.input_text);
        inputValue = editText.getText().toString();
        RegisterMessage.setGrounp_id(2);
        RegisterMessage.setContents(inputValue);
        editText.setText("");

        Call<register_message> get_post_register = apiService.get_post_register_message(RegisterMessage);
        get_post_register.enqueue(new Callback<register_message>() {
            @Override
            public void onResponse(Call<register_message> call, Response<register_message> response) {

                RegisterMessage=response.body();
                regist_message_result=String.valueOf(RegisterMessage.getResult());
                Log.e("OnRegisterMessage",String.valueOf(RegisterMessage.getResult()));
                Log.e("onresponse", String.valueOf(response.code()));
                Log.e("onresponse", "success");
                try {
                    Log.e("regist_message_result",regist_message_result);
                    m_Adapter.add(inputValue,1);
                    m_Adapter.notifyDataSetChanged();

                }catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<register_message> call, Throwable t) {
                Log.d("connectfail", t.toString());
            }

        });



    }
    public void get_local_data(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }
}
