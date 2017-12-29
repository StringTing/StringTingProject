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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.string.leeyun.stringting_android.API.Ger_last_5day_matched_account;
import com.string.leeyun.stringting_android.API.Get_today_introduction;
import com.string.leeyun.stringting_android.API.Im_get_today_introduction;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_last_5days_matched_accountList;
import com.string.leeyun.stringting_android.API.okhttp_intercepter_token;
import com.string.leeyun.stringting_android.API.open_id;

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
import static java.lang.System.load;


public class Tab_First extends Fragment implements View.OnClickListener {

    private FrameLayout today_pic_first;
    private FrameLayout today_pic_second;

    private Context mContext;
    private Resources mResources;
    private ImageView mImageView , mImageView2 , l1,l2,l3;
    private Bitmap today_Bitmap1,today_Bitmap2 , lb1,lb2,lb3;
      int account_id;
     String token;
     String sex;
    Rest_ApiService apiService;
    Retrofit retrofit;
    ArrayList<Integer> matching_account=new ArrayList<Integer>();

    float cornerRadius = 25f;
    public String today_image_url_first;
    public String today_image_url_second;
    public String last_image_url_first;
    public String last_image_url_second;
    public String last_image_url_third;
    ArrayList<Get_today_introduction> im_get_today;
    ArrayList<Ger_last_5day_matched_account>im_get_last_5day;

    TableRow tr;
    ImageView img_pic ;



