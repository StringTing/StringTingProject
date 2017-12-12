package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.squareup.picasso.Picasso;
import com.string.leeyun.stringting_android.API.Getdetail;
import com.string.leeyun.stringting_android.API.Im_get_today_introduction;
import com.string.leeyun.stringting_android.API.Rest_ApiService;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.id.imageView4;
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
    ArrayList<String>profile_image_full_url=new ArrayList<String>();
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);


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
                    try {
                        detail_photo=new ArrayList<Integer>();

                        if (getdetail.getImages().get(0) != null) {
                            for (int i=0;i<getdetail.getImages().size();i++){
                                profile_image_list.add(String.valueOf(getdetail.getImages(i)));
                            }

                            Log.e("get_eval_list_image", String.valueOf(getdetail.getImages(0)));

                        }

                        String replace = "{}";
                        String medium = "medium";
                        String large="large";
                        String small = "small";
                        if (getdetail.getImages(0) != null && getdetail.getImages(1) != null) {
                            for (int i=0;i<getdetail.getImages().size();i++){
                                profile_image_list.get(i).replace(replace,large);
                            }

                            Log.e("image_url_first", profile_image_list.get(0));
                        }

                        image_url_add(profile_image_list);

                        LinearLayout inflatedLayout = (LinearLayout)findViewById(R.id.pic_view);
                        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                        for(int x=0;x<profile_image_full_url.size();x++) {

                            ImageView image = new ImageView(Personal_profile.this);
                            final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 184, getResources().getDisplayMetrics());

                            final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 261, getResources().getDisplayMetrics());

                            LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(width,height);

                            paramlinear.setMargins(0,0,30,0);

                            image.setLayoutParams(paramlinear);

                            Picasso.with(Personal_profile.this)
                                    .load(profile_image_full_url.get(x))
                                    .into(image);

                            inflatedLayout.addView(image, paramlinear);

                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imagenull_today", "false");
                    }

                }

                @Override
                public void onFailure(Call<Getdetail> call, Throwable t) {
                    Log.e("today-introduction-fail", t.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("get_detail_profile","null");
        }



        // 커스텀 어댑터 생성
        m_Adapter = new PersonalChatCustom();


        // Xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);



        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);


        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 1);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        m_Adapter.add("안녕하세요! \n" +
                "회원정보를 입력하시느라 고생많으셨어요~\n" +
                "이제 마지막 단계인데요!\n" +
                "제가 하는 질문을 이상형인 사람이 질문한다고생각해주시고 정성스럽게 답장해주세요!", 0);

        setListViewHeightBasedOnItems(m_ListView);

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
        for (int i=0;i<profile_image_list.size();i++){
            profile_image_full_url.add(API_IMAGE_URL+profile_image_list.get(i));

        }
    }
}
