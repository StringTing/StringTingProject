package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_matched_account;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.register_message;
import com.string.leeyun.stringting_android.API.userinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;


public class Tab_Fourth extends Fragment {

    private ListView lv;
    private Tab_Fourth_Custom mAdapter;
    private ArrayList<listItem> list;
    public List<get_matched_account> get_matched_accountList;
    Rest_ApiService apiService;
    Retrofit retrofit;

    public Tab_Fourth() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_tab_fourth,container, false);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //리스트아이템 클래스 : 리스트뷰의 아이템에 본인이 넣고자 하는 데이터들의 묶음
        list = new ArrayList<listItem>();
        lv = (ListView)getView().findViewById(R.id.list);
        mAdapter = new Tab_Fourth_Custom(list);
        lv.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);
  /*      ImageView pro= null;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kakao_default_profile_image);
        pro.setBackgroundResource(R.drawable.circlr_border);
        pro.setImageBitmap(bitmap);

*/
        //데이터 넣는거
       // list.add(new listItem(iv,"이름","내용","시간"));
        list.add(new listItem(R.drawable.kakao_default_profile_image,"이름","내용","시간"));
        list.add(new listItem(R.drawable.kakao_default_profile_image,"이름","내용","시간"));
        list.add(new listItem(R.drawable.kakao_default_profile_image,"이름","내용","시간"));
        list.add(new listItem(R.drawable.kakao_default_profile_image,"이름","내용","시간"));

        Call<List<get_matched_account>>get_matched_account = apiService.get_matched_account("male",34);
        get_matched_account.enqueue(new Callback<List<get_matched_account>>() {
            @Override
            public void onResponse(Call<List<get_matched_account>> call, Response<List<get_matched_account>> response) {

                get_matched_accountList=response.body();
                String date= String.valueOf(get_matched_accountList.get(0).getCreatetime());
                Log.e("createtime", String.valueOf(date));
                Log.e("onresponse", String.valueOf(response.code()));
                Log.e("onresponse", "success");


            }

            @Override
            public void onFailure(Call<List<get_matched_account>> call, Throwable t) {
                Log.d("connectfail", t.toString());
            }

        });
    }



    //리스트 아이템 클래스
    public class listItem{
        private int profile; //이미지리소스 아이디값 받아오는 변수
        private String name;
        private String chat;
        private String date;


        public listItem(int p,String n, String c,String d){
            this.profile=p;
            this.name = n;
            this.chat = c;
            this.date = d;
        }
    }



    //Adapter
    public class Tab_Fourth_Custom extends BaseAdapter {

        private ArrayList<listItem> list;

        public Tab_Fourth_Custom(ArrayList<listItem> list){
            this.list = list;
        }



        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public listItem getItem(int position) {
            //현재 position에 따른 list값 반환
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            final Context context = parent.getContext();
            final int pos = position;
            View v= convertView;


            if(v==null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.tab_fourth_message,parent,false);

                ImageView profile = (ImageView)v.findViewById(R.id.profile_image);
                TextView name = (TextView)v.findViewById(R.id.keyword);
                TextView chat = (TextView)v.findViewById(R.id.text);
                TextView date = (TextView)v.findViewById(R.id.date);



                profile.setImageResource(getItem(pos).profile);
                name.setText(getItem(pos).name);
                chat.setText(getItem(pos).chat);
                date.setText(getItem(pos).date);
            }

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 채팅방 불러오기
                    Intent i = new Intent(getActivity(),Chatting.class);
                    startActivity(i);
                }
            });

            return v;
        }


    }
}