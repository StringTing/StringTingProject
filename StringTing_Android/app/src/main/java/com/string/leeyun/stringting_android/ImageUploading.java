package com.string.leeyun.stringting_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.get_matched_account;
import com.string.leeyun.stringting_android.API.register_image;
import com.string.leeyun.stringting_android.API.userinfo;
import com.string.leeyun.stringting_android.model.api_call;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

import static android.content.Context.MODE_PRIVATE;
import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;

/**
 * Created by ahdguialee on 2018. 1. 31..
 */

public class ImageUploading {

    get_matched_account account1;
    userinfo Userinfo;

    int account_id_localdb;
    String token_localdb;
    Rest_ApiService apiService;
    Retrofit retrofit;
    String sex;

    public void image_uplodaing_method(ArrayList<String> Imageupload_countList,String token, int account_id, String sex) {

        Log.e("image_uploading_method 파라미타체크", Imageupload_countList.get(0));


        MultipartBody.Part[] images1 = new MultipartBody.Part[0];
        MultipartBody.Part[] images2 = new MultipartBody.Part[0];
        MultipartBody.Part[] images3 = new MultipartBody.Part[0];

        ArrayList<String> Imageresized_small = new ArrayList<>();
        ArrayList<String> Imageresized_middle = new ArrayList<>();
        ArrayList<String> Imageresized_large = new ArrayList<>();
        ArrayList<String> Imageprofile1 = new ArrayList<>();
        ArrayList<String> Imageprofile2 = new ArrayList<>();
        ArrayList<String> Imageprofile3 = new ArrayList<>();

        HashMap<String, RequestBody> images = null;
        try {
            for (int i = 0; i < Imageupload_countList.size(); i++) {
                Imageresized_small.add(image_resize.bitmap_resized_small(Imageupload_countList.get(i)));
                Imageresized_middle.add(image_resize.bitmap_resized_middle(Imageupload_countList.get(i)));
                Imageresized_large.add(image_resize.bitmap_resized_large(Imageupload_countList.get(i)));

                Log.e("image-small", Imageresized_small.get(i));
                Log.e("image-middle", Imageresized_middle.get(i));
                Log.e("image-large", Imageresized_middle.get(i));
            }

            if(Imageresized_small.size()>=1){
                if (Imageresized_small.get(0) != null) {
                    Imageprofile1.add(Imageresized_small.get(0));
                    Imageprofile1.add(Imageresized_middle.get(0));
                    Imageprofile1.add(Imageresized_large.get(0));
                    Imageprofile1.add("0");
                }
            }
            if (Imageresized_small.size()>=2) {
                if (Imageresized_small.get(1) != null) {

                    Imageprofile2.add(Imageresized_small.get(1));
                    Imageprofile2.add(Imageresized_middle.get(1));
                    Imageprofile2.add(Imageresized_large.get(1));
                    Imageprofile2.add("1");
                }
            }
            if(Imageresized_small.size()>=3){
                if (Imageresized_small.get(2) != null) {
                    Imageprofile3.add(Imageresized_small.get(2));
                    Imageprofile3.add(Imageresized_middle.get(2));
                    Imageprofile3.add(Imageresized_large.get(2));
                    Imageprofile3.add("2");
                }
            }
            if(Imageresized_small.size()>=4) {
                if (Imageresized_small.get(3) != null) {
                    Imageprofile3.add(Imageresized_small.get(3));
                    Imageprofile3.add(Imageresized_middle.get(3));
                    Imageprofile3.add(Imageresized_large.get(3));
                    Imageprofile3.add("3");
                }
            }
            if(Imageresized_small.size()>=5){
                    if (Imageresized_small.get(4) != null) {
                    Imageprofile3.add(Imageresized_small.get(4));
                    Imageprofile3.add(Imageresized_middle.get(4));
                    Imageprofile3.add(Imageresized_large.get(4));
                    Imageprofile3.add("4");
                }
            }


            ArrayList<String> keyvalue = new ArrayList<>();
            keyvalue.add("-small");
            keyvalue.add("-medium");
            keyvalue.add("-large");
            keyvalue.add("-index");

            images1 = new MultipartBody.Part[Imageprofile1.size()];
            images2 = new MultipartBody.Part[Imageprofile2.size()];
            images3 = new MultipartBody.Part[Imageprofile3.size()];

            images = new HashMap<String, RequestBody>();
            if (Imageprofile1!=null) {

                for (int index = 0; index < Imageprofile1.size(); index++) {
                    Log.e("Imageprofile1", Imageprofile1.get(index));
                    File file = new File(Imageprofile1.get(index));
                    if (index<3) {
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);

                        images1[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), file.getName(), surveyBody);
                        Log.e("멀티파트폼 index<3", String.valueOf(images1[index]));
                    }
                    if (index==3){
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("text/plain"),"");
                        images1[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), Imageprofile1.get(index));

                        Log.e("멀티파트폼 index==3", String.valueOf(images1[index]));

                    }

                }
            }
            if (Imageprofile2!=null) {
                for (int index = 0; index < Imageprofile2.size(); index++) {
                    Log.e("Imageprofile2", Imageprofile2.get(index));
                    File file = new File(Imageprofile2.get(index));
                    if (index < 3) {
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);

                        images2[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), file.getName(), surveyBody);
                        Log.e("멀티파트폼 index<3", String.valueOf(images2[index]));
                    }
                    if (index == 3) {
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("text/plain"),"");
                        images2[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), Imageprofile2.get(index));
                    }

                }
            }
            if(Imageprofile3!=null){
                for (int index = 0; index < Imageprofile3.size(); index++) {
                    Log.e("Imageprofile1", Imageprofile2.get(index));
                    File file = new File(Imageprofile2.get(index));
                    if (index < 3) {
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                        images.put("image" + keyvalue.get(index) + "\"; filename=\"" + file.getName(), surveyBody);

                        images3[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), file.getName(), surveyBody);
                        Log.e("멀티파트폼 index<3", String.valueOf(images3[index]));
                    }
                    if (index == 3) {
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("text/plain"),"");
                        images3[index] = MultipartBody.Part.createFormData("image" + keyvalue.get(index), Imageprofile3.get(index));
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        api_call imageUploadingApi=new api_call();
        if (images1.length>=3){
            if (images1[0]!=null){
                imageUploadingApi.image_edit_uploading(images1,token,account_id,sex);

            }
        }
        if (images2.length>=3) {
            if (images2[0] != null) {
                imageUploadingApi.image_edit_uploading(images2, token, account_id, sex);

            }
        }
        if(images3.length>=3) {
            if (images3[0] != null) {
                imageUploadingApi.image_edit_uploading(images3, token, account_id, sex);

            }
        }
        else {
            Log.e("images가 null값이거나 3번째 이미지를 초과했습니다","false");
        }


    }

}
