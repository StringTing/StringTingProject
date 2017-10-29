package com.example.leeyun.stringting_android;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import static com.example.leeyun.stringting_android.R.styleable.View;

public class ChatView extends Activity {
    ListView m_ListView;
    ChatCustom m_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

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


        /*요기가 우리가 띄울거
        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                EditText editText = (EditText) findViewById(R.id.input_text);
                String inputValue = editText.getText().toString();
                editText.setText("");
                refresh(inputValue, 0);
            }
        }
        );*/



        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener() {
            int i=1;
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.input_text);
                String inputValue = editText.getText().toString();
                editText.setText("");
                refresh(inputValue, 1);
                m_Adapter.add("case" + i, 0);
                i++;
            }
        }
        );
    }

    private void refresh (String inputValue, int _str) {
        m_Adapter.add(inputValue,_str) ;
        m_Adapter.notifyDataSetChanged();
    }
    public void onClick_back(View v) {
        super.onBackPressed(); // or super.finish();

    }

}
