package com.example.leeyun.stringting_android;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Tab_Second extends Fragment implements View.OnClickListener {

    private ImageView candy1,candy2,candy3,candy4,candy5;

    private ImageView o_candy1,o_candy2,o_candy3,o_candy4,o_candy5;


    public Tab_Second() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View result=inflater.inflate(R.layout.activity_tab_second, container, false);
        ViewPager pager= (ViewPager)result.findViewById(R.id.viewPager);


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