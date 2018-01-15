package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.Getdetail;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_introduction_qnalist;
import com.string.leeyun.stringting_android.API.message;
import com.string.leeyun.stringting_android.API.userinfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
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

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;

public class PersonalProfile_Edit extends AppCompatActivity {

    //사진 및 정보
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto1, iv_UserPhoto2, iv_UserPhoto3, iv_UserPhoto4, iv_UserPhoto5, iv_UserPhoto6;
    public int imageupload_count=0;
    public ArrayList<String> Imageupload_countList=new ArrayList<>();
    userinfo UserInfo = new userinfo();
    ResponseApi responapi =new ResponseApi();
    Rest_ApiService apiService;
    Retrofit retrofit;
    String token;
    int account_id;
    String sex;
    Getdetail getdetail;
    ImageView personfile_image;
    ArrayList<String>profile_image_list;
    ArrayList<String>profile_image_replace;
    ArrayList<String>profile_image_full_url=new ArrayList<String>();
    int macthing_account;
    String macthing_sex;
    ArrayList<Integer>detail_photo;
    get_introduction_qnalist QnaList;
    ArrayList<String> question_array;
    ArrayList<String>answer_array;
    message Message = new message();

    File Postfile;




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }




    //대화창
    ListView m_ListView;
    PersonalChatCustom m_Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile__edit);


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
            Call<Getdetail> call = apiService.get_detail(sex, account_id);

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

        Call<get_introduction_qnalist> PostQnaList = apiService.get_introduction_qnalist(sex,account_id);
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
}
