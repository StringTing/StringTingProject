package com.string.leeyun.stringting_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;


public class Tab_Fifth extends Fragment  {

    RelativeLayout coin,notice,push,inquire;

    ImageView profile;

    public Tab_Fifth() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_tab_fifth, container, false);

        profile = (ImageView) v.findViewById(R.id.profile);

        Picasso.with(getActivity()).load(R.drawable.gametitle_01).transform(new CircleTransForm()).into(profile);

        coin = (RelativeLayout) v.findViewById(R.id.coin_charge);
        notice = (RelativeLayout) v.findViewById(R.id.notice);
        push = (RelativeLayout) v.findViewById(R.id.push_setting);
        inquire = (RelativeLayout) v.findViewById(R.id.inquire);

        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Coin.class);
                startActivity(i);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Notice_webview.class);
                startActivity(i);
            }
        });

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });


        inquire.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                // email setting 배열로 해놔서 복수 발송 가능
                String[] address = {"wearecro@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT,"스트링 고객센터에 문의합니다");
                email.putExtra(Intent.EXTRA_TEXT,"문의 할 내용을 적어주세요\n\n내용:\n\n\n\n\nString계정 : \nOS version : Android");
                startActivity(email);

            }
        });






        return v;
    }


}
