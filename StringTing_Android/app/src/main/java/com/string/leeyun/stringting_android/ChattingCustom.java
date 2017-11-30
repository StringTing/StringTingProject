package com.string.leeyun.stringting_android;

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

public class ChattingCustom extends BaseAdapter{
    public static class ListContents{
        String msg;
        int type;
        ListContents(String m, int t){
            this.msg= m;
            this.type = t;
        }


    }

    public ArrayList<ListContents> getM_List() {
        return m_List;
    }

    private ArrayList<ListContents> m_List;
    public ChattingCustom() {
        m_List = new ArrayList<ListContents>();
    }
    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg,int _type) {m_List.add(new ListContents(_msg,_type));}

    public void set(int c,String s) {m_List.set(c, new ListContents(s,c));}

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }
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
        final Chatting chatView= new Chatting();


        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatting_item, parent, false);
            layout    = (RelativeLayout) convertView.findViewById(R.id.layout);
            text    = (TextView) convertView.findViewById(R.id.text);


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
            text.setBackgroundResource(R.drawable.left_b);
            layout.setGravity(Gravity.LEFT);

        }else if(m_List.get(position).type == 1) {
            text.setBackgroundResource(R.drawable.right_b);
            layout.setGravity(Gravity.RIGHT);
        }



        return convertView;
    }



    private class CustomHolder {
        TextView    m_TextView;
        RelativeLayout layout;
    }
}
