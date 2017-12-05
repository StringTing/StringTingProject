package com.string.leeyun.stringting_android.API;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
        public static final String API_IMAGE_URL="http://115.68.226.54:3825/profile/";
        public  static  final String API_URLTest="http://192.168.0.8:8000/information/";



        @POST("male/join/")
        Call<join> getPostUserinfo(@Body userinfo Userinfo);

        @POST("female/join/")
        Call<join> getPostUserinfoF(@Body userinfo Userinfo);

        @POST("check/email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi responseApi);

        @Multipart
        @POST("{sex}/{account_id}/register/image/")
        Call<ResponseApi> getPostImage(@Path("sex")String RegisterImage_sex,@Path("account_id")String account_id,@Part MultipartBody.Part image);


        @POST("{sex}/register/like/")
        Call<register_like> getPostRegister_like(@Path("sex")String Registerlike_sex,@Body register_like registerLike);


        @POST("male/register/message/")
        Call<register_message>get_post_register_message(@Body register_message RegisterMessage);

        @GET("{sex}/{account_id}/get/detail/")
        Call<Getdetail>Getdetail(@Path("sex")String Getdetail_sex, @Path("account_id")int Getdetail_accountid);

        @GET("{sex}/{account_id}/get/last-5day-matched-account/")
        Call<get_last_5days_matched_accountList>Get_last_5day(@Path("sex")String get_last5_sex,@Path("account_id")int get_last5_accountid);

        @GET("{sex}/{account_id}/get/eval-account/")
        Call<get_eval_accountList>Get_evalaccount(@Path("sex")String evalaccount_sex, @Path("account_id")int evalaccount_accountid);

        @GET("{sex}/{account_id}/get/today-introduction/")
        Call<Im_get_today_introduction>Get_today_introduction(@Path("sex")String today_intro_sex, @Path("account_id")int today_introduction_id);

        @Multipart
        @POST("{sex}/{account_id}/register/image/")
        Call<register_image>post_register_image(@Path("sex")String register_image_sex, @Path("account_id") int register_image_account_id, @Part MultipartBody.Part[] images);


        @GET("{sex}/{account_id}/get/matched-account/")
        Call<get_matched_accountList> get_matched_account(@Path("sex")String matched_sex, @Path("account_id")int matched_account_id);

        @GET("{sex}/get/message-list/{group_id}/")
        Call<get_message_list>get_message_list(@Path("group_id")int group_id);


    }



