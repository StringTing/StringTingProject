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


public class Tab_Second extends Fragment implements View.OnClickListener {


    public Tab_Second() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View result=inflater.inflate(R.layout.activity_tab_second, container, false);
        ViewPager pager= (ViewPager)result.findViewById(R.id.viewPager);



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

    }


// Inflate the layout for this fragment





}