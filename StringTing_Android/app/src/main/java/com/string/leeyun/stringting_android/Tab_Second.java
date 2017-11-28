package com.string.leeyun.stringting_android;

import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.leeyun.stringting_android.R;
import com.string.leeyun.stringting_android.API.Get_evalaccount;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tab_Second extends Fragment implements View.OnClickListener {

    private ImageView candy1,candy2,candy3,candy4,candy5;

    private ImageView o_candy1,o_candy2,o_candy3,o_candy4,o_candy5;

    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    userinfo Userinfo= new userinfo();
    Get_evalaccount get_evalaccount=new Get_evalaccount();
    String get_eval_account;
    String generalInfoJson = "{'name': 'Future Studio Dev Team', 'website': 'https://futurestud.io', 'account': [{'name': 'Christian', 'flowerCount': 1 }, {'name': 'Marcus','flowerCount': 3 }, {'name': 'Norman','flowerCount': 2 }]}";

    Gson gson= new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);

        View result=inflater.inflate(R.layout.activity_tab_second, container, false);

        ViewPager pager= (ViewPager)result.findViewById(R.id.viewPager);



        Call<List<Get_evalaccount>> call = apiService.Get_evalaccount("male",1);
        call.enqueue(new Callback<List<Get_evalaccount>>() {
            @Override
            public void onResponse(Call<List<Get_evalaccount>> call, Response<List<Get_evalaccount>> response) {

                List<Get_evalaccount> get_eval_list=response.body();
                Log.e("Get_evalaccount", String.valueOf(get_eval_list.get(1)));

            }

            @Override
            public void onFailure(Call<List<Get_evalaccount>> call, Throwable t) {
                Log.v("onresponseImage2",t.toString());
            }
        });



        //캔디 버튼
        candy1 = (ImageView) result.findViewById(R.id.c1);
        candy2 = (ImageView) result.findViewById(R.id.c2);
        candy3 = (ImageView) result.findViewById(R.id.c3);
        candy4 = (ImageView) result.findViewById(R.id.c4);
        candy5 = (ImageView) result.findViewById(R.id.c5);

        o_candy1 = (ImageView) result.findViewById(R.id.cc1);
        o_candy2 = (ImageView) result.findViewById(R.id.cc2);
        o_candy3 = (ImageView) result.findViewById(R.id.cc3);
        o_candy4 = (ImageView) result.findViewById(R.id.cc4);
        o_candy5 = (ImageView) result.findViewById(R.id.cc5);

        candy1.setOnClickListener(this);
        candy2.setOnClickListener(this);
        candy3.setOnClickListener(this);
        candy4.setOnClickListener(this);
        candy5.setOnClickListener(this);





        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달

        ViewPagerAdapter adapter= new ViewPagerAdapter(getActivity().getLayoutInflater());



        //ViewPager에 Adapter 설정

        pager.setAdapter(adapter);


        return result;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.c1:
                o_candy1.setVisibility(View.VISIBLE);

                o_candy2.setVisibility(View.INVISIBLE);
                o_candy3.setVisibility(View.INVISIBLE);
                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);
                break;

            case R.id.c2:
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);

                o_candy3.setVisibility(View.INVISIBLE);
                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);
                break;

            case R.id.c3:
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);

                o_candy4.setVisibility(View.INVISIBLE);
                o_candy5.setVisibility(View.INVISIBLE);
                break;

            case R.id.c4:
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);
                o_candy4.setVisibility(View.VISIBLE);

                o_candy5.setVisibility(View.INVISIBLE);
                break;

            case R.id.c5:
                o_candy1.setVisibility(View.VISIBLE);
                o_candy2.setVisibility(View.VISIBLE);
                o_candy3.setVisibility(View.VISIBLE);
                o_candy4.setVisibility(View.VISIBLE);
                o_candy5.setVisibility(View.VISIBLE);
                break;
        }

    }




// Inflate the layout for this fragment





}