    public Tab_First() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_tab_first, container, false);

        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
        final TableLayout tableLayout = (TableLayout) v.findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) v.findViewById(R.id.add_btn);

        //이미지 들어오는거
        final int[] p = {1,2,3,4,5};

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbtn.setVisibility(View.GONE);
                // activity_main.xml에서 정의한 LinearLayout 객체 할당

                for(int i=0; i<p.length/3; ++i) {
                    tr = new TableRow(getApplicationContext());
                    tr.setPadding(0,23,0,0);
                    for (int j = 0; j < 3; j++) {
                        // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
                        View img = inflater.inflate(R.layout.addimg, null);
                        //img_pic = (ImageView) addimg_table.findViewById(R.id.last1);
                        tr.addView(img);
                        if(j<=1){
                            img.setPadding(0,0,23,0);
                        }
                    }
                    tableLayout.addView(tr);
                    if(p.length%3 != 0) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0,23,0,0);
                        for (i=0; i<p.length%3; ++i){
                            // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
                            View img = inflater.inflate(R.layout.addimg, null);
                            //img_pic = (ImageView) addimg_table.findViewById(R.id.last1);
                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
                //inflater.inflate(R.layout.addimg_2, inflatedLayout);
            }
        });





        try {

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
        } catch (Exception e) {
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
                            Log.e("get_today_list_image", String.valueOf(im_get_today.get(0).getImages(0)));
                            for (int i=0;i<im_get_today.size();i++){
                                matching_account.add(im_get_today.get(i).getId());

                                TextView today_info_text=(TextView)getView().findViewById(R.id.today_info_text);
                                TextView today_info_text1=(TextView)getView().findViewById(R.id.today_info_text1);

                                String shp=" #";

                                String set_info_text= String.valueOf(shp+im_get_today.get(0).getHeight())+shp+im_get_today.get(0).getDrink()+shp+im_get_today.get(0).getDepartment();
                                String set_info_text1= String.valueOf(shp+im_get_today.get(1).getHeight())+shp+im_get_today.get(1).getDrink()+shp+im_get_today.get(1).getDepartment();

                                today_info_text.setText(set_info_text);
                                today_info_text1.setText(set_info_text1);
                            }

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
                            image_url_today(v);

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
        today_pic_first = (FrameLayout) v.findViewById(R.id.t_pic1);
        today_pic_second=(FrameLayout)v.findViewById(R.id.t_pic2);
        today_pic_first.setOnClickListener(this);
        today_pic_second.setOnClickListener(this);




        // Inflate the layout for this fragment
        return  v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.t_pic1 :


                Log.e("macthing_account", String.valueOf(matching_account.get(0)));

                ImageView today_rock=(ImageView)v.findViewById(R.id.today_rock_img);

                if(today_rock.getVisibility()==today_rock.VISIBLE) {
                    Intent i = new Intent(getActivity(),Todaypic_pop.class);
                    i.putExtra("matching_account",matching_account.get(0));
                    if (sex.equals("male")){
                        i.putExtra("matching_sex","female");
                    }
                    else{
                        i.putExtra("matching_sex","male");

                    }
                    startActivity(i);

                }else {
                    Intent e = new Intent(getActivity(),TodaypicScd_pop.class);
                    e.putExtra("matching_account",matching_account.get(1));
                    if (sex.equals("male")){
                        e.putExtra("matching_sex","female");
                    }
                    else{
                        e.putExtra("matching_sex","male");

                    }
                    startActivity(e);
                }


                break;
            case R.id.t_pic2 :
                ImageView today_rock1=(ImageView)v.findViewById(R.id.today_rock_img2);


                if(today_rock1.getVisibility()==today_rock1.VISIBLE) {
                    Intent i = new Intent(getActivity(),Todaypic_pop.class);
                    i.putExtra("matching_account",matching_account.get(1));
                    if (sex.equals("male")){
                        i.putExtra("matching_sex","female");
                    }
                    else{
                        i.putExtra("matching_sex","male");

                    }
                    startActivity(i);

                }else {
                    Intent e = new Intent(getActivity(),TodaypicScd_pop.class);
                    e.putExtra("matching_account",matching_account.get(1));
                    if (sex.equals("male")){
                        e.putExtra("matching_sex","female");
                    }
                    else{
                        e.putExtra("matching_sex","male");

                    }
                    startActivity(e);
                }
                break;
        }



    }



    public void image_url_today(View v){


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



        final Thread mTread =new Thread() {
            public void run() {
                try
                {

                    URL url_frist = new URL(API_IMAGE_URL + today_image_url_first);
                    URL url_second = new URL(API_IMAGE_URL+ today_image_url_second);
                    URL url_last_day_first=new URL(API_IMAGE_URL+last_image_url_first);
                    URL url_last_day_second=new URL(API_IMAGE_URL+last_image_url_second);
                    URLConnection conn;
                    URLConnection conn1;
                    URLConnection conn2;
                    URLConnection conn3;

                    if(!url_frist.equals(null)){
                        conn=url_frist.openConnection();
                        conn.connect();
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                        today_Bitmap1 = BitmapFactory.decodeStream(bis);
                        bis.close();

                    }
                    if(!url_second.equals(null)){
                      conn1 = url_second.openConnection();
                        conn1.connect();
                        BufferedInputStream bis1= new BufferedInputStream(conn1.getInputStream());
                        today_Bitmap2= BitmapFactory.decodeStream(bis1);
                        bis1.close();


                    }
                    if(!url_last_day_first.equals(null)){
                       conn2 =url_last_day_first.openConnection();
                        conn2.connect();
                        BufferedInputStream bis2= new BufferedInputStream(conn2.getInputStream());
                        lb1 =  BitmapFactory.decodeStream(bis2);
                        bis2.close();

                    }
                    if(!url_last_day_second.equals(null)){
                      conn3 =url_last_day_second.openConnection();
                        conn3.connect();
                        BufferedInputStream bis3= new BufferedInputStream(conn3.getInputStream());
                        lb2 =  BitmapFactory.decodeStream(bis3);
                        bis3.close();

                    }

//                    URL url_last_day_third =new URL(API_IMAGE_URL+last_image_url_third);

                    Log.e("image_url", String.valueOf(url_frist));
//                    URLConnection conn4=url_last_day_third.openConnection();


                } catch (Exception e)

                {
                        e.printStackTrace();
                        Log.e("setting_today_bitmap","fail");
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
            Log.e("setting_today_image","fail");

        }
        // Display the bitmap in ImageView


        // Define the ImageView corners radius

        if(!today_Bitmap1.equals(null)){
            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap1);
            roundedBitmapDrawable.setCornerRadius(cornerRadius);
            roundedBitmapDrawable.setAntiAlias(true);
            mImageView.setImageDrawable(roundedBitmapDrawable);

        }
        if(!today_Bitmap2.equals(null)){
            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(mResources, today_Bitmap2);
            roundedBitmapDrawable1.setCornerRadius(cornerRadius);
            roundedBitmapDrawable1.setAntiAlias(true);
            mImageView2.setImageDrawable(roundedBitmapDrawable1);

        }
        if(!lb1.equals(null)){
            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable2 = RoundedBitmapDrawableFactory.create(mResources, lb1);
            roundedBitmapDrawable2.setCornerRadius(cornerRadius);
            roundedBitmapDrawable2.setAntiAlias(true);
            l1.setImageDrawable(roundedBitmapDrawable2);

        }
        if(!lb2.equals(null)){
            android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable3 = RoundedBitmapDrawableFactory.create(mResources, lb2);
//        android.support.v4.graphics.drawable.RoundedBitmapDrawable roundedBitmapDrawable4 = RoundedBitmapDrawableFactory.create(mResources, lb3);
            roundedBitmapDrawable3.setCornerRadius(cornerRadius);
//        roundedBitmapDrawable4.setCornerRadius(cornerRadius);
            roundedBitmapDrawable3.setAntiAlias(true);
//        roundedBitmapDrawable4.setAntiAlias(true);
            // Set the ImageView image as drawable object
            l2.setImageDrawable(roundedBitmapDrawable3);
//        l3.setImageDrawable(roundedBitmapDrawable4);

        }

    }


    public void open_today_pic_setvisible(View v){
        ImageView today_rock=(ImageView)v.findViewById(R.id.today_rock_img);

        LayoutInflater inflater;

        today_rock.setVisibility(View.INVISIBLE);
    }


}