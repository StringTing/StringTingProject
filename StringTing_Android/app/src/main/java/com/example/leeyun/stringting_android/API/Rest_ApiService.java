package com.example.leeyun.stringting_android.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by leeyun on 2017. 11. 3..
 */


public interface Rest_ApiService {

        public  static  final String API_URL="http://115.68.226.54:3825/information/";

        @GET("/")
        Call<ResponseBody> getComment(@Query("postId") int testid);

        @FormUrlEncoded
        @POST("join/")
        Call<ResponseApi> getPostCommentStr(@Field("PostJSON2") String Userinfo_Json);

        @FormUrlEncoded
        @POST("check_email/")
        Call<ResponseApi> getPostEmailStr(@Field("email") String email);

        @POST("check_email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi reponseapi);


    }


