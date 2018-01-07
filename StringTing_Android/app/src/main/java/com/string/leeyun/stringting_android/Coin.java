//package com.string.leeyun.stringting_android;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.string.leeyun.stringting_android.API.Rest_ApiService;
//import com.string.leeyun.stringting_android.API.get_matched_account;
//import com.string.leeyun.stringting_android.API.register_image;
//import com.string.leeyun.stringting_android.API.userinfo;
//import com.tsengvn.typekit.TypekitContextWrapper;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
//
//public class Coin extends AppCompatActivity {
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coin);
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        Coin.this.finish();
//    }
//}
