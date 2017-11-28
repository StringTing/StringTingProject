package com.example.leeyun.stringting_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.graphics.drawable.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.leeyun.stringting_android.API.Get_evalaccount;
import com.example.leeyun.stringting_android.API.Get_today_introduction;
import com.example.leeyun.stringting_android.API.Rest_ApiService;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Tab_First extends Fragment {

    private RelativeLayout mLayout;

    private Context mContext;
    private Resources mResources;
    private ImageView mImageView , mImageView2 , l1,l2,l3;
    private Bitmap mBitmap , lb1,lb2,lb3;
    public  int account_id;
    Rest_ApiService apiService;
    Retrofit retrofit;
    float cornerRadius = 25f;
    public List<Get_today_introduction>get_today_introductions;

    public Tab_First() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab_first, container, false);


    //api정의
        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);



        try {
            Thread.sleep(3000);
            SharedPreferences local_id = this.getActivity().getSharedPreferences("Local_DB", Context.MODE_PRIVATE);

            account_id = local_id.getInt("account_id",1);
            Log.e("localdbtest_account_id", String.valueOf(account_id));

            if (account_id==0){
                Log.e("localid is null","fail");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



   /*
        Call<List<Get_today_introduction>> call = apiService.Get_today_introduction("male",34);

        call.enqueue(new Callback<List<Get_today_introduction>>() {
            @Override
            public void onResponse(Call<List<Get_today_introduction>> call, Response<List<Get_today_introduction>> response) {

                get_today_introductions=response.body();
                Log.e("get_eval_list_image", String.valueOf(get_today_introductions.get(0).getImages()));

            }

            @Override
            public void onFailure(Call<List<Get_today_introduction>> call, Throwable t) {
                Log.v("onresponseImage2",t.toString());
            }
        });*/


        // Get the application context
        mContext = getApplicationContext();


        // Get the Resources
        mResources = getResources();

        // Get the widgets reference from XML layout
        //mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
        mImageView = (ImageView) v.findViewById(R.id.ph1);
        mImageView2 = (ImageView) v.findViewById(R.id.ph2);
        //mBTN = (Button) v.findViewById(R.id.btn);



        try {
            URL url = new URL("이미지 주소");
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            mBitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            // Display the bitmap in ImageView
            mImageView.setImageBitmap(mBitmap);
            mImageView2.setImageBitmap(mBitmap);


            // Define the ImageView corners radius

            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, mBitmap);


            roundedBitmapDrawable.setCornerRadius(cornerRadius);

            roundedBitmapDrawable.setAntiAlias(true);


            // Set the ImageView image as drawable object
            mImageView.setImageDrawable(roundedBitmapDrawable);
            mImageView2.setImageDrawable(roundedBitmapDrawable);

        } catch (Exception e) {
        }


//        // Get the bitmap from drawable resources
//        mBitmap = BitmapFactory.decodeResource(mResources, R.drawable.gametitle_01);
//





        //last pic
        l1 = (ImageView) v.findViewById(R.id.last1);
        l2 = (ImageView) v.findViewById(R.id.last2);
        l3 = (ImageView) v.findViewById(R.id.last3);

        lb1 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);
        lb2 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);
        lb3 =  BitmapFactory.decodeResource(mResources, R.drawable.kakao_default_profile_image);

        l1.setImageBitmap(lb1);
        l2.setImageBitmap(lb2);
        l3.setImageBitmap(lb3);

        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(mResources, lb1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(mResources, lb2);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable3 = RoundedBitmapDrawableFactory.create(mResources,lb3);


        roundedBitmapDrawable1.setCornerRadius(cornerRadius);
        roundedBitmapDrawable2.setCornerRadius(cornerRadius);
        roundedBitmapDrawable3.setCornerRadius(cornerRadius);

        roundedBitmapDrawable1.setAntiAlias(true);
        roundedBitmapDrawable2.setAntiAlias(true);
        roundedBitmapDrawable3.setAntiAlias(true);


        l1.setImageDrawable(roundedBitmapDrawable1);
        l2.setImageDrawable(roundedBitmapDrawable2);
        l3.setImageDrawable(roundedBitmapDrawable3);

       /* //블러 라이브러리
        ImageView bluri = (ImageView) v.findViewById(R.id.pic1_background);
       Glide.with(mContext).load(R.drawable.gametitle_01).into(blur);
 //.bitmapTransform(new BlurTransformation(mContext)*/

        //local_db에서 account_id가져옴


        // Inflate the layout for this fragment
        return  v;
    }



    public void get_local_accoint_id(){

    }
}