package com.string.leeyun.stringting_android.API;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by leeyun on 2017. 11. 3..
 */


public interface Rest_ApiService {

        public  static  final String API_URL="http://115.68.226.54:3825/information/";
        public  static  final String API_URLTest="http://192.168.0.8:8000/information/";



        @POST("male/join/")
        Call<join> getPostUserinfo(@Body userinfo Userinfo);

        @POST("check/email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi responseApi);

        @Multipart
        @POST("{sex}/{account_id}/register/image/")
        Call<ResponseApi> getPostImage(@Path("sex")String RegisterImage_sex,@Path("account_id")String account_id,@Part MultipartBody.Part image);


        @POST("{sex}/register/like/")
        Call<register_like> getPostRegister_like(@Path("sex")String Registerlike_sex,@Body register_like registerLike);

        @POST("{sex}/register/get/message/")
        Call<register_message>getPostRegister_message(@Path("sex")String RegisterMessage_sex,@Body register_message registerMessage);

        @GET("{sex}/{account_id}/get/detail/")
        Call<Getdetail>Getdetail(@Path("sex")String Getdetail_sex, @Path("account_id")int Getdetail_accountid);

        @GET("{sex}/{account_id}/get/last-5day_matched-account/")
        Call<Ger_last_5day_matched_account>Get_last_5day(@Path("sex")String GetLast5_sex,@Path("account_id")int GetLas5_accountid);

        @GET("{sex}/{account_id}/get/eval-account/")
        Call<List<Get_evalaccount>>Get_evalaccount(@Path("sex")String evalaccount_sex, @Path("account_id")int evalaccount_accountid);

        @GET("{sex}/{account_id}/get/today-introduction/")
        Call<List<Get_today_introduction>>Get_today_introduction(@Path("sex")String today_introductiion_sex,@Path("account_id")int today_introduction_account_id);

        @Multipart
        @POST("{sex}/{account_id}/register/image/")
        Call<register_image>post_register_image(@Path("sex")String register_image_sex,@Path("account_id")String register_image_account_id,@Part MultipartBody.Part[] images);

    }



