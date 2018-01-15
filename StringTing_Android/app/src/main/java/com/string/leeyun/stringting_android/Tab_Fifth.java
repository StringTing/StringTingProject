package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.string.leeyun.stringting_android.API.Getdetail;
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

import static android.content.Context.MODE_PRIVATE;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;


public class Tab_Fifth extends Fragment  {

    RelativeLayout coin,notice,push,inquire;
    TextView edit;
    int account_id;
    String sex;
    String token;
    ImageView profile;
    Getdetail getdetail;
    ArrayList<String>profile_image_list;
    ArrayList<String>profile_image_replace;
    ArrayList<String>getProfile_image_list;
    ArrayList<Integer>detail_photo;
    ArrayList<String>profile_image_full_url=new ArrayList<>();

    public Tab_Fifth() {
        // Required empty public constructor
    }
    Retrofit retrofit;
    Rest_ApiService apiService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.activity_tab_fifth, container, false);

        profile = (ImageView) v.findViewById(R.id.profile);


        coin = (RelativeLayout) v.findViewById(R.id.coin_charge);
        notice = (RelativeLayout) v.findViewById(R.id.notice);
        push = (RelativeLayout) v.findViewById(R.id.push_setting);
        inquire = (RelativeLayout) v.findViewById(R.id.inquire);

        edit = (TextView)v.findViewById(R.id.profile_edit);

        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), google_play_item_payment.class);
                startActivity(i);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),Notice_webview.class);
                startActivity(i);
            }
        });

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });


        inquire.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                // email setting 배열로 해놔서 복수 발송 가능
                String[] address = {"wearecro@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT,"스트링 고객센터에 문의합니다");
                email.putExtra(Intent.EXTRA_TEXT,"문의 할 내용을 적어주세요\n\n내용:\n\n\n\n\nString계정 : \nOS version : Android");
                startActivity(email);

            }
        });

        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PersonalProfile_Edit.class);
                startActivity(intent);
            }
        });


        ImageView white_member=(ImageView)v.findViewById(R.id.whitemember_banner);
        white_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Whitemember.class);
                startActivity(intent);
            }
        });


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

                        image_url_add(profile_image_replace);

                        //imageview 동적으로 생성하는 부분 라운드처리해줘야함



                       ImageView my_profile=(ImageView)v.findViewById(R.id.profile);

                       Picasso.with(getActivity()).load(profile_image_full_url.get(0)).transform(new CircleTransForm()).into(my_profile);




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



        return v;
    }

    public void get_local_data(){
        SharedPreferences pref = getActivity().getSharedPreferences("Local_DB", MODE_PRIVATE);
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
