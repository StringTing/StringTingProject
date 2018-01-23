package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
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
import static java.lang.System.in;
import static java.lang.System.load;


public class Tab_First extends Fragment implements View.OnClickListener {

    private RelativeLayout today_pic_first;
    private RelativeLayout today_pic_second;
    public static String openselected="null";
    public static String openselected_count="null";
    public static String openselected_second="null";
    public static String openselected_count_second="null";
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
    ArrayList<String>last5_image_list=new ArrayList<>();
    ArrayList<String>last5_image_replace=new ArrayList<>();
    ArrayList<String>last5_image_full_url=new ArrayList<String>();
    public String today_image_url_first;
    public String today_image_url_second;
    public String last_image_url_first;
    public String last_image_url_second;
    public String last_image_url_third;
    ArrayList<Get_today_introduction> im_get_today;
    ArrayList<Ger_last_5day_matched_account>im_get_last_5day;
    Boolean opened_form_api1;
    Boolean opened_form_api2;
    TableRow tr;
    ImageView img_pic ;
    //이미지 들어오는거
    final int[] last_pic = {1,2,3,4,5};



    public Tab_First() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences pref = this.getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);




        final View v = inflater.inflate(R.layout.activity_tab_first, container, false);

        //whitemember view
        ImageView white_member=(ImageView)v.findViewById(R.id.whitemember_banner);
        white_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Whitemember.class);
                startActivity(intent);
            }
        });



        try {

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
                        if (im_get_today.get(0).getImages().getApproved().get(0).getName() != null) {
                            today_image_url_first = String.valueOf(im_get_today.get(0).getImages().getApproved().get(0).getName());
                            Log.e("get_today_list_image", String.valueOf(im_get_today.get(0).getImages().getApproved().get(0).getName()));
                            for (int i=0;i<im_get_today.size();i++){
                                matching_account.add(im_get_today.get(i).getId());

                                TextView today_info_text=(TextView)getView().findViewById(R.id.today_info_text);
                                TextView today_info_text1=(TextView)getView().findViewById(R.id.today_info_text1);

                                String first_shp="#";
                                String shp=" #";

                                String set_info_text= String.valueOf(first_shp+im_get_today.get(0).getHeight())+shp+im_get_today.get(0).getDrink()+shp+im_get_today.get(0).getDepartment();
                                String set_info_text1= String.valueOf(first_shp+im_get_today.get(1).getHeight())+shp+im_get_today.get(1).getDrink()+shp+im_get_today.get(1).getDepartment();

                                today_info_text.setText(set_info_text);
                                today_info_text1.setText(set_info_text1);
                            }

                        }
                        today_image_url_second = String.valueOf(im_get_today.get(1).getImages().getApproved().get(0).getName());
                        opened_form_api1=im_get_today.get(0).isOpened();
                        opened_form_api2=im_get_today.get(1).isOpened();
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
                        String replace = "{}";
                        String medium = "medium";
                        String small = "small";
                        if(im_get_last_5day.get(0).getImages().getApproved().get(0).getName()!=null) {
                            for (int i = 0; i < im_get_last_5day.size(); i++) {
                                last5_image_list.add(String.valueOf(im_get_last_5day.get(i).getImages().getApproved().get(0).getName()));
                                last5_image_replace.add(last5_image_list.get(i).replace(replace,small));
                                last5_image_full_url.add(API_IMAGE_URL+last5_image_replace.get(i));
                                Log.e("지난픽들 log", String.valueOf(last5_image_full_url));
                            }
                            last_5_pic_view(inflater);

                        }

                        Log.e("get_eval_list_image", String.valueOf(im_get_today.get(0).getImages().getApproved().get(0).getName()));


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
        today_pic_first = (RelativeLayout) v.findViewById(R.id.t_pic1);
        today_pic_second=(RelativeLayout)v.findViewById(R.id.t_pic2);
        today_pic_first.setOnClickListener(this);
        today_pic_second.setOnClickListener(this);



        Log.e("openselected_second",openselected_second);

    try {
        if (openselected.equals("true")&&opened_form_api1==true) {


            ImageView today_rock = (ImageView) v.findViewById(R.id.today_rock_img);
            today_rock.setVisibility(View.GONE);

        }


    }catch (Exception e){
        e.printStackTrace();
    }
    try{
        if(openselected_second.equals("true")&&opened_form_api2==true){
            ImageView today_rock = (ImageView) v.findViewById(R.id.today_rock_img2);
            today_rock.setVisibility(View.GONE);
        }
    }catch (Exception e){
        e.printStackTrace();
    }


        // Inflate the layout for this fragment
        return  v;
    }


    @Override
    public void onClick(View v) {

        LayoutInflater Inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View today_pic_view = Inflater.inflate(R.layout.activity_tab_first, null, false);

        switch (v.getId()){
            case R.id.t_pic1 :

                try {
                    Log.e("macthing_account", String.valueOf(matching_account.get(0)));

                }catch (Exception e){
                    e.printStackTrace();
                }

                ImageView today_rock=(ImageView)today_pic_view.findViewById(R.id.today_rock_img);
                ImageView today_rock_second=(ImageView)today_pic_view.findViewById(R.id.today_rock_img2);

                if(openselected_count.equals("null")&&openselected_count_second.equals("null")) {
                    Intent i = new Intent(getActivity(),Todaypic_pop.class);
                    i.putExtra("matching_account",matching_account.get(0));
                    i.putExtra("what_pic","first");
                    if (sex.equals("male")){
                        i.putExtra("matching_sex","female");
                    }
                    else{
                        i.putExtra("matching_sex","male");

                    }

                    if (openselected_count.equals("true")){
                        Intent direct_detail = new Intent(getActivity(),Personal_profile.class);
                        direct_detail.putExtra("matching_account",matching_account.get(0));
                        direct_detail.putExtra("what_pic","first");
                        if (sex.equals("male")){
                            direct_detail.putExtra("matching_sex","female");
                        }
                        else{
                            direct_detail.putExtra("matching_sex","male");

                        }
                        startActivity(direct_detail);
                        break;
                    }
                    startActivity(i);

                }else {
                    Intent e = new Intent(getActivity(),TodaypicScd_pop.class);
                    e.putExtra("matching_account",matching_account.get(1));
                    e.putExtra("what_pic","second");

                    if (sex.equals("male")){
                        e.putExtra("matching_sex","female");
                    }
                    else{
                        e.putExtra("matching_sex","male");

                    }
                    if (openselected_count.equals("true")){
                        Intent direct_detail = new Intent(getActivity(),Personal_profile.class);
                        direct_detail.putExtra("matching_account",matching_account.get(0));
                        direct_detail.putExtra("what_pic","first");
                        if (sex.equals("male")){
                            direct_detail.putExtra("matching_sex","female");
                        }
                        else{
                            direct_detail.putExtra("matching_sex","male");

                        }
                        startActivity(direct_detail);
                        break;
                    }
                    startActivity(e);
                }


                break;
            case R.id.t_pic2 :

                ImageView today_rock1=(ImageView)today_pic_view.findViewById(R.id.today_rock_img);
                ImageView today_rock_second2=(ImageView)today_pic_view.findViewById(R.id.today_rock_img2);

                if(openselected_count.equals("null")&&openselected_count_second.equals("null")) {
                    Intent i = new Intent(getActivity(),Todaypic_pop.class);
                    i.putExtra("matching_account",matching_account.get(1));
                    i.putExtra("what_pic","second");
                    if (sex.equals("male")){
                        i.putExtra("matching_sex","female");
                    }
                    else{
                        i.putExtra("matching_sex","male");

                    }
                    if (openselected_count_second.equals("true")){
                        Intent direct_detail = new Intent(getActivity(),Personal_profile.class);
                        direct_detail.putExtra("matching_account",matching_account.get(1));
                        direct_detail.putExtra("what_pic","first");
                        if (sex.equals("male")){
                            direct_detail.putExtra("matching_sex","female");
                        }
                        else{
                            direct_detail.putExtra("matching_sex","male");

                        }
                        startActivity(direct_detail);
                        break;
                    }
                    startActivity(i);

                }else {
                    Intent e = new Intent(getActivity(),TodaypicScd_pop.class);
                    e.putExtra("matching_account",matching_account.get(1));
                    e.putExtra("what_pic","second");

                    if (sex.equals("male")){
                        e.putExtra("matching_sex","female");

                    }
                    else{
                        e.putExtra("matching_sex","male");

                    }
                    if (openselected_count_second.equals("true")){
                        Intent direct_detail = new Intent(getActivity(),Personal_profile.class);
                        direct_detail.putExtra("matching_account",matching_account.get(1));
                        direct_detail.putExtra("what_pic","first");
                        if (sex.equals("male")){
                            direct_detail.putExtra("matching_sex","female");
                        }
                        else{
                            direct_detail.putExtra("matching_sex","male");

                        }
                        startActivity(direct_detail);
                        break;
                    }

                    startActivity(e);
                }
                break;
        }





    }



    public void image_url_today(View v){
        //Arraylist에서 url 받아와서 뷰에 로드해줘야함

        // Get the application context
        mContext = getApplicationContext();
        // Get the Resources
        mResources = getResources();
        // Get the widgets reference from XML layout
        //mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
        mImageView = (ImageView) v.findViewById(R.id.ph1);
        mImageView2 = (ImageView) v.findViewById(R.id.ph2);
        l1 = (ImageView) v.findViewById(R.id.last1);
     /*   l2 = (ImageView) v.findViewById(R.id.last2);
        l3 = (ImageView) v.findViewById(R.id.last3);
*/




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


    public void openselected_check(){

    }



    public void last_5_pic_view(final LayoutInflater inflater){
        //추가할 부모 뷰
        //final TableLayout inflatedLayout = (TableLayout) v.findViewById(R.id.addpic);
//        final View v = inflater.inflate(R.layout.activity_tab_first,null);

        final TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.addpic);

        //더보기 버튼
        final LinearLayout addbtn = (LinearLayout) getView().findViewById(R.id.add_btn);

        //이미지 들어오는거
        int last_img_count;

        //픽이 3개 이하일 때
        if (last5_image_full_url.size() < 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);

            if (last5_image_full_url.size() % 3 != 0) {
                addbtn.setVisibility(View.GONE);
                tr = new TableRow(getApplicationContext());
                tr.setPadding(0, 35, 0, 0);
                for (last_img_count = 0; last_img_count < last5_image_full_url.size() % 3; ++last_img_count) {
                    View img = inflater.inflate(R.layout.addimg, null);
                    ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                    Transformation transformation = new RoundedTransformationBuilder()
                            .borderColor(Color.BLACK)
                            .borderWidthDp(0)
                            .cornerRadiusDp(8)
                            .oval(false)
                            .build();


                    Picasso.with(getContext())
                            .load(last5_image_full_url.get(last_img_count))
                            .fit()
                            .transform(transformation)
                            .into(img_pic);

                    tr.addView(img);
                }
                tableLayout.addView(tr);
            }

        }

        //3개 이상 픽
        else if (last5_image_full_url.size() >= 3) {
            TextView nothing = (TextView) getView().findViewById(R.id.nothing);
            nothing.setVisibility(View.GONE);


            tr = new TableRow(getApplicationContext());
            tr.setPadding(0, 35, 0, 0);
            for (int i=0; i < 3 ; ++i) {
                View img = inflater.inflate(R.layout.addimg, null);
                ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(0)
                        .cornerRadiusDp(8)
                        .oval(false)
                        .build();


                Picasso.with(getContext())
                        .load(last5_image_full_url.get(i))
                        .fit()
                        .transform(transformation)
                        .into(img_pic);

                tr.addView(img);
            }
            tableLayout.addView(tr);

            final int remainder = last5_image_full_url.size() -3;

            addbtn.setVisibility(View.VISIBLE);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbtn.setVisibility(View.GONE);

                    for (int i = 0; i < remainder / 3; ++i) {
                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int j = 0; j < 3; ++j) {
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(last5_image_full_url.get(i+ last5_image_full_url.size() % 3))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                    if (remainder % 3 != 0) {

                        tr = new TableRow(getApplicationContext());
                        tr.setPadding(0, 35, 0, 0);
                        for (int i = 0; i < remainder % 3; ++i) {
                            View img = inflater.inflate(R.layout.addimg, null);
                            ImageView img_pic = (ImageView) img.findViewById(R.id.last1);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.BLACK)
                                    .borderWidthDp(0)
                                    .cornerRadiusDp(8)
                                    .oval(false)
                                    .build();


                            Picasso.with(getContext())
                                    .load(last5_image_full_url.get(i))
                                    .fit()
                                    .transform(transformation)
                                    .into(img_pic);

                            tr.addView(img);
                        }
                        tableLayout.addView(tr);
                    }
                }
            });

        }


    }

}