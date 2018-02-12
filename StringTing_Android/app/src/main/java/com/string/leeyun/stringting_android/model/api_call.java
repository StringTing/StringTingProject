package com.string.leeyun.stringting_android.model;

import android.os.AsyncTask;
import android.util.Log;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.register_image;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;

/**
 * Created by ahdguialee on 2018. 2. 12..
 */

public class api_call extends AsyncTask<Integer,Integer,Integer> {
    Retrofit retrofit;
    Rest_ApiService apiService;


    public void image_uploading(MultipartBody.Part[] images1, final String token, final int account_id, final String sex){

        @Override
        protected void onPreExecute(){
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

            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(){

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
            }

        }

        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }




    @Override
    protected Integer doInBackground(Integer... integers) {
        return null;
    }
}
