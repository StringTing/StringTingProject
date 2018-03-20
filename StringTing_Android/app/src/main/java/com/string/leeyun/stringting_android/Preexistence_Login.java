package com.string.leeyun.stringting_android;

/**
 * Created by leeyun on 2017. 9. 8..
 */



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookSdk;
import com.kakao.auth.Session;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import com.tsengvn.typekit.TypekitContextWrapper;

import com.string.leeyun.stringting_android.API.ResponseApi;
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.check_login;


import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.string.leeyun.stringting_android.API.Rest_ApiService.API_URL;


/**
 * Created by leeyun on 2017. 8. 24..
 */

public class Preexistence_Login extends Activity {


    private CallbackManager callbackManager;
    SessionCallback callback;
    Rest_ApiService apiService;
    Retrofit retrofit;
    String Email;
    String sex;
    public check_login CheckLogin=new check_login();
    public ResponseApi responapi =new ResponseApi();
    String token;
    String fcm_token;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // SDK 초기화 (setContentView 보다 먼저 실행되어야합니다. 안그럼 에러납니다.)
        setContentView(R.layout.preexistence_login);

        FrameLayout kakaologin = (FrameLayout) findViewById(R.id.loginBtn_ka);
        kakaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback = new SessionCallback();
                Session.getCurrentSession().addCallback(callback);
            }
        });




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

        Provision_Linkify.setTextColor(Color.rgb(255,255,255));

        //이용약관 및 개인정보 취급방식에대한 링크

       Intent intent=getIntent();
       fcm_token= (String) intent.getExtras().get("fcm_token");






    }
    public void onClick_login_local(View v){
        Intent intent = new Intent(getApplicationContext(),Login_local.class);

        startActivity(intent);

    }




    public void facebookLoginOnclick(View v){
        Thread t = new Thread() {
            public void run() {
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().logInWithReadPermissions(Preexistence_Login.this,
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
                                    try {
                                        Log.e("user profile", user.toString());
                                        Email = response.getJSONObject().getString("id").toString();
                                        sex = response.getJSONObject().getString("gender".toString());
                                        Log.e("email:", Email);
                                        Log.e("sex", sex);
                                        token = String.valueOf(result.getAccessToken().getToken());
                                        Log.e("facebook_token", String.valueOf(result.getAccessToken().getToken()));

                                        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("token", token);
                                        editor.putString("fcm_token", fcm_token);
                                        Log.e("fcm_token", fcm_token);
                                        editor.commit();

                                        CheckLogin.setEmail(Email);
                                        CheckLogin.setSex(sex);
                                        CheckLogin.setPassword(" ");
                                        CheckLogin.setFcm_token(fcm_token);
                                        responapi.setEmail(Email);


                                        finish();
                                    } catch (Exception e) {
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
                                                .addHeader("access-token", token)
                                                .build();


                                        return chain.proceed(newRequest);

                                    }
                                });

                                retrofit = new Retrofit.Builder()
                                        .baseUrl(API_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client1.build())
                                        .build();
                                apiService = retrofit.create(Rest_ApiService.class);

                                callback = new SessionCallback();
                                Session.getCurrentSession().addCallback(callback);


                                try {

                                    Call<ResponseApi> comment = apiService.getPostEmailStr1(responapi);
                                    comment.enqueue(new Callback<ResponseApi>() {
                                        @Override
                                        public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {


                                            ResponseApi gsonresponse = response.body();
                                            Log.e("onresponse_Email_check", gsonresponse.getResult());
                                            Log.e("onresponse_Email_check", gsonresponse.getMessage());
                                            Log.e("onresponse_Email_check", String.valueOf(response.code()));
                                            if ("true".equals(gsonresponse.getResult())) {
                                                Log.v("onresponse_Email_check", "success");

                                            } else {
                                                Log.v("onresponse", "fail");
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<ResponseApi> call, Throwable t) {
                                            Log.d("sam", "fail");
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {
                                    Call<check_login> post_check_login = apiService.post_check_login(CheckLogin);
                                    post_check_login.enqueue(new Callback<check_login>() {
                                        @Override
                                        public void onResponse(Call<check_login> call, Response<check_login> response) {

                                            Intent intent_activate = new Intent(Preexistence_Login.this, TabbedBar.class);
                                            Intent intent_ghost = new Intent(Preexistence_Login.this, Basicinfo_Edit.class);
                                            Intent intent_interview = new Intent(Preexistence_Login.this, Mediate.class);
                                            check_login gsonresponse = response.body();

                                            try {
                                                int account_id = Integer.parseInt(gsonresponse.getId());
                                                String sex = gsonresponse.getSex();
                                                save_local_data(sex, account_id, token);
                                                Log.e("체크로그인", gsonresponse.getStatus());

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            if (TextUtils.isEmpty(gsonresponse.getStatus())) {

                                                Log.e("get_status", "등록되지않은 이메일입니다");
                                                intent_ghost.putExtra("ID", Email);
                                                intent_ghost.putExtra("PW", "-");
                                                intent_ghost.putExtra("setformat", "FACEBOOK");
                                                intent_ghost.putExtra("token", token);
                                                startActivity(intent_ghost);
                                            } else {
                                                Log.e("onresponse", gsonresponse.getStatus());
                                                Log.e("onresponse", String.valueOf(response.code()));
                                                Log.e("onresponse", "success");

                                                if (gsonresponse.getStatus().equals("ACTIVATE")) {

                                                    startActivity(intent_activate);
                                                } else if (gsonresponse.getStatus().equals("GHOST")) {
                                                    intent_ghost.putExtra("ID", Email);
                                                    intent_ghost.putExtra("PW", "-");
                                                    intent_ghost.putExtra("setformat", "FACEBOOK");
                                                    intent_ghost.putExtra("token", token);

                                                    startActivity(intent_ghost);
                                                } else if (gsonresponse.getStatus().equals("INREVIEW")) {
                                                    startActivity(intent_interview);
                                                } else if (gsonresponse.getStatus().equals("REJECTED")) {

                                                } else {
                                                    intent_ghost.putExtra("ID", Email);
                                                    intent_ghost.putExtra("PW", " ");
                                                    Log.e("id", Email);
                                                    intent_ghost.putExtra("setformat", "FACEBOOK");
                                                    intent_ghost.putExtra("fcm_token", fcm_token);
                                                    intent_ghost.putExtra("token", token);

                                                    startActivity(intent_ghost);
                                                }
                                            }


                                        }


                                        @Override
                                        public void onFailure(Call<check_login> call, Throwable t) {
                                            Log.d("sam", t.toString());
                                        }

                                    });
                                } catch (Exception e) {
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
        };
        t.start();
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
    public void onClick_login_firstpage(View v) {
        Intent exintent = new Intent(getApplicationContext(), Preexistence_Login.class);

        startActivity(exintent);

    }

    public class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e("세션","성공");
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("sessionopenfail","fail");
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.preexistence_login); // 세션 연결이 실패했을때
        }

    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KaKaoSign.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


    public boolean isConnected() {          //인터넷 연결상태확인인
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    public void onClick_main_firstpage(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


