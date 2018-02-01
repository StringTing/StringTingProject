package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_introduction_qna;
import com.string.leeyun.stringting_android.API.get_introduction_qnalist;
import com.string.leeyun.stringting_android.API.get_introduction_question;
import com.string.leeyun.stringting_android.API.get_introduction_questionlist;
import com.string.leeyun.stringting_android.API.join;
import com.string.leeyun.stringting_android.API.post_qna;
import com.string.leeyun.stringting_android.API.post_qna_list;
import com.string.leeyun.stringting_android.API.register_message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.google.gson.Gson;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URLTest;
import static com.string.leeyun.stringting_android.R.mipmap.e;
import static java.lang.reflect.Array.getInt;

public class ChatView extends Activity implements AdapterView.OnItemClickListener {

    int i=0;
    ListView m_ListView;
    ChatCustom m_Adapter;
    userinfo Userinfo = new userinfo();
    register_message RegisterMessage =new register_message();
    Rest_ApiService apiService;
    Rest_ApiService apiService1;
    Retrofit retrofit;
    static  int position;
     int account_id;
    String fcm_token;
    String token;
    String sex;
    public int question_id;
    post_qna PostQna=new post_qna();
    ArrayList<String> Imageupload_countList;
    ArrayList<get_introduction_question>GetIntroductionQuestion;
    int question_id_count=0;

    public ArrayList<post_qna>postQnaList=new ArrayList<post_qna>();



    public ArrayList<post_qna> getPostQnaList() {
        return postQnaList;
    }



    post_qna_list postQnaList1=new post_qna_list();
    ArrayList<Integer>question_id_array;

    ArrayList<String>question_contents=new ArrayList<String>();


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);




        Userinfo = (userinfo)getIntent().getSerializableExtra("UserInfo");

        final String Userinfo_Json= new Gson().toJson(Userinfo);
        Log.e("TestUserinfoGson",Userinfo_Json);            //userinfo정보를 json타입으로 변환

        savePreferences(Userinfo.getEmail());
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        String test = pref.getString("ID","fail");
        account_id= pref.getInt("account_id",0);
        token = pref.getString("token","0");
        sex= pref.getString("sex","?");
        Log.v("localdbtest",test);



        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client.build())
                .build();
        apiService= retrofit.create(Rest_ApiService.class);
        Intent intent=getIntent();

        Imageupload_countList=intent.getExtras().getStringArrayList("ProfileFilepath");




        sex=local_sex();
        Log.e("sex",sex);

        setonclick();

        // 커스텀 어댑터 생성
        m_Adapter = new ChatCustom();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        question_id_array=new ArrayList<Integer>();

        String get="get";
        Call<get_introduction_questionlist> PostQnaList = apiService.get_introduction_questionlist(get);
        PostQnaList.enqueue(new Callback<get_introduction_questionlist>() {
            @Override
            public void onResponse(Call<get_introduction_questionlist> call, Response<get_introduction_questionlist> response) {
                GetIntroductionQuestion = response.body().getQuestions();


                Log.e("onresponse_get_question", GetIntroductionQuestion.get(0).getContents());

                Log.e("onresponse_get_ques" +
                        "tion", String.valueOf(GetIntroductionQuestion.get(0).getId()));


                for (int i=0;i<3;i++){
                    question_id_array.add(GetIntroductionQuestion.get(i).getId());
                    question_contents.add(GetIntroductionQuestion.get(i).getContents());
                }


                m_Adapter.add(GetIntroductionQuestion.get(0).getContents(),0);




            }

            @Override
            public void onFailure(Call<get_introduction_questionlist> call, Throwable t) {
                Log.d("sam", t.toString());
            }

        });




        //수정 버튼을 클릭하면 edittext를 받아서 listview에 세팅해줌
        findViewById(R.id.modify_sendbtn).setOnClickListener(new TextView.OnClickListener() {
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


    public void setonclick(){
        final Button send = (Button) findViewById(R.id.send_btn);

        final EditText editText = (EditText) findViewById(R.id.input_text);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().replace(" ", "").equals("")||charSequence.length()<5){
                    send.setBackgroundResource(R.drawable.rounded_non_sendbtn);
                    send.setClickable(false);

                } else {
                    send.setBackgroundResource(R.drawable.rounded_sendbtn);
                    send.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

/*
    private void replacee (String inputValue ,int _str){
        m_Adapter.remove(_str);
        m_Adapter.notifyDataSetChanged();
    }*/


    public void onClick_back(View v) {
        Intent pop = new Intent(this,MyIdeal_pop.class);
        startActivity(pop);
    }

    @Override
    public void onBackPressed() {
        Intent pop = new Intent(this,MyIdeal_pop.class);
        startActivity(pop);
        // super.onBackPressed();
    }

    public void onClick_TabbedBar(View v){
        Intent intent = new Intent(getApplicationContext(), Mediate.class);
        get_local_data();
       intent.putExtra("ProfileFilepath",Imageupload_countList);
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < postQnaList.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("question_id", postQnaList.get(i).getQuestion_id());
                sObject.put("answer", postQnaList.get(i).getAnswer());
                jArray.put(sObject);
            }
            obj.put("qna_list", jArray);//배열을 넣음

            System.out.println(obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("account-id", String.valueOf(account_id));



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
                .baseUrl(API_URLTest)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .build();

        apiService1= retrofit.create(Rest_ApiService.class);




//172.30.1.7:3825
        //test server
        try {
            final Call<post_qna_list> PostQnaList1 = apiService1.post_qna_list(postQnaList1);
            PostQnaList1.enqueue(new Callback<post_qna_list>() {
                @Override
                public void onResponse(Call<post_qna_list> call, Response<post_qna_list> response) {

                    post_qna_list gsonresponse = response.body();

                    Log.e("onresponse_post_qna_list", gsonresponse.getResult());
                    Log.e("onresponse", String.valueOf(response.code()));
                    Log.e("onresponse", "success");


                }

                @Override
                public void onFailure(Call<post_qna_list> call, Throwable t) {
                    Log.d("sam", t.toString());
                }

            });

        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void save_token(String token,int account_id,String sex){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",token);
        editor.putInt("account_id",account_id);
        editor.putString("sex",sex);
        editor.clear();
        editor.commit();
    }

    public void get_local_data(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        Log.e("local_token",String.valueOf(token));
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
        String get_sex=pref.getString("sex","notfound");
        return get_sex;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void save_get_question(String token,int account_id,String sex){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",token);
        editor.putInt("account_id",account_id);
        editor.putString("sex",sex);
        editor.clear();
        editor.commit();
    }


    public void send_question(View v){


        EditText editText = (EditText) findViewById(R.id.input_text);
        String inputValue = editText.getText().toString();


        PostQna.setQuestion_id(question_id_array.get(question_id_count));
        question_id_count+=1;
        Log.e("dd", String.valueOf(question_id_array.get(0)));
        Log.e("dd1",inputValue);
        PostQna.setAnswer(inputValue);
        postQnaList.add(PostQna);
        postQnaList1.getPostQna().add(PostQna);

        if(inputValue.length()>=5){

            editText.setText("");
            m_Adapter.add(inputValue,1) ;
            m_Adapter.notifyDataSetChanged();
            if (i<2) {
                m_Adapter.add(question_contents.get(i + 1), 0);
            }
            i++;
        }
        if(i==3){
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
