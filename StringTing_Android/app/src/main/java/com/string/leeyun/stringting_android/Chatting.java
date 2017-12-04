package com.string.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.string.leeyun.stringting_android.R.mipmap.m;


public class Chatting extends AppCompatActivity  {

    ListView m_ListView;
    ChattingCustom m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        //그룹아이디를 받아와 메시지 리스트생성

        // 커스텀 어댑터 생성
        m_Adapter = new ChattingCustom();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);








    }
    public void send_message(View v){

        ArrayList<String>arrayList=new ArrayList<>();
        arrayList.add("배고프다");
        arrayList.add("내일 닭발먹으니까 괜찮다");
        String inputValue = null;
        for (int i=0;i<arrayList.size();i++) {
                m_Adapter.add(arrayList.get(i), 0);
                EditText editText = (EditText) findViewById(R.id.input_text);
                if (editText.getText()!=null){
                    inputValue = editText.getText().toString();
                    editText.setText("");
                    m_Adapter.add(inputValue,1);

                }


            m_Adapter.notifyDataSetChanged();

        }



    }
}
