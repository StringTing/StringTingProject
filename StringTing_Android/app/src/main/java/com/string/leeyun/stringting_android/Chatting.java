package com.string.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.leeyun.stringting_android.R;

public class Chatting extends AppCompatActivity  {

    ListView m_ListView;
    ChattingCustom m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);



        // 커스텀 어댑터 생성
        m_Adapter = new ChattingCustom();


        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        m_Adapter.add("TEST01", 0);

        m_Adapter.add("TEST01", 1);


    }
}
