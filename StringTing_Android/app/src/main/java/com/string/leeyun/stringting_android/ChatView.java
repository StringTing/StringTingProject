package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
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

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_matched_account;
import com.string.leeyun.stringting_android.API.join;
import com.string.leeyun.stringting_android.API.okhttp_intercepter_token;
import com.string.leeyun.stringting_android.API.register_image;
import com.string.leeyun.stringting_android.API.register_message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.id.ll;

public class ChatView extends Activity implements AdapterView.OnItemClickListener {
    ListView m_ListView;
    ChatCustom m_Adapter;
    userinfo Userinfo = new userinfo();
    register_message RegisterMessage =new register_message();
    Rest_ApiService apiService;
    Retrofit retrofit;
    static  int position;
    public int account_id;
    public  String fcm_token;
    public int account_id_localdb;
    String token_localdb;
    String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);


        Userinfo = (userinfo)getIntent().getSerializableExtra("UserInfo");

        final String Userinfo_Json= new Gson().toJson(Userinfo);
        Log.e("TestUserinfoGson",Userinfo_Json);            //userinfo정보를 json타입으로 변환

        savePreferences(Userinfo.getEmail());
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        String test = pref.getString("ID","success");
        Log.v("localdbtest",test);



        OkHttpClient.Builder client = new OkHttpClient.Builder();
        okhttp_intercepter_token Okhttp_intercepter =new okhttp_intercepter_token();
        Okhttp_intercepter.setAccount_id(account_id);
        client.addInterceptor(new okhttp_intercepter_token());
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        apiService= retrofit.create(Rest_ApiService.class);
        Intent intent=getIntent();

        //local에 있는 fcm token 가져옴


        //image resized ,small,middle,large
        ArrayList<String> Imageupload_countList=new ArrayList<>();
        ArrayList<String>Imageresized_small=new ArrayList<>();
        ArrayList<String>Imageresized_middle=new ArrayList<>();
        ArrayList<String>Imageresized_large=new ArrayList<>();
        Imageupload_countList=intent.getExtras().getStringArrayList("ProfileFilepath");
        ArrayList<String>Imageprofile1=new ArrayList<>();
        ArrayList<String>Imageprofile2=new ArrayList<>();
        ArrayList<String>Imageprofile3=new ArrayList<>();

       for (int i=0;i<Imageupload_countList.size();i++){
           Imageresized_small.add(image_resize.bitmap_resized_small(Imageupload_countList.get(i)));
            Imageresized_middle.add(image_resize.bitmap_resized_middle(Imageupload_countList.get(i)));
           Imageresized_large.add(image_resize.bitmap_resized_large(Imageupload_countList.get(i)));

           Log.e("image-small", Imageresized_small.get(i));
           Log.e("image-middle", Imageresized_middle.get(i));
           Log.e("image-large", Imageresized_middle.get(i));
       }

       if(Imageresized_small.get(0)!=null){
           Imageprofile1.add(Imageresized_small.get(0));
           Imageprofile1.add(Imageresized_middle.get(0));
           Imageprofile1.add(Imageresized_large.get(0));
       }
       else if(Imageresized_small.get(1)!=null){

           Imageprofile2.add(Imageresized_small.get(1));
           Imageprofile2.add(Imageresized_middle.get(1));
           Imageprofile2.add(Imageresized_large.get(1));
        }
        else if(Imageresized_small.get(2)!=null){
           Imageprofile3.add(Imageresized_large.get(2));
           Imageprofile3.add(Imageresized_middle.get(2));
           Imageprofile3.add(Imageresized_large.get(2));
       }


        ArrayList<String>keyvalue=new ArrayList<>();
        keyvalue.add("-small");
        keyvalue.add("-medium");
        keyvalue.add("-large");

        MultipartBody.Part[] images1 = new MultipartBody.Part[Imageprofile1.size()];

        for (int index = 0; index < Imageprofile1.size(); index++) {
            Log.e("Imageprofile1",Imageprofile1.get(index));
            File file = new File(Imageprofile1.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
            images1[index] = MultipartBody.Part.createFormData("image"+keyvalue.get(index), file.getName(), surveyBody);
        }



        sex=local_sex();
        Log.e("sex",sex);


        Thread mTread =new Thread() {
            public void run() {
                try

                {
                    if (sex.equals("male")){
                        Call<join> getPostUserinfo = apiService.getPostUserinfo(Userinfo);
                        getPostUserinfo.enqueue(new Callback<join>() {
                            @Override
                            public void onResponse(Call<join> call, Response<join> response) {

                                join gsonresponse=response.body();
                                Log.e("onresponse_join", gsonresponse.getResult());
                                Log.e("onresponse", String.valueOf(response.code()));
                                Log.e("onresponse", "success");
                                account_id=gsonresponse.getAccount_id();
                                Log.e("account_id", String.valueOf(account_id));
                                Log.e("fcm_token",String.valueOf(Userinfo.getFcm_token()));

                                if (gsonresponse.getToken()!=null){
                                    save_token(gsonresponse.getToken());
                                }
                                save_accountid(gsonresponse.getAccount_id());
                                save_token(gsonresponse.getToken());
                                SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                                account_id_localdb = pref.getInt("account_id",0);
                                Log.e("local_account", Integer.toString(account_id_localdb));
                                token_localdb=pref.getString("token","?");
                                Log.e("local_token",String.valueOf(token_localdb));



                            }

                            @Override
                            public void onFailure(Call<join> call, Throwable t) {
                                Log.d("sam", t.toString());
                            }

                        });

                    }
                    else{
                        Call<join> getPostUserinfo = apiService.getPostUserinfoF(Userinfo);
                        getPostUserinfo.enqueue(new Callback<join>() {
                            @Override
                            public void onResponse(Call<join> call, Response<join> response) {

                                join gsonresponse=response.body();
                                Log.e("onresponse_join", gsonresponse.getResult());
                                Log.e("onresponse", String.valueOf(response.code()));
                                Log.e("onresponse", "success");
                                account_id=gsonresponse.getAccount_id();
                                Log.e("account_id", String.valueOf(account_id));
                                Log.e("fcm_token",String.valueOf(Userinfo.getFcm_token()));

                                if (gsonresponse.getToken()!=null){
                                    save_token(gsonresponse.getToken());
                                }
                                if (gsonresponse.getAccount_id()!=0){
                                    save_accountid(gsonresponse.getAccount_id());
                                }
                                save_accountid(gsonresponse.getAccount_id());
                                save_token(gsonresponse.getToken());
                                SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                                account_id_localdb = pref.getInt("account_id",0);
                                Log.e("local_account", String.valueOf(account_id_localdb));
                                token_localdb=pref.getString("token","?");
                                Log.e("loacal_token",String.valueOf(token_localdb));

                            }

                            @Override
                            public void onFailure(Call<join> call, Throwable t) {
                                Log.d("sam", t.toString());
                            }

                        });
                    }


                } catch (Exception e)

                {

                }
            }
        };



        mTread.start();
        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
        okhttp_intercepter_token Okhttp_intercepter1 =new okhttp_intercepter_token();
        Okhttp_intercepter1.setAccount_id(account_id_localdb);
        Okhttp_intercepter1.setToken(token_localdb);
        client.addInterceptor(new okhttp_intercepter_token());
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();
        apiService= retrofit.create(Rest_ApiService.class);
        try {

            mTread.join();

            Call<register_image> call = apiService.post_register_image(sex,account_id,images1);
            call.enqueue(new Callback<register_image>() {
                @Override
                public void onResponse(Call<register_image> call, Response<register_image> response) {
                    register_image imageresponse=response.body();
                    Log.e("onregistImage",imageresponse.getResult());


                }

                @Override
                public void onFailure(Call<register_image> call, Throwable t) {
                    Log.e("onregistImage_fail",t.toString());
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }






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

    public void onClick_TabbedBar(View v){
        Intent intent = new Intent(getApplicationContext(), TabbedBar.class);






        startActivity(intent);
    }

    // local 값 저장하기
        public void savePreferences(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ID",data);
        editor.commit();
    }

        public void save_accountid(int data){
            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("account_id",data);
            editor.clear();
            editor.commit();
        }

    public void save_token(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",data);
        editor.clear();
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


    public String local_sex(){
        SharedPreferences pref=getSharedPreferences("Local_DB", MODE_PRIVATE);
        String get_sex=pref.getString("sex","male");
        return get_sex;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
