package com.string.leeyun.stringting_android;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.facebook.AccessToken;
import com.facebook.FacebookAuthorizationException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.check_login;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;
import static com.string.leeyun.stringting_android.R.mipmap.e;


public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;

    private Button CustomloginButton;
    private CallbackManager callbackManager;

    String refreshedToken;
    SessionCallback callback;

    Rest_ApiService apiService;
    Retrofit retrofit;
     String Email;
     String sex;
    public check_login CheckLogin=new check_login();
    public ResponseApi responapi =new ResponseApi();
    String token;


    class Strings extends Application {

        private String Facebook_email;

        public String getState(){
            return Facebook_email;
        }
        public void setState(String s){
            Facebook_email = s;
        }
    }



    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // SDK 초기화 (setContentView 보다 먼저 실행되어야합니다. 안그럼 에러납니다.)
        setContentView(R.layout.activity_main);



        FrameLayout kakaologin = (FrameLayout) findViewById(R.id.loginBtn2);
        kakaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getCurrentSession().addCallback(callback);
            }
        });

        //Restrofit_test

        TextView Provision_Linkify = (TextView) findViewById(R.id.Provision_Linkify);

        String text = "가입하기 또는 로그인 버튼을 누르면\n이용약관 및 개인정보취급방침에 동의하신 것이 됩니다.";

        Provision_Linkify.setText(text);


        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Pattern pattern1 = Pattern.compile("이용약관");
        Pattern pattern2 = Pattern.compile("개인정보취급방침");

        Linkify.addLinks(Provision_Linkify, pattern1, "http://www.naver.com", null, mTransform);
        Linkify.addLinks(Provision_Linkify, pattern2, "http://gun0912.tistory.com/", null, mTransform);

        //이용약관 및 개인정보 취급방식에대한 링크
        Provision_Linkify.setTextColor(Color.rgb(255,255,255));



        //runtime permission
        PermissionListener permissionListener= new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this,"권한허가",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this,"권한거부\n"+ deniedPermissions.toString(),Toast.LENGTH_SHORT).show();

            }

        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진에 접근하기위해서는 사진 접근 권한이 필요해요")
                .setDeniedMessage("접근을 거부 하셨군요 \n [설정]->[권한]에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();



        //fcm token



        Thread mTread =new Thread() {
            public void run() {
                try
                {
                    refreshedToken = FirebaseInstanceId.getInstance().getToken();

                } catch (Exception e)

                {
                    e.printStackTrace();
                }
            }
        };

        mTread.start();
        try {
            mTread.join();
            SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("fcm_token",refreshedToken);
            editor.commit();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }


        //LocalDb

        SharedPreferences local_id = getSharedPreferences("Local_DB", MODE_PRIVATE);
        try {
            String getTEST=local_id.getString("ID","0");
            String get_local_token= local_id.getString("token","0");
            int get_local_account_id=local_id.getInt("account_id",0);
            Log.e("Localdbid",getTEST);
            Log.e("local_token",get_local_token);
            Log.e("local_account_id", String.valueOf(get_local_account_id));
            if (getTEST.equals(null)){
  //              Log.e("localid is null","fail");

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("can not get localid","fail");
        }



    }
    public void facebookLoginOnclick(View v){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            try{
                            Log.e("user profile", user.toString());
                            Email   = response.getJSONObject().getString("id").toString();
                                sex= response.getJSONObject().getString("gender".toString());
                                Log.e("email:",Email);
                                Log.e("sex",sex);
                            token= String.valueOf(result.getAccessToken().getToken());
                                Log.e("facebook_token", String.valueOf(result.getAccessToken().getToken()));

                                SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("token",token);
                                editor.putString("fcm_token",refreshedToken);
                                Log.e("fcm_token",token);
                                editor.commit();

                            CheckLogin.setEmail(Email);

                            CheckLogin.setPassword(" ");
                            CheckLogin.setFcm_token(refreshedToken);
                                responapi.setEmail(Email);


                                finish();}
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                        client1.addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {

                                Request builder = chain.request();
                                Request newRequest;


                                newRequest = builder.newBuilder()
                                        .addHeader("access-token",token)
                                        .build();


                                return chain.proceed(newRequest);

                            }
                        });

                        retrofit = new Retrofit.Builder()
                                .baseUrl(API_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client1.build())
                                .build();
                        apiService= retrofit.create(Rest_ApiService.class);

                        callback = new SessionCallback();
                        Session.getCurrentSession().addCallback(callback);


                        try {

                            Call<ResponseApi> comment = apiService.getPostEmailStr1(responapi);
                            comment.enqueue(new Callback<ResponseApi>() {
                                @Override
                                public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {


                                    ResponseApi gsonresponse=response.body();
                                    Log.e("onresponse_Email_check", gsonresponse.getResult());
                                    Log.e("onresponse_Email_check",gsonresponse.getMessage());
                                    Log.e("onresponse_Email_check", String.valueOf(response.code()));
                                    if("true".equals(gsonresponse.getResult())){
                                        Log.v("onresponse_Email_check", "success");

                                    }
                                    else{
                                        Log.v("onresponse","fail");
                                    }



                                }

                                @Override
                                public void onFailure(Call<ResponseApi> call, Throwable t) {
                                    Log.d("sam", "fail");
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            Call<check_login> post_check_login = apiService.post_check_login(CheckLogin);
                            post_check_login.enqueue(new Callback<check_login>() {
                                @Override
                                public void onResponse(Call<check_login> call, Response<check_login> response) {

                                    Intent intent_activate = new Intent(MainActivity.this,  TabbedBar.class);
                                    Intent intent_ghost= new Intent(MainActivity.this, Basicinfo_Edit.class);
                                    Intent intent_interview=new Intent(MainActivity.this, Mediate.class);
                                    check_login gsonresponse = response.body();
                                    Log.e("onresponse_check_login", gsonresponse.getResult());
                                    Log.e("onresponse_check_login",gsonresponse.getMessage());
                                    try{
                                        int account_id= Integer.parseInt(gsonresponse.getId());
                                        String sex=gsonresponse.getSex();
                                        save_local_data(sex,account_id,token);

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    if (gsonresponse.getStatus()==null) {

                                        Log.e("get_status","등록되지않은 이메일입니다");
                                        intent_ghost.putExtra("ID",Email);
                                        intent_ghost.putExtra("PW","-");
                                        intent_ghost.putExtra("setformat","FACEBOOK");
                                        intent_ghost.putExtra("token",token);
                                        startActivity(intent_ghost);
                                    }
                                    else
                                    {
                                        Log.e("onresponse", gsonresponse.getStatus());
                                        Log.e("onresponse", String.valueOf(response.code()));
                                        Log.e("onresponse", "success");

                                        if (gsonresponse.getStatus().equals("ACTIVATE"))
                                        {

                                            startActivity(intent_activate);
                                        }
                                        else if(gsonresponse.getStatus().equals("GHOST")){
                                            intent_ghost.putExtra("ID",Email);
                                            intent_ghost.putExtra("PW","-");
                                            intent_ghost.putExtra("setformat","FACEBOOK");
                                            intent_ghost.putExtra("token",token);

                                            startActivity(intent_ghost);
                                        }
                                        else if(gsonresponse.getStatus().equals("INREVIEW")){
                                            startActivity(intent_interview);
                                        }
                                        else if(gsonresponse.getStatus().equals("REJECTED")){

                                        }
                                        else{
                                            intent_ghost.putExtra("ID",Email);
                                            intent_ghost.putExtra("PW"," ");
                                            Log.e("id",Email);
                                            intent_ghost.putExtra("setformat","FACEBOOK");
                                            intent_ghost.putExtra("fcm_token",refreshedToken);
                                            intent_ghost.putExtra("token",token);

                                            startActivity(intent_ghost);
                                        }
                                    }




                                }


                                @Override
                                public void onFailure(Call<check_login> call, Throwable t) {
                                    Log.d("sam", t.toString());
                                }

                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    public void onClick_membership(View v){
        Intent intent = new Intent(getApplicationContext(),Membership_form.class);

        startActivity(intent);

    }
    public void onClick_login_firstpage(View v){
        Intent exintent = new Intent(getApplicationContext(),Preexistence_Login.class);
        exintent.putExtra("fcm_token",refreshedToken);
        startActivity(exintent);

    }

    public class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }

                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    //토큰도 받아와야함.
                    Intent intent_interview=new Intent(MainActivity.this, Mediate.class);


                    Log.e("UserProfile", userProfile.toString());
                    String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                    Log.e("KakaoId", kakaoID);
                    final String token=Session.getCurrentSession().getAccessToken();
                    String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                    SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("token",token);
                    editor.putString("fcm_token",refreshedToken);
                    Log.e("fcm_token",token);
                    editor.commit();

                    CheckLogin.setEmail(Email);
                    CheckLogin.setPassword(" ");
                    CheckLogin.setFcm_token(refreshedToken);

                    OkHttpClient.Builder client1 = new OkHttpClient.Builder();
                    client1.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {

                            Request builder = chain.request();
                            Request newRequest;


                            newRequest = builder.newBuilder()
                                    .addHeader("access-token",token)
                                    .build();


                            return chain.proceed(newRequest);

                        }
                    });

                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client1.build())
                            .build();
                    apiService= retrofit.create(Rest_ApiService.class);

                    callback = new SessionCallback();
                    Session.getCurrentSession().addCallback(callback);


                    try {

                        Call<ResponseApi> comment = apiService.getPostEmailStr1(responapi);
                        comment.enqueue(new Callback<ResponseApi>() {
                            @Override
                            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {


                                ResponseApi gsonresponse=response.body();
                                Log.e("onresponse_Email_check", gsonresponse.getResult());
                                Log.e("onresponse_Email_check",gsonresponse.getMessage());
                                Log.e("onresponse_Email_check", String.valueOf(response.code()));
                                if("true".equals(gsonresponse.getResult())){
                                    Log.v("onresponse_Email_check", "success");

                                }
                                else{
                                    Log.v("onresponse","fail");
                                }



                            }

                            @Override
                            public void onFailure(Call<ResponseApi> call, Throwable t) {
                                Log.d("sam", "fail");
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }




                    try {
                        Call<check_login> post_check_login = apiService.post_check_login(CheckLogin);
                        post_check_login.enqueue(new Callback<check_login>() {
                            @Override
                            public void onResponse(Call<check_login> call, Response<check_login> response) {

                                Intent intent_activate = new Intent(MainActivity.this,  TabbedBar.class);
                                Intent intent_ghost= new Intent(MainActivity.this, Basicinfo_Edit.class);
                                Intent intent_interview=new Intent(MainActivity.this, Mediate.class);
                                check_login gsonresponse = response.body();
                                Log.e("onresponse_check_login", gsonresponse.getResult());
                                Log.e("onresponse_check_login",gsonresponse.getMessage());
                                try{
                                    int account_id= Integer.parseInt(gsonresponse.getId());
                                    String sex=gsonresponse.getSex();
                                    save_local_data(sex,account_id,token);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                if (gsonresponse.getStatus()==null) {

                                    Log.e("get_status","등록되지않은 이메일입니다");
                                    startActivity(intent_ghost);
                                }
                                else
                                {
                                    Log.e("onresponse", gsonresponse.getStatus());
                                    Log.e("onresponse", String.valueOf(response.code()));
                                    Log.e("onresponse", "success");

                                    if (gsonresponse.getStatus().equals("ACTIVATE"))
                                    {

                                        startActivity(intent_activate);
                                    }
                                    else if(gsonresponse.getStatus().equals("GHOST")){
                                        intent_ghost.putExtra("ID",Email);
                                        intent_ghost.putExtra("PW","-");
                                        intent_ghost.putExtra("setformat","FACEBOOK");
                                        startActivity(intent_ghost);
                                    }
                                    else if(gsonresponse.getStatus().equals("INREVIEW")){
                                        startActivity(intent_interview);
                                    }
                                    else if(gsonresponse.getStatus().equals("REJECTED")){

                                    }
                                    else{
                                        intent_ghost.putExtra("ID",Email);
                                        intent_ghost.putExtra("PW"," ");
                                        intent_ghost.putExtra("setformat","FACEBOOK");
                                        intent_ghost.putExtra("fcm_token",refreshedToken);
                                        startActivity(intent_ghost);
                                    }
                                }

                            }


                            @Override
                            public void onFailure(Call<check_login> call, Throwable t) {
                                Log.d("sam", t.toString());
                            }

                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MainActivity.this,  Basicinfo_Edit.class);
                    intent.putExtra("ID",kakaoID);
                    intent.putExtra("PW"," ");
                    intent.putExtra("setformat","KAKAO");
                    startActivity(intent);
                }
            });

        }


        @Override
        public void onSessionOpenFailed(KakaoException exception) {

            Log.e("sessionopenfail","fail");
        }
    }

        public boolean isConnected() {          //인터넷 연결상태확인인
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }

            return false;
        }
    public void save_local_data(String sex,int account_id,String token){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sex",sex);
        editor.putInt("account_id",account_id);
        editor.putString("token",token);

        editor.clear();
        editor.commit();
    }
}


