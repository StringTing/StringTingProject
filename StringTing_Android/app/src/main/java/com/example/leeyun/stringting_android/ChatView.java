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
    userinfo Userinfo = new userinfo();

    static  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        Intent intent=getIntent();

        ArrayList<String> Imageupload_countList=new ArrayList<>();

        Imageupload_countList=intent.getExtras().getStringArrayList("ProfileFilepate");

        for (int i=0;i<Imageupload_countList.size();i++){
            Log.v("Imagefilepath",Imageupload_countList.get(i));
        }
        Userinfo = (userinfo)getIntent().getSerializableExtra("UserInfo");

        final String Userinfo_Json= new Gson().toJson(Userinfo);
        Log.e("TestUserinfoGson",Userinfo_Json);            //userinfo정보를 json타입으로 변환

        savePreferences(Userinfo.getEmail());

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
        //수정 버튼을 클릭하면 edittext를 받아서 listview에 세팅해줌
        findViewById(R.id.modify_sendbtn).setOnClickListener(new Button.OnClickListener() {
            int i=1;
            @Override
            public void onClick(View v) {

                EditText editText =(EditText)findViewById(R.id.modify_Edit);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
                Toast.makeText(getApplicationContext(), "modify_send", Toast.LENGTH_LONG).show();
                String modifyString = editText.getText().toString();
                Log.v("modifyString",modifyString);

                m_Adapter.getM_List().set(position, new ChatCustom.ListContents(modifyString,position));
                m_Adapter.notifyDataSetChanged();

                LinearLayout l2 = (LinearLayout)findViewById(R.id.enter_chatting_visible);
                l2.setVisibility(View.GONE);
                LinearLayout ll = (LinearLayout)findViewById(R.id.enter_chatting);
                ll.setVisibility(View.VISIBLE);


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
        intent.putExtra("Userinfo",Userinfo);
        startActivity(intent);
    }

    // 값 저장하기
        public void savePreferences(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Id",data);
        editor.commit();
    }



    //수정하기 버튼을 눌렀을때 position을 받아옴, 전송버튼을 수정버튼으로 바꿔줌
    public void modify(View view) {

        LinearLayout ll = (LinearLayout)findViewById(R.id.enter_chatting);
        ll.setVisibility(View.GONE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.enter_chatting_visible);
        l2.setVisibility(View.VISIBLE);

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

            position=m_ListView.getPositionForView(view);

            Log.v("checked", String.valueOf(position));
        }

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
