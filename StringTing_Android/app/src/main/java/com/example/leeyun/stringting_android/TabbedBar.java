package com.example.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TabbedBar extends AppCompatActivity implements View.OnClickListener {

    private final int FRAGMENT1= 1;
    private final int FRAGMENT2= 2;
    private final int FRAGMENT3= 3;
    private final int FRAGMENT4= 4;
    private final int FRAGMENT5= 5;


    private Button bt_tab1, bt_tab2,bt_tab3,bt_tab4,bt_tab5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_bar);

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
