package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.squareup.picasso.Picasso;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_matched_account;
import com.string.leeyun.stringting_android.API.get_matched_accountList;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;


public class Tab_Fourth extends Fragment {

    private ListView lv;
    private Tab_Fourth_Custom mAdapter;
    private ArrayList<listItem> list;
    Rest_ApiService apiService;
    Retrofit retrofit;
    ArrayList<get_matched_account> get_matched_accounts;
    String token;
    int account_id;
    String sex;


    ArrayList<String>contents;
    ArrayList<String>date;
    ArrayList<String>chat_url_list;
    ArrayList<Integer>group_id;
    ArrayList<String>chat_url_list_replace;
    ArrayList<String>chat_url_list_full;
    ArrayList<String>info;
    ArrayList<Integer> unread_message;
    public Tab_Fourth() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_local_data();
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





        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        client1.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request builder = chain.request();
                Request newRequest;


                newRequest = builder.newBuilder()
                        .addHeader("access-token",token)
                        .addHeader("account-id", String.valueOf(account_id))
                        .addHeader("account-sex",sex)
                        .addHeader("Content-Type","application/json")
                        .build();


                return chain.proceed(newRequest);

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();


        apiService= retrofit.create(Rest_ApiService.class);


        //데이터 넣는거
       // list.add(new listItem(iv,"이름","내용","시간"));


        contents=new ArrayList<>();
        date=new ArrayList<>();
        chat_url_list=new ArrayList<>();
        chat_url_list_replace=new ArrayList<>();
        group_id=new ArrayList<>();
        chat_url_list_full=new ArrayList<>();
        info =new ArrayList<>();
        unread_message=new ArrayList<>();
        Call<get_matched_accountList>get_matched_account = apiService.get_matched_account(sex,account_id);
        get_matched_account.enqueue(new Callback<get_matched_accountList>() {

            @Override
            public void onResponse(Call<get_matched_accountList> call, Response<get_matched_accountList> response) {
                if (response.body()!=null) {
                    Log.e("get_matched_account","응답성공");
                    try {
                        String shap=" #";
                        get_matched_accounts=response.body().getAccounts();
                        for (int i = 0; i  < get_matched_accounts.size(); i++) {
                            get_matched_accounts.get(1).getAccount();
                            if(get_matched_accounts.get(i).getLast_messages().getContents()!=null){
                                contents.add(get_matched_accounts.get(i).getLast_messages().getContents());
                                chat_url_list.add(get_matched_accounts.get(i).getAccount().getImages().getApproved().get(0).getName());
                                Log.e("image_log",get_matched_accounts.get(i).getAccount().getImages().getApproved().get(0).getName());
                                group_id.add(get_matched_accounts.get(i).getGroup_id());
                                info.add(shap+get_matched_accounts.get(i).getAccount().getBirthday()+shap+get_matched_accounts.get(i).getAccount().getBody_form()+shap+get_matched_accounts.get(i).getAccount().getEducation()+shap+get_matched_accounts.get(i).getAccount().getHeight());
                                unread_message.add(get_matched_accounts.get(i).getUnread_messages());
                            }
                            Log.e("contentsArray",get_matched_accounts.get(1).getLast_messages().getContents());

                        }

                        Log.e("get_matched_accountList", String.valueOf(response.body()));
                        Log.e("onresponse", String.valueOf(response.code()));
                        String replace = "{}";
                        String medium = "medium";
                        String small = "small";
                        for (int i=0;i<contents.size();i++){
                            if(chat_url_list.get(i)!=null){
                                chat_url_list_replace.add(chat_url_list.get(i).replace(replace,small));
                                chat_url_list_full.add(API_IMAGE_URL+chat_url_list_replace.get(i));
                                list.add(new listItem(chat_url_list_full.get(i),info.get(i),contents.get(i),"111", group_id.get(i),unread_message.get(i))) ;
                                Log.e("i",String.valueOf(i));

                            }
                            Log.e("contents",contents.get(i));


                        }


                        lv.setAdapter(mAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("get_matehced_account","응답은 성공했지만 값이안들어옴");
                    }

                }
            }

            @Override
            public void onFailure(Call<get_matched_accountList> call, Throwable t) {
                Log.d("matched_account_fail", t.toString());
            }

        });
    }



    //리스트 아이템 클래스
    public class listItem{
        private String profile; //이미지리소스 아이디값 받아오는 변수
        private String info;
        private String chat;
        private String date;
        private int group_id;
        private int unread_message;


        public listItem(String p,String info, String c,String d,int e,int unread){
            this.profile=p;
            this.info=info;
            this.chat = c;
            this.date = d;
            this.group_id=e;
            this.unread_message=unread;
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
            final int group_id = 0;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.tab_fourth_message,parent,false);
            final int group_id_set = getItem(pos).group_id;




                ImageView profile = (ImageView)v.findViewById(R.id.profile_image);
                TextView chat = (TextView)v.findViewById(R.id.text);
                TextView date = (TextView)v.findViewById(R.id.date);
                TextView info = (TextView)v.findViewById(R.id.keyword);
                TextView unread= (TextView)v.findViewById(R.id.pop);

                //라운드처리 일단 해줘봄...
                Picasso.with(getActivity()).load(getItem(pos).profile).transform(new CircleTransForm()).into(profile);
                //profile.setImageResource(getItem(pos).profile);
                chat.setText(getItem(pos).chat);
                date.setText(getItem(pos).date);
                info.setText(getItem(pos).info);
                unread.setText(Integer.toString(getItem(pos).unread_message));


            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 채팅방 불러오기
                    Intent i = new Intent(getContext(),Chatting.class);
                    //여기다 그룹아이디 넣어주기 그럼 채팅방이랑연결
                    i.putExtra("group_id",group_id_set);
                    startActivity(i);
                }
            });

            return v;
        }


    }

    public void get_local_data(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }
}
