package com.example.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import static android.view.View.GONE;
import static com.example.leeyun.stringting_android.R.drawable.gametitle_01;


public class Tab_First extends Fragment  {
    LinearLayout topLL;


    public Tab_First() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View result=inflater.inflate(R.layout.activity_tab_first, container, false);


       //더보기 버튼
        result.findViewById(R.id.add_btn).setOnClickListener(new LinearLayout.OnClickListener(){
            @Override
            public void onClick(View v) {
                //더보기 버튼 지우기
                LinearLayout btn = (LinearLayout) getView().findViewById(R.id.add_btn);
                btn.setVisibility(GONE);

                int[] d = {0,1,2,3,4};
                int re = d.length/3;
                //몫
                for(int i =0; i<=re; i++) {
                    //부모뷰
                    topLL = (LinearLayout)getView().findViewById(R.id.addpic);

                    //새로운 리니어레이아웃
                    LinearLayout parentLL = new LinearLayout(getContext());

                    //새로운 리니어뷰의 레이아웃 설정
                    parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    parentLL.setOrientation(LinearLayout.HORIZONTAL);
                    topLL.addView(parentLL);

                    int mod = 3-(d.length%3);
                    //d%3 ==0일경우

                    for(int e=0; e<=2;e++){
                        //새로운 이미지뷰
                        ImageView iv = new ImageView(getContext());
                        iv.setImageResource(R.drawable.kakao_default_profile_image);

                        iv.setMinimumWidth(250);
                        iv.setMinimumHeight(250);

                        //layout_width, layout_height, gravity 설정
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                        lp.gravity = Gravity.CENTER;
                        iv.setLayoutParams(lp);

                        parentLL.addView(iv);
                    }


                }

            }
        });
        return result;


    }

}
