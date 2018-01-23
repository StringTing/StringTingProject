package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.Getdetail;
import com.string.leeyun.stringting_android.API.Im_get_today_introduction;
import com.string.leeyun.stringting_android.API.Rest_ApiService;

import com.string.leeyun.stringting_android.API.qna_list;
import com.tsengvn.typekit.TypekitContextWrapper;

import com.string.leeyun.stringting_android.API.get_introduction_qna;
import com.string.leeyun.stringting_android.API.get_introduction_qnalist;
import com.string.leeyun.stringting_android.API.get_introduction_questionlist;


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

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.t;
import static java.lang.System.load;


public class Personal_profile extends AppCompatActivity {
    ListView m_ListView;
    PersonalChatCustom m_Adapter;
    ArrayList<Integer>detail_photo;
    int account_id;
    String token;
    String sex;
    Retrofit retrofit;
    Rest_ApiService apiService;
    Getdetail getdetail;
    ImageView personfile_image;
    ArrayList<String>profile_image_list;
    ArrayList<String>profile_image_replace;
    ArrayList<String>profile_image_full_url=new ArrayList<String>();

    int macthing_account;
    String macthing_sex;

    get_introduction_qnalist QnaList;
    ArrayList<String>question_array;
    ArrayList<String>answer_array;
    ArrayList<get_introduction_qna> GetIntroDuction;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    public void chat_request(View v){
        Intent chat_r = new Intent(this,Chat_pop.class);
        chat_r.putExtra("account_id",account_id);
        chat_r.putExtra("sex",sex);
        chat_r.putExtra("token",token);
        startActivity(chat_r);
    }

    public void report_request(View v){
        Intent r= new Intent(this,Report_pop.class);
        r.putExtra("account_id",account_id);
        r.putExtra("sex",sex);
        r.putExtra("token",token);
        startActivity(r);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void Back(View v){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        Intent intent=getIntent();
        macthing_account= (int) intent.getSerializableExtra("matching_account");
        macthing_sex= (String) intent.getSerializableExtra("matching_sex");


        get_local_data();


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

        try {
            Call<Getdetail> call = apiService.get_detail(macthing_sex, macthing_account);

            call.enqueue(new Callback<Getdetail>() {

                @Override
                public void onResponse(Call<Getdetail> call, retrofit2.Response<Getdetail> response) {
                    Log.e("today_introduction", String.valueOf(response.raw()));
                    Log.e("today_introduction", String.valueOf(response.body()));
                    Log.e("today_introduction", String.valueOf(response.code()));

                    getdetail=new Getdetail();
                    getdetail=response.body();
                    profile_image_list=new ArrayList<String>();
                    profile_image_replace=new ArrayList<String>();
                    try {
                        detail_photo=new ArrayList<Integer>();

                        if (getdetail.getImages().getApproved().get(0).getName() != null) {
                            for (int i=0;i<getdetail.getImages().getApproved().size();i++){
                                profile_image_list.add(String.valueOf(getdetail.getImages().getApproved().get(i).getName()));
                            }

                            Log.e("get_eval_list_image", String.valueOf(getdetail.getImages().getApproved().get(0).getName()));

                        }

                        String replace = "{}";
                        String medium = "medium";
                        String large="large";
                        String small = "small";
                        if (getdetail.getImages().getApproved().get(0) != null) {
                            for (int i=0;i<getdetail.getImages().getApproved().size();i++){
                               profile_image_replace.add(profile_image_list.get(i).replace(replace,large));
                            }

                            Log.e("image_url_first", profile_image_replace.get(0));
                        }

                        image_url_add(profile_image_list);

                        LinearLayout inflatedLayout = (LinearLayout)findViewById(R.id.pic_view);
                        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        //imageview 동적으로 생성하는 부분 라운드처리해줘야함

                        for(int x=0;x<profile_image_full_url.size();x++) {

                            ImageView image = new ImageView(Personal_profile.this);
                            final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 184, getResources().getDisplayMetrics());

                            final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 261, getResources().getDisplayMetrics());

                            LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(width,height);

                            paramlinear.setMargins(0,0,50,0);

                            image.setLayoutParams(paramlinear);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();

                            Picasso.with(Personal_profile.this)
                                    .load(profile_image_full_url.get(x))
                                    .fit()
                                    .transform(transformation)
                                    .into(image);

                            inflatedLayout.addView(image, paramlinear);

                        }

                        ImageView profile=(ImageView)findViewById(R.id.detail_profile);

                        Picasso.with(Personal_profile.this).load(profile_image_full_url.get(0)).transform(new CircleTransForm()).into(profile);

                       /*
                        내 프로필 서클

                       ImageView my_profile=(ImageView)findViewById(R.id.my_profile);

                       Picasso.with(Personal_profile.this).load(?????????).transform(new CircleTransForm()).into(my_profile);*/




                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imagenull_today", "false");
                    }

                }

                @Override
                public void onFailure(Call<Getdetail> call, Throwable t) {
                    Log.e("getdatail-fail", t.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("get_detail_profile","null");
        }


        QnaList=new get_introduction_qnalist();

        Call<get_introduction_qnalist> PostQnaList = apiService.get_introduction_qnalist(macthing_sex,macthing_account);
        PostQnaList.enqueue(new Callback<get_introduction_qnalist>() {
            @Override
            public void onResponse(Call<get_introduction_qnalist> call, Response<get_introduction_qnalist> response) {

                QnaList=response.body();

                question_array=new ArrayList<String>();
                answer_array=new ArrayList<String>();


                Log.e("onresponse_get_question", String.valueOf(QnaList.getQna_list().getApproved().get(0).getAnswer()));



                for (int i=0;i<3;i++){
                    question_array.add(QnaList.getQna_list().getApproved().get(i).getQuestion());
                    answer_array.add(QnaList.getQna_list().getApproved().get(i).getAnswer());
                }



                // 커스텀 어댑터 생성
                m_Adapter = new PersonalChatCustom();


                // Xml에서 추가한 ListView 연결
                m_ListView = (ListView) findViewById(R.id.listView1);



                // ListView에 어댑터 연결
                m_ListView.setAdapter(m_Adapter);


                m_Adapter.add(question_array.get(0), 1);

                m_Adapter.add(answer_array.get(0), 0);

                m_Adapter.add(question_array.get(1), 1);

                m_Adapter.add(answer_array.get(1), 0);

                m_Adapter.add(question_array.get(2), 1);

                m_Adapter.add(answer_array.get(2), 0);
                setListViewHeightBasedOnItems(m_ListView);

            }

            @Override
            public void onFailure(Call<get_introduction_qnalist> call, Throwable t) {
                Log.d("sam", t.toString());
            }

        });





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

    public void get_local_data(){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        account_id = pref.getInt("account_id",0);
        Log.e("local_account", Integer.toString(account_id));
        token=pref.getString("token","?");
        sex=pref.getString("sex","0");
        Log.e("local_token",String.valueOf(token));
    }

    public void image_url_add(ArrayList<String> profile_image_list){
        for (int i=0;i<profile_image_replace.size();i++){
            profile_image_full_url.add(API_IMAGE_URL+profile_image_replace.get(i));
            Log.e("profile_image_full_url",profile_image_full_url.get(0));
        }
    }
}
