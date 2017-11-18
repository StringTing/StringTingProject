package com.example.leeyun.stringting_android;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.media.CamcorderProfile.get;
import static android.view.Gravity.CENTER;
import static android.view.View.GONE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sg970 on 2017-10-28.
 */

public class ChatCustom extends BaseAdapter{
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
    public ChatCustom() {
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
        Button btn = null;
        LinearLayout modify_layout = null;
        TextView        text    = null;
        TextView        text_l    = null;
        CustomHolder    holder  = null;
        RelativeLayout layout  = null;
        final ChatView chatView= new ChatView();


        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chat_view_item, parent, false);

            modify_layout = (LinearLayout)convertView.findViewById(R.id.modify_layout);
            layout    = (RelativeLayout) convertView.findViewById(R.id.layout);
            text    = (TextView) convertView.findViewById(R.id.text);
            text_l    = (TextView) convertView.findViewById(R.id.text_l);
            btn = (Button)convertView.findViewById(R.id.modify_btn);
            // 메인리스트 - 수정버튼
            //Button OptionButton = (Button) convertView.findViewById(R.id.modify_btn);
            //OptionButton.setOnClickListener((View.OnClickListener)context);
            //OptionButton.setTag(welarmDTOMap.get("welarmNo").toString());


            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView   = text;
            holder.m_TextView_l   = text_l;
            holder.m_Btn = btn;
            holder.m_layout = modify_layout;
            holder.layout = layout;
            convertView.setTag(holder);
        }
        else {
            holder  = (CustomHolder) convertView.getTag();
            text    = holder.m_TextView;
            text_l = holder.m_TextView_l;
            btn = holder.m_Btn;
            modify_layout = holder.m_layout;
            layout  = holder.layout;
        }

        // Text 등록
        text.setText(m_List.get(position).msg);
        text_l.setText(m_List.get(position).msg);


        if( m_List.get(position).type == 0 ) {
            text_l.setBackgroundResource(R.drawable.bubble_left);
            layout.setGravity(Gravity.LEFT);
            modify_layout.setVisibility(GONE);
        }else if(m_List.get(position).type == 1) {
            modify_layout.setBackgroundResource(R.drawable.bubble_right);
            btn.setGravity(CENTER);
            text_l.setVisibility(GONE);
            layout.setGravity(Gravity.RIGHT);
        }



        return convertView;
    }



    private class CustomHolder {
        TextView    m_TextView;
        TextView m_TextView_l;
        Button m_Btn;
        LinearLayout m_layout;
        RelativeLayout layout;
    }
}
