package com.example.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;


public class Personal_profile extends AppCompatActivity {
    ListView m_ListView;
    PersonalChatCustom m_Adapter;
    int[] photo = {0,1,2,3,4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        //부모뷰
        LinearLayout dynamic = (LinearLayout) findViewById(R.id.pic_view);
        dynamic.setPadding(250, 0, 30, 0);

        //새로운이미지
        for (int i = 0; i < photo.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.kakao_default_profile_image);

            // iv.setMinimumWidth(210);
            // iv.setMinimumHeight(280);

            //layout_width, layout_height, gravity 설정
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(500, 700, 1f);
            lp.setMarginStart(30);
            lp.setMarginEnd(30);
            lp.gravity = Gravity.CENTER;
            iv.setLayoutParams(lp);

            dynamic.addView(iv);
        }

        // 커스텀 어댑터 생성
        m_Adapter = new PersonalChatCustom();


        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 1);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        setListViewHeightBasedOnItems(m_ListView);

    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
