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
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.leeyun.stringting_android.R.id.sex;

/**
 * Created by leeyun on 2017. 11. 3..
 */


public interface Rest_ApiService {

        public  static  final String API_URL="http://115.68.226.54:3825/information/";
        public  static  final String API_URLTest="http://192.168.0.8:8000/information/";



        @POST("male/join/")
        Call<userinfo> getPostUserinfo(@Body userinfo Userinfo);

        @POST("check/email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi responseApi);

        @Multipart
        @POST("{sex}/{account_id}/register/image/")
        Call<ResponseApi> getPostImage(@Path("sex")String RegisterImage_sex,@Path("account_id")String account_id,@Part MultipartBody.Part image);


        @POST("{sex}/register/like/")
        Call<register_like> getPostRegister_like(@Path("sex")String Registerlike_sex,@Body register_like registerLike);

        @POST("{sex}/register/get/message/")
        Call<register_message>getPostRegister_message(@Path("sex")String RegisterMessage_sex,@Body register_message registerMessage);

        @GET("{sex}/{account_id}/get/detail")
        Call<Getdetail>Getdetail(@Path("sex")String Getdetail_sex, @Path("account_id")int Getdetail_accountid);

        @GET("{sex}/{account_id}/get/last-5day_matched-account")
        Call<Ger_last_5day_matched_account>Get_last_5day(@Path("sex")String GetLast5_sex,@Path("account_id")int GetLas5_accountid);

        @GET("{sex}/{account_id}/get/eval-account")
        Call<Get_evalaccount>Get_evalaccount(@Path("sex")String evalaccount_sex,@Path("account_id")int evalaccount_accountid);



    }


