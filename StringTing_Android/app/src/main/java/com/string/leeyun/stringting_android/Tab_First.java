package com.string.leeyun.stringting_android;

import android.content.Context;
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

import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.string.leeyun.stringting_android.API.Get_today_introduction;
import com.string.leeyun.stringting_android.API.Rest_ApiService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.t;


public class Tab_First extends Fragment {

    private RelativeLayout mLayout;

    private Context mContext;
    private Resources mResources;
    private ImageView mImageView , mImageView2 , l1,l2,l3;
    private Bitmap today_Bitmap1,today_Bitmap2 , lb1,lb2,lb3;
    public  int account_id;
    Rest_ApiService apiService;
    Retrofit retrofit;
    float cornerRadius = 25f;
    public List<Get_today_introduction>get_today_introductions;
    public String image_url_first;
    public String image_url_second;

    public Tab_First() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_tab_first, container, false);


    //api정의
        retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
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




        Call<List<Get_today_introduction>> call = apiService.Get_today_introduction("male",34);

        call.enqueue(new Callback<List<Get_today_introduction>>() {

            @Override
            public void onResponse(Call<List<Get_today_introduction>> call, retrofit2.Response<List<Get_today_introduction>> response) {
                get_today_introductions=response.body();
                try {
                    image_url_first = String.valueOf(get_today_introductions.get(0).getImages(0));
                    image_url_second = String.valueOf(get_today_introductions.get(1).getImages(0));
                    Log.e("get_eval_list_image", String.valueOf(get_today_introductions.get(0).getImages(0)));
                    String replace = "{}";
                    String medium = "medium";
                    if (image_url_first != null && image_url_second != null) {
                        image_url_first = image_url_first.replace(replace, medium);
                        image_url_second = image_url_second.replace(replace, medium);
                        Log.e("image_url_first", image_url_first);
                        Log.e("image_url_second", image_url_second);
                        image_url(v);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("imageNULL","false");
                }


            }


            @Override
            public void onFailure(Call<List<Get_today_introduction>> call, Throwable t) {
                Log.v("onresponseImage2",t.toString());
            }
        });


       /* //블러 라이브러리
        ImageView bluri = (ImageView) v.findViewById(R.id.pic1_background);
       Glide.with(mContext).load(R.drawable.gametitle_01).into(blur);
 //.bitmapTransform(new BlurTransformation(mContext)*/

        //local_db에서 account_id가져옴


        // Inflate the layout for this fragment
        return  v;
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
        //mBTN = (Button) v.findViewById(R.id.btn);


        HttpURLConnection connection = null;
        InputStream is = null;

        Bitmap retBitmap = null;


        Thread mTread =new Thread() {
            public void run() {
                try

                {


                    URL url_frist = new URL(API_IMAGE_URL + image_url_first);
                    URL url_second = new URL(API_IMAGE_URL+image_url_second);

                    Log.e("image_url", String.valueOf(url_frist));
                    URLConnection conn = url_frist.openConnection();
                    URLConnection conn1 = url_second.openConnection();
                    conn.connect();
                    conn1.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    BufferedInputStream bis1= new BufferedInputStream(conn1.getInputStream());
                    today_Bitmap1 = BitmapFactory.decodeStream(bis);
                    today_Bitmap2= BitmapFactory.decodeStream(bis1);
                    bis.close();
                    bis1.close();
                } catch (Exception e)

                {

                }
            }
        };

        mTread.start();
        try {
            mTread.join();
            mImageView.setImageBitmap(today_Bitmap1);
            mImageView2.setImageBitmap(today_Bitmap2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Display the bitmap in ImageView





            // Define the ImageView corners radius

            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap1);
        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap2);


            roundedBitmapDrawable.setCornerRadius(cornerRadius);
            roundedBitmapDrawable.setCornerRadius(cornerRadius);
            roundedBitmapDrawable.setAntiAlias(true);
            roundedBitmapDrawable1.setAntiAlias(true);

            // Set the ImageView image as drawable object
            mImageView.setImageDrawable(roundedBitmapDrawable);
            mImageView2.setImageDrawable(roundedBitmapDrawable1);




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