package com.string.leeyun.stringting_android.API;

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
        public static final String API_IMAGE_URL="http://115.68.226.54:3825/profile/";
        public  static  final String API_URLTest="http://172.30.1.7:3825/information/";



        @POST("join/")
        Call<join> getPostUserinfo(@Body userinfo Userinfo);

        @POST("check/email/")
        Call<ResponseApi> getPostEmailStr1(@Body ResponseApi responseApi);

        @POST("check/login/")
        Call<check_login>post_check_login(@Body check_login CheckLogin);

        @POST("register/introduction-qna/")
        Call<post_qna_list>post_qna_list(@Body post_qna_list postQnaList);

        @POST("register/open-profile/")
        Call<open_id>post_open_id(@Body open_id OpenId);


        @POST("register/like/")
        Call<register_like> getPostRegister_like(@Path("sex")String Registerlike_sex,@Body register_like registerLike);


        @POST("register/message/")
        Call<register_message>get_post_register_message(@Body register_message RegisterMessage);

        @GET("{sex}/{account_id}/get/detail/")
        Call<Getdetail>get_detail(@Path("sex")String Getdetail_sex, @Path("account_id")int Getdetail_accountid);

        @GET("{sex}/{account_id}/get/last-5day-matched-account/")
        Call<get_last_5days_matched_accountList>Get_last_5day(@Path("sex")String get_last5_sex,@Path("account_id")int get_last5_accountid);

        @GET("{sex}/{account_id}/get/eval-account/")
        Call<get_eval_accountList>Get_evalaccount(@Path("sex")String evalaccount_sex, @Path("account_id")int evalaccount_accountid);

        @GET("{sex}/{account_id}/get/today-introduction/")
        Call<Im_get_today_introduction>Get_today_introduction(@Path("sex")String today_intro_sex, @Path("account_id")int today_introduction_id);

        @Multipart
        @POST("register/image/")
        Call<register_image>post_register_image(@Part MultipartBody.Part[] images);

        @GET("{sex}/{account_id}/get/my-pick-list/")
        Call<Get_my_pick_list>get_my_pick_list(@Path("sex")String pick_sex, @Path("account_id")int pick_id);

        @GET("{sex}/{account_id}/get/matched-account")
        Call<get_matched_accountList> get_matched_account(@Path("sex")String matched_sex, @Path("account_id")int matched_account_id);

        @GET("get/message-list/{group_id}/")
        Call<get_message_list>get_message_list_api(@Path("group_id")int group_id);

        @GET("{sex}/{account-id}/get/introduction-qna/")
        Call<get_introduction_qnalist>get_introduction_qnalist(@Path("sex")String introduction_sex, @Path("account-id") int introduction_account);

        @GET("{get}/introduction-questions/")
        Call<get_introduction_questionlist>get_introduction_questionlist(@Path("get")String get);

        @POST("register/score/")
        Call<post_register_score>post_register_score(@Body post_register_score post_register_score );

        @POST("pay/item/")
        Call<post_item>post_item(@Body post_item postItem);

        @POST("register/edit-introduction-qna/")
        Call<post_qna_list>post_edit_qna(@Body post_qna_list postEditQna);

        @Multipart
        @POST("register/edit-image/")
        Call<register_image>post_regist_edit_image(@Part MultipartBody.Part[] images);

        @POST("register/edit-account-info/")
        Call<userinfo>post_regist_edit_basicinfo(@Body userinfo Userinfo);



    }



