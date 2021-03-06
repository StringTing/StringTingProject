package com.string.leeyun.stringting_android.model;

import android.util.Log;

import com.string.leeyun.stringting_android.API.Get_my_pick_list;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.register_image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_IMAGE_URL;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import com.string.leeyun.stringting_android.API.userinfo;
import com.string.leeyun.stringting_android.Tab_Third;

/**
 * Created by ahdguialee on 2018. 2. 12..
 */

public class api_call {
    Retrofit retrofit;
    Rest_ApiService apiService;




    public void image_uploading(MultipartBody.Part[] images1, final String token, final int account_id, final String sex){
        Thread t = new Thread() {
            public void run() {
                try {
                    OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                    client1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                            Request builder = chain.request();
                            Request newRequest;


                            newRequest = builder.newBuilder()
                                    .addHeader("access-token",token)
                                    .addHeader("account-id", String.valueOf(account_id))
                                    .addHeader("account-sex",sex)
                                    .build();


                            return chain.proceed(newRequest);

                        }
                    });


                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client1.build())
                            .build();
                } catch (Exception e) {
                    // 무시..
                }
            }
        };
        // 스레드 시작
        t.start();

        try {
            t.join();
            apiService= retrofit.create(Rest_ApiService.class);
            Call<register_image> call = apiService.post_register_image(images1);
            call.enqueue(new Callback<register_image>() {
                @Override
                public void onResponse(Call<register_image> call, Response<register_image> response) {
                    register_image imageresponse=response.body();
                    Log.e("model 이미지 업로딩 분류",imageresponse.getResult());
//                Log.e("onregistImage", imageresponse.getMessage());


                }

                @Override
                public void onFailure(Call<register_image> call, Throwable t) {
                    Log.e("onregistImage_fail",t.toString());
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void image_edit_uploading(MultipartBody.Part[] images1, final String token, final int account_id, final String sex){
        Thread t = new Thread() {
            public void run() {
                try {
                    OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                    client1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                            Request builder = chain.request();
                            Request newRequest;


                            newRequest = builder.newBuilder()
                                    .addHeader("access-token",token)
                                    .addHeader("account-id", String.valueOf(account_id))
                                    .addHeader("account-sex",sex)
                                    .build();


                            return chain.proceed(newRequest);

                        }
                    });


                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client1.build())
                            .build();
                } catch (Exception e) {
                    // 무시..
                }
            }
        };
        // 스레드 시작
        t.start();

        try {
            t.join();
            apiService= retrofit.create(Rest_ApiService.class);
            Call<register_image> call = apiService.post_regist_edit_image(images1);
            call.enqueue(new Callback<register_image>() {
                @Override
                public void onResponse(Call<register_image> call, Response<register_image> response) {
                    register_image imageresponse=response.body();
                    Log.e("model 이미지 업로딩 분류",imageresponse.getResult());
//                Log.e("onregistImage", imageresponse.getMessage());


                }

                @Override
                public void onFailure(Call<register_image> call, Throwable t) {
                    Log.e("onregistImage_fail",t.toString());
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void editing_basicinfo(userinfo Userinfo, final String token, final int account_id, final String sex){
        Thread t = new Thread() {
            public void run() {
                try {
                    OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                    client1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {

                            Request builder = chain.request();
                            Request newRequest;


                            newRequest = builder.newBuilder()
                                    .addHeader("access-token",token)
                                    .addHeader("account-id", String.valueOf(account_id))
                                    .addHeader("account-sex",sex)
                                    .build();


                            return chain.proceed(newRequest);

                        }
                    });


                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client1.build())
                            .build();
                } catch (Exception e) {
                    // 무시.;
                }
            }
        };
        // 스레드 시작
        t.start();

        try {
            t.join();
            Log.e("수정하기 edit basicinfo Userinfo 잘넘어왔나", String.valueOf(Userinfo));
            apiService= retrofit.create(Rest_ApiService.class);
            Call<userinfo> postEditUserinfo = apiService.post_regist_edit_basicinfo(Userinfo);
            postEditUserinfo.enqueue(new Callback<userinfo>() {
                @Override
                public void onResponse(Call<userinfo> call, Response<userinfo> response) {

                    userinfo gsonresponse=response.body();
                    Log.e("베이직인포 수정하기 응답", gsonresponse.getResult());
                    Log.e("onresponse", String.valueOf(response.code()));
                    Log.e("onresponse", "success");


                }

                @Override
                public void onFailure(Call<userinfo> call, Throwable t) {
                    Log.d("sam", t.toString());
                }

            });

        } catch (Exception e)

        {
                e.printStackTrace();
        }


    }


}
