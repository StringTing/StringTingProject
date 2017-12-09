package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.graphics.drawable.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.string.leeyun.stringting_android.API.Ger_last_5day_matched_account;
import com.string.leeyun.stringting_android.API.Get_today_introduction;
import com.string.leeyun.stringting_android.API.Im_get_today_introduction;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_last_5days_matched_accountList;
import com.string.leeyun.stringting_android.API.okhttp_intercepter_token;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.http.HEAD;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.e;


public class Tab_First extends Fragment implements View.OnClickListener {

    private FrameLayout mLayout;

    private Context mContext;
    private Resources mResources;
    private ImageView mImageView , mImageView2 , l1,l2,l3;
    private Bitmap today_Bitmap1,today_Bitmap2 , lb1,lb2,lb3;
      int account_id;
     String token;
     String sex;
    Rest_ApiService apiService;
    Retrofit retrofit;
    float cornerRadius = 25f;
    public String today_image_url_first;
    public String today_image_url_second;
    public String last_image_url_first;
    public String last_image_url_second;
    public String last_image_url_third;
    ArrayList<Get_today_introduction> im_get_today;
    ArrayList<Ger_last_5day_matched_account>im_get_last_5day;


    public Tab_First() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_tab_first, container, false);

        //추가할 부모 뷰
        final LinearLayout inflatedLayout = (LinearLayout) v.findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) v.findViewById(R.id.add_btn);

        //이미지 들어오는거
        final int[] p = {1,2,3};

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbtn.setVisibility(View.GONE);
                // activity_main.xml에서 정의한 LinearLayout 객체 할당

                if(p.length<3) {
                    // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
                    inflater.inflate(R.layout.addimg, inflatedLayout);
                }
                inflater.inflate(R.layout.addimg_2, inflatedLayout);
            }
        });


        //api정의


        try {
            Thread.sleep(2000);
            SharedPreferences pref = this.getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);
            sex=pref.getString("sex","notfound");
            Log.e("sex",sex);
            account_id = pref.getInt("account_id",0);
            Log.e("local_account", String.valueOf(account_id));
            token=pref.getString("token","?");
            Log.e("loacal_token",String.valueOf(token));
            if (account_id==0){
                Log.e("localid is null","fail");

            }
            else if(token.equals(null)){
                Log.e("local_token is null","fail");

            }
            else if(sex.equals(null)){
                Log.e("local_sex is null","fail");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
            Call<Im_get_today_introduction> call = apiService.Get_today_introduction(sex, account_id);

            call.enqueue(new Callback<Im_get_today_introduction>() {

                @Override
                public void onResponse(Call<Im_get_today_introduction> call, retrofit2.Response<Im_get_today_introduction> response) {
                    im_get_today = response.body().getGet_today_introductions();
                    Log.e("today_introduction", String.valueOf(response.raw()));
                    Log.e("today_introduction", String.valueOf(response.body()));
                    Log.e("today_introduction", String.valueOf(response.code()));

                    try {
                        if (im_get_today.get(0).getImages(0) != null) {
                            today_image_url_first = String.valueOf(im_get_today.get(0).getImages(0));
                            Log.e("get_eval_list_image", String.valueOf(im_get_today.get(0).getImages(0)));

                        }
                        today_image_url_second = String.valueOf(im_get_today.get(1).getImages(0));


                        String replace = "{}";
                        String medium = "medium";
                        String small = "small";
                        if (today_image_url_first != null && today_image_url_second != null) {
                            today_image_url_first = today_image_url_first.replace(replace, small);
                            today_image_url_second = today_image_url_second.replace(replace, small);
                            Log.e("image_url_first", today_image_url_first);
                            Log.e("image_url_second", today_image_url_second);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imagenull_today", "false");
                    }

                }

                @Override
                public void onFailure(Call<Im_get_today_introduction> call, Throwable t) {
                    Log.e("today-introduction-fail", t.toString());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e("today-introductuon","null");
        }


        try {
            Call<get_last_5days_matched_accountList> call5day = apiService.Get_last_5day(sex, account_id);
            call5day.enqueue(new Callback<get_last_5days_matched_accountList>() {

                @Override
                public void onResponse(Call<get_last_5days_matched_accountList> call5day, retrofit2.Response<get_last_5days_matched_accountList> response) {
                    Log.e("last-5day", String.valueOf(response.raw()));
                    Log.e("last-5day", String.valueOf(response.body()));
                    Log.e("last-5day", String.valueOf(response.code()));

                    im_get_last_5day = response.body().getGet_last_5day_matched_account();
                    try {
                        last_image_url_first = String.valueOf(im_get_last_5day.get(0).getImages().get(0));
                        last_image_url_second = String.valueOf(im_get_last_5day.get(1).getImages().get(0));
//                    last_image_url_third = String.valueOf(im_get_last_5day.get(2).getImages().get(0));

                        Log.e("get_eval_list_image", String.valueOf(im_get_today.get(0).getImages(0)));
                        String replace = "{}";
                        String medium = "medium";
                        String small = "small";
                        if (last_image_url_first != null && last_image_url_second != null) {
                            last_image_url_first = last_image_url_first.replace(replace, small);
                            last_image_url_second = last_image_url_second.replace(replace, small);
//                        last_image_url_third =last_image_url_third.replace(replace,small);
                            Log.e("image_url_first", last_image_url_first);
                            Log.e("image_url_second", last_image_url_second);
                            image_url(v);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("imageNULL_last5", "false");
                    }

                }

                @Override
                public void onFailure(Call<get_last_5days_matched_accountList> call, Throwable t) {
                    Log.e("last-introduction-fail", t.toString());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            Log.e("last_introduction","null");
        }
        mLayout = (FrameLayout) v.findViewById(R.id.t_pic1);
        mLayout.setOnClickListener(this);



        // Inflate the layout for this fragment
        return  v;
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(),Personal_profile.class);
        startActivity(i);
    }



    public void image_url(View v){


        // Get the application context
        mContext = getApplicationContext();
        // Get the Resources
        mResources = getResources();
        // Get the widgets reference from XML layout
        //mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
        mImageView = (ImageView) v.findViewById(R.id.ph1);
        mImageView2 = (ImageView) v.findViewById(R.id.ph2);
        l1 = (ImageView) v.findViewById(R.id.last1);
        l2 = (ImageView) v.findViewById(R.id.last2);
        l3 = (ImageView) v.findViewById(R.id.last3);

        //mBTN = (Button) v.findViewById(R.id.btn);



        Thread mTread =new Thread() {
            public void run() {
                try
                {

                    URL url_frist = new URL(API_IMAGE_URL + today_image_url_first);
                    URL url_second = new URL(API_IMAGE_URL+today_image_url_second);
                    URL url_last_day_first=new URL(API_IMAGE_URL+last_image_url_first);
                    URL url_last_day_second=new URL(API_IMAGE_URL+last_image_url_second);
//                    URL url_last_day_third =new URL(API_IMAGE_URL+last_image_url_third);


                    Log.e("image_url", String.valueOf(url_frist));
                    URLConnection conn = url_frist.openConnection();
                    URLConnection conn1 = url_second.openConnection();
                    URLConnection conn2 =url_last_day_first.openConnection();
                    URLConnection conn3 =url_last_day_second.openConnection();
//                    URLConnection conn4=url_last_day_third.openConnection();
                    try{
                        conn.connect();
                        conn1.connect();
                        conn2.connect();
                        conn3.connect();
//                    conn4.connect();

                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                        BufferedInputStream bis1= new BufferedInputStream(conn1.getInputStream());
                        BufferedInputStream bis2= new BufferedInputStream(conn2.getInputStream());
                        BufferedInputStream bis3= new BufferedInputStream(conn3.getInputStream());
//                    BufferedInputStream bis4= new BufferedInputStream(conn4.getInputStream());

                        today_Bitmap1 = BitmapFactory.decodeStream(bis);
                        today_Bitmap2= BitmapFactory.decodeStream(bis1);
                        lb1 =  BitmapFactory.decodeStream(bis2);
                        lb2 =  BitmapFactory.decodeStream(bis3);
//                    lb3 =  BitmapFactory.decodeStream(bis4);


                        bis.close();
                        bis1.close();
                        bis2.close();
                        bis3.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


//                    bis4.close();
                } catch (Exception e)

                {
                        e.printStackTrace();
                }
            }
        };

        mTread.start();
        try {
            mTread.join();
            mImageView.setImageBitmap(today_Bitmap1);
            mImageView2.setImageBitmap(today_Bitmap2);
            l1.setImageBitmap(lb1);
            l2.setImageBitmap(lb2);
//            l1.setImageBitmap(lb3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Display the bitmap in ImageView


        // Define the ImageView corners radius

        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap2);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(mResources, lb1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable3 = RoundedBitmapDrawableFactory.create(mResources, lb2);
//        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable4 = RoundedBitmapDrawableFactory.create(mResources, lb3);


        roundedBitmapDrawable.setCornerRadius(cornerRadius);
        roundedBitmapDrawable1.setCornerRadius(cornerRadius);
        roundedBitmapDrawable2.setCornerRadius(cornerRadius);
        roundedBitmapDrawable3.setCornerRadius(cornerRadius);
//        roundedBitmapDrawable4.setCornerRadius(cornerRadius);
        roundedBitmapDrawable.setAntiAlias(true);
        roundedBitmapDrawable1.setAntiAlias(true);
        roundedBitmapDrawable2.setAntiAlias(true);
        roundedBitmapDrawable3.setAntiAlias(true);
//        roundedBitmapDrawable4.setAntiAlias(true);
        // Set the ImageView image as drawable object
        mImageView.setImageDrawable(roundedBitmapDrawable);
        mImageView2.setImageDrawable(roundedBitmapDrawable1);
        l1.setImageDrawable(roundedBitmapDrawable2);
        l2.setImageDrawable(roundedBitmapDrawable3);
//        l3.setImageDrawable(roundedBitmapDrawable4);





//        // Get the bitmap from drawable resources
//        mBitmap = BitmapFactory.decodeResource(mResources, R.drawable.gametitle_01);
//



        //last pic




        android.support.v4.graphics.drawable.RoundedBitmapDrawable last_five_day1 = RoundedBitmapDrawableFactory.create(mResources, lb1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable last_five_day2 = RoundedBitmapDrawableFactory.create(mResources, lb2);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable last_five_day3 = RoundedBitmapDrawableFactory.create(mResources,lb3);


        last_five_day1.setCornerRadius(cornerRadius);
        last_five_day2.setCornerRadius(cornerRadius);
        last_five_day3.setCornerRadius(cornerRadius);

        last_five_day1.setAntiAlias(true);
        last_five_day2.setAntiAlias(true);
        last_five_day3.setAntiAlias(true);


        l1.setImageDrawable(last_five_day1);
        l2.setImageDrawable(last_five_day2);
        l3.setImageDrawable(last_five_day3);

    }

    public void get_local_accoint_id(){

    }



}