package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.e;
import static java.lang.reflect.Array.getInt;

public class ChatView extends Activity implements AdapterView.OnItemClickListener {
    int question_id1;
    int question_id2;
    int question_id3;

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
    public int question_id;
    post_qna PostQna;
    ArrayList<String> Imageupload_countList;
    ArrayList<get_introduction_question>GetIntroductionQuestion;

    post_qna_list postQnaList;


    String contents1;
    String contents2;
    String contents3;


    ArrayList<Integer>question_id_array=new ArrayList<Integer>();

    ArrayList<String>question_contents=new ArrayList<String>();



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
        int account_id= pref.getInt("account_id",0);
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



        // 커스텀 어댑터 생성
        m_Adapter = new ChatCustom();

        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);







        Thread mTread_add_question =new Thread() {
            public void run() {
                try
                {

                    try{

                        get_introduction_excute asyncTask = new get_introduction_excute();
                        asyncTask.execute();
                        asyncTask.getStatus();

                        if (asyncTask.getStatus() == android.os.AsyncTask.Status.PENDING) {
                            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                            question_id_array.add(pref.getInt("question1",0));
                            Log.e("ss", String.valueOf(question_id_array.get(0)));
                            asyncTask.execute();
                        } else if (asyncTask.getStatus() == android.os.AsyncTask.Status.FINISHED) {
                            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                            question_id_array.add(pref.getInt("question1",0));
                            Log.e("ss", String.valueOf(question_id_array.get(0)));
                            set_question_excute asyncTask_set=new set_question_excute();
                            asyncTask_set.execute();
                        }else{
                            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                            question_id_array.add(pref.getInt("question1",0));
                            Log.e("ss", String.valueOf(question_id_array.get(0)));
                            set_question_excute asyncTask_set=new set_question_excute();
                            asyncTask_set.execute();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        };

        mTread_add_question.start();
        try {
            mTread_add_question.join();



        } catch (Exception e) {
            e.printStackTrace();
        }




        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener() {
            int i=0;
            @Override
            public void onClick(View v) {


                    EditText editText = (EditText) findViewById(R.id.input_text);
                    String inputValue = editText.getText().toString();
                    PostQna.setQuestion_id(question_id_array.get(i));
                    PostQna.setAnswer(inputValue);
                    postQnaList.getPostQna().add(PostQna);


                if(inputValue.length()>=5){

                        editText.setText("");
                        refresh(inputValue, 1);
                     m_Adapter.add(question_contents.get(i+1), 0);

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
        Intent intent = new Intent(getApplicationContext(), Mediate.class);

       intent.putExtra("ProfileFilepath",Imageupload_countList);
        try {
            Call<post_qna_list> PostQnaList1 = apiService.post_qna_list(postQnaList);
            PostQnaList1.enqueue(new Callback<post_qna_list>() {
                @Override
                public void onResponse(Call<post_qna_list> call, Response<post_qna_list> response) {

                    post_qna_list gsonresponse = response.body();
                    Log.e("onresponse_join", gsonresponse.getResult());
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


    class get_introduction_excute extends AsyncTask<String, Integer, Long> {
     @Override
     protected void onCancelled()
    {
         super.onCancelled();
      }
        @Override
        protected void onPostExecute(Long result) {
             super.onPostExecute(result);





        }
             @Override
         protected void onPreExecute() {

             super.onPreExecute();
            }
            @Override
            protected void onProgressUpdate(Integer... values) {


            }
         @Override protected Long doInBackground(String... params) {
             String get="get";


             Call<get_introduction_questionlist> PostQnaList = apiService.get_introduction_questionlist(get);
             PostQnaList.enqueue(new Callback<get_introduction_questionlist>() {
                 @Override
                 public void onResponse(Call<get_introduction_questionlist> call, Response<get_introduction_questionlist> response) {
                     GetIntroductionQuestion = response.body().getQuestions();


                     question_id1=GetIntroductionQuestion.get(0).getId();
                     question_id2=GetIntroductionQuestion.get(1).getId();
                     question_id3=GetIntroductionQuestion.get(2).getId();
                     contents1=GetIntroductionQuestion.get(0).getContents();
                     contents2=GetIntroductionQuestion.get(1).getContents();
                     contents3=GetIntroductionQuestion.get(2).getContents();

                     Log.e("onresponse_get_question", GetIntroductionQuestion.get(0).getContents());

                     Log.e("onresponse_get_ques" +
                             "tion", String.valueOf(GetIntroductionQuestion.get(0).getId()));


                     SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                     SharedPreferences.Editor editor = pref.edit();
                     editor.putInt("question1",question_id1);
                     editor.putInt("question2",question_id2);
                     editor.putInt("question3",question_id3);
                     editor.putString("contents1",contents1);
                     editor.putString("contents2",contents2);
                     editor.putString("contents3",contents3);
                     editor.putString("sex",sex);
                     editor.clear();
                     editor.commit();

                     m_Adapter.add(GetIntroductionQuestion.get(0).getContents(),0);



                 }

                 @Override
                 public void onFailure(Call<get_introduction_questionlist> call, Throwable t) {
                     Log.d("sam", t.toString());
                 }

             });

             return null ;
         }
    }


    class set_question_excute extends AsyncTask<String, Integer, Long> {
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }
        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {


        }
        @Override protected Long doInBackground(String... params) {
            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
            question_id_array.add(pref.getInt("question1",0));
            question_id_array.add(question_id2=pref.getInt("question2",0));
            question_id_array.add(question_id3=pref.getInt("question3",0));
            question_contents.add(contents1=pref.getString("contents1","0"));
            question_contents.add(contents2=pref.getString("contents2","0"));
            question_contents.add(contents3=pref.getString("contents3","0"));
            Log.e("question_id1", String.valueOf(question_id1));
            return null ;
        }
    }


}
