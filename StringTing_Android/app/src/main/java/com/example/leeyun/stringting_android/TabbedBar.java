package com.example.leeyun.stringting_android;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.leeyun.stringting_android.API.ResponseApi;
import com.example.leeyun.stringting_android.API.Rest_ApiService;
import com.example.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabbedBar extends AppCompatActivity implements View.OnClickListener {

    private final int FRAGMENT1= 1;
    private final int FRAGMENT2= 2;
    private final int FRAGMENT3= 3;
    private final int FRAGMENT4= 4;
    private final int FRAGMENT5= 5;

    ViewPager pager;

    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;

    userinfo Userinfo=new userinfo();
    private Button bt_tab1, bt_tab2,bt_tab3,bt_tab4,bt_tab5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_bar);



        Intent intent =getIntent();

        Userinfo = (userinfo)getIntent().getSerializableExtra("Userinfo");
        final String Userinfo_Json= new Gson().toJson(Userinfo);
        Log.e("Userinfo_Json",Userinfo_Json);
        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URLTest).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);



        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달



        Call<userinfo>PostUserinfo = apiService.getPostUserinfo(Userinfo);
        PostUserinfo.enqueue(new Callback<userinfo>() {
            @Override
            public void onResponse(Call<userinfo> call, Response<userinfo> response) {


                userinfo gsonresponse=response.body();
                Log.v("onresponse", gsonresponse.getResult());
                Log.v("onresponse", String.valueOf(response.code()));
                if("success".equals(gsonresponse.getResult())){
                    Log.v("onresponse", "success");

                }
                else{
                    Log.v("onresponse","fail");
                }



            }

            @Override
            public void onFailure(Call<userinfo> call, Throwable t) {
                Log.d("sam", "fail");
            }
        });



        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.button1);
        bt_tab2 = (Button)findViewById(R.id.button2);
        bt_tab3 = (Button)findViewById(R.id.button3);
        bt_tab4 = (Button)findViewById(R.id.button4);
        bt_tab5 = (Button)findViewById(R.id.button5);


        // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        bt_tab4.setOnClickListener(this);
        bt_tab5.setOnClickListener(this);

        callFragment(FRAGMENT1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.button2 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;

            case R.id.button3 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT3);
                break;

            case R.id.button4 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT4);
                break;

            case R.id.button5 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT5);
                break;
        }
    }

    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                Tab_First fragment1 = new Tab_First();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                Tab_Second fragment2 = new Tab_Second();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;

            case 3:
                // '프래그먼트2' 호출
                Tab_Third fragment3 = new Tab_Third();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                break;

            case 4:
                // '프래그먼트2' 호출
                Tab_Fourth fragment4 = new Tab_Fourth();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                break;

            case 5:
                // '프래그먼트2' 호출
                Tab_Fifth fragment5 = new Tab_Fifth();
                transaction.replace(R.id.fragment_container, fragment5);
                transaction.commit();
                break;
        }

    }
}
