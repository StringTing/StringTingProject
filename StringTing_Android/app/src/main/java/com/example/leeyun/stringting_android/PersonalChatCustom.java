package com.example.leeyun.stringting_android;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;
import static android.view.View.GONE;

/**
 * Created by sg970 on 2017-10-28.
 */

public class PersonalChatCustom extends BaseAdapter{
    public class ListContents{
        String msg;
        int type;
        ListContents(String m, int t){
            this.msg= m;
            this.type = t;
        }
    }

    private ArrayList<ListContents> m_List;
    public PersonalChatCustom() {
        m_List = new ArrayList<ListContents>();
    }
    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg,int _type) {m_List.add(new ListContents(_msg,_type));}


    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        TextView        text    = null;
        CustomHolder    holder  = null;
        RelativeLayout layout  = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.personal_chat_view_item, parent, false);
            layout    = (RelativeLayout) convertView.findViewById(R.id.layout);
            text    = (TextView) convertView.findViewById(R.id.text);

            // 메인리스트 - 수정버튼
            //Button OptionButton = (Button) convertView.findViewById(R.id.modify_btn);
            //OptionButton.setOnClickListener((View.OnClickListener)context);
            //OptionButton.setTag(welarmDTOMap.get("welarmNo").toString());


            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView   = text;
            holder.layout = layout;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
            layout  = holder.layout;
        }

        // Text 등록
        text.setText(m_List.get(position).msg);



        if( m_List.get(position).type == 0 ) {
            text.setBackgroundResource(R.drawable.bubble_left);
            layout.setGravity(Gravity.LEFT);

        }else if(m_List.get(position).type == 1) {
            text.setBackgroundResource(R.drawable.bubble_right);
            layout.setGravity(Gravity.RIGHT);
        }



        return convertView;
    }



    private class CustomHolder {
        TextView    m_TextView;
        RelativeLayout layout;
    }
}
