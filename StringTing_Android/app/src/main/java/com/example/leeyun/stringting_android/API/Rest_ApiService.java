package com.example.leeyun.stringting_android.API;

import android.media.Image;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leeyun on 2017. 11. 3..
 */


public interface Rest_ApiService {

        public  static  final String API_URL="http://115.68.226.54:3825/information/";


        @FormUrlEncoded
        @POST("join/")
        Call<ResponseApi> getPostCommentStr(@Field("PostJSON2") String Userinfo_Json);

        @POST("check/email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi responseApi);

        @Multipart
        @POST("upload-image/")
        Call<ResponseApi> getPostImage(@Part MultipartBody.Part Image, @Part("sex") String sex, @Part("account_id")String account_id);


    }


