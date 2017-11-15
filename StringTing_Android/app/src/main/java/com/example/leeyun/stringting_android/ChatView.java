package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ChatView extends Activity implements AdapterView.OnItemClickListener {
    ListView m_ListView;
    ChatCustom m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        final userinfo Userinfo = (userinfo)getIntent().getSerializableExtra("UserInfo");

        final String Userinfo_Json= new Gson().toJson(Userinfo);
        Log.e("TestUserinfoGson",Userinfo_Json);            //userinfo정보를 json타입으로 변환

        savePreferences(Userinfo.getId());

        SharedPreferences example= getSharedPreferences("Local_DB", MODE_PRIVATE);
        String str = example.getString("Id", "");
        Log.e("localdbtest",str);







        // 커스텀 어댑터 생성
        m_Adapter = new ChatCustom();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);





        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener() {
            int i=1;
            @Override
            public void onClick(View v) {

                    EditText editText = (EditText) findViewById(R.id.input_text);
                    String inputValue = editText.getText().toString();
                    if(inputValue.length()>=5){
                        editText.setText("");
                        refresh(inputValue, 1);
                        m_Adapter.add("case" + i, 0);
                        i++;
                    }
                if(i==5){
                    //키보드 내려가는거
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    //버튼 바뀌는거
                    LinearLayout ll = (LinearLayout)findViewById(R.id.enter_chatting);
                    ll.setVisibility(View.INVISIBLE);
                    Button btn =(Button) findViewById(R.id.next_btn);
                    btn.setVisibility(View.VISIBLE);

                }
            }
        }
        );


    }


    private void refresh (String inputValue, int _str) {
        m_Adapter.add(inputValue,_str) ;
        m_Adapter.notifyDataSetChanged();
    }
/*
    private void replacee (String inputValue ,int _str){
        m_Adapter.remove(_str);
        m_Adapter.notifyDataSetChanged();
    }*/


    public void onClick_back(View v) {
        super.onBackPressed(); // or super.finish();

    }

    public void onClick_TabbedBar(View v){               //basicinfo에서 불러온 정보들을 변수에 저장
        Intent intent = new Intent(getApplicationContext(), TabbedBar.class);
        startActivity(intent);
    }

    // 값 저장하기
        public void savePreferences(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Id",data);
        editor.commit();
    }


    public void modify(View view) {


        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        EditText editText =(EditText)findViewById(R.id.input_text);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);



        String modifyString = editText.getText().toString();
        Log.v("modifyString",modifyString);

        int count,checked;
        count=m_Adapter.getCount();
        Log.v("count", String.valueOf(count));
        if(count>0){
            checked=m_ListView.getCheckedItemPosition();

            Log.v("checked", String.valueOf(checked));
                m_Adapter.getM_List().set(1, new ChatCustom.ListContents(modifyString,1));

                m_Adapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
