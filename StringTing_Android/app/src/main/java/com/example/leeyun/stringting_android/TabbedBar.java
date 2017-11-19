package com.example.leeyun.stringting_android;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TabbedBar extends AppCompatActivity implements View.OnClickListener {

    private final int FRAGMENT1= 1;
    private final int FRAGMENT2= 2;
    private final int FRAGMENT3= 3;
    private final int FRAGMENT4= 4;
    private final int FRAGMENT5= 5;

    ViewPager pager;

    private LinearLayout bt_tab1,bt_tab2,bt_tab3,bt_tab4,bt_tab5;


    private LinearLayout on_tab1,on_tab2,on_tab3,on_tab4,on_tab5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_bar);





        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달




        // 위젯에 대한 참조
        bt_tab1 = (LinearLayout) findViewById(R.id.tab1);
        bt_tab2 = (LinearLayout) findViewById(R.id.tab2);
        bt_tab3 = (LinearLayout) findViewById(R.id.tab3);
        bt_tab4 = (LinearLayout) findViewById(R.id.tab4);
        bt_tab5 = (LinearLayout) findViewById(R.id.tab5);


        on_tab1 = (LinearLayout) findViewById(R.id.on_tab1);
        on_tab2 = (LinearLayout) findViewById(R.id.on_tab2);
        on_tab3 = (LinearLayout) findViewById(R.id.on_tab3);
        on_tab4 = (LinearLayout) findViewById(R.id.on_tab4);
        on_tab5 = (LinearLayout) findViewById(R.id.on_tab5);



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
            case R.id.tab1 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.tab2 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;

            case R.id.tab3 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT3);
                break;

            case R.id.tab4:
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT4);
                break;

            case R.id.tab5 :
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
                on_tab1.setVisibility(View.VISIBLE);

                on_tab2.setVisibility(View.INVISIBLE);
                on_tab3.setVisibility(View.INVISIBLE);
                on_tab4.setVisibility(View.INVISIBLE);
                on_tab5.setVisibility(View.INVISIBLE);
                break;

            case 2:
                // '프래그먼트2' 호출
                Tab_Second fragment2 = new Tab_Second();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                on_tab2.setVisibility(View.VISIBLE);


                on_tab1.setVisibility(View.INVISIBLE);
                on_tab3.setVisibility(View.INVISIBLE);
                on_tab4.setVisibility(View.INVISIBLE);
                on_tab5.setVisibility(View.INVISIBLE);
                break;

            case 3:
                // '프래그먼트2' 호출
                Tab_Third fragment3 = new Tab_Third();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                on_tab3.setVisibility(View.VISIBLE);

                on_tab1.setVisibility(View.INVISIBLE);
                on_tab2.setVisibility(View.INVISIBLE);
                on_tab4.setVisibility(View.INVISIBLE);
                on_tab5.setVisibility(View.INVISIBLE);
                break;

            case 4:
                // '프래그먼트2' 호출
                Tab_Fourth fragment4 = new Tab_Fourth();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                on_tab4.setVisibility(View.VISIBLE);

                on_tab1.setVisibility(View.INVISIBLE);
                on_tab2.setVisibility(View.INVISIBLE);
                on_tab3.setVisibility(View.INVISIBLE);
                on_tab5.setVisibility(View.INVISIBLE);
                break;

            case 5:
                // '프래그먼트2' 호출
                Tab_Fifth fragment5 = new Tab_Fifth();
                transaction.replace(R.id.fragment_container, fragment5);
                transaction.commit();
                on_tab5.setVisibility(View.VISIBLE);

                on_tab1.setVisibility(View.INVISIBLE);
                on_tab2.setVisibility(View.INVISIBLE);
                on_tab3.setVisibility(View.INVISIBLE);
                on_tab4.setVisibility(View.INVISIBLE);
                break;
        }

    }
}
