package com.example.leeyun.stringting_android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.example.leeyun.stringting_android.R.id.Provision_Linkify;
import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;
import static com.kakao.auth.StringSet.error;


public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;

    private Button CustomloginButton;
    private CallbackManager callbackManager;

    SessionCallback callback;

    static String Email;



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // SDK 초기화 (setContentView 보다 먼저 실행되어야합니다. 안그럼 에러납니다.)
        setContentView(R.layout.activity_main);


        //Restrofit_test


        TextView Provision_Linkify = (TextView) findViewById(R.id.Provision_Linkify);

        String text = "가입하기 또는 로그인 버튼을 누루면 이용약관 및 개인정보취급방침에 동의하신 것이 됩니다.";
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




        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);


        
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
                            Log.e("email:",Email);
                            Intent intent = new Intent(MainActivity.this,  Basicinfo_Edit.class);
                            intent.putExtra("ID",Email);
                            intent.putExtra("setid",'F');
                            startActivity(intent);
                            finish();}
                            catch (Exception e){
                                e.printStackTrace();
                            }
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
        Intent exintent = new Intent(getApplicationContext(),Login_firstpage.class);

        startActivity(exintent);

    }

    

    private class SessionCallback implements ISessionCallback {

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
                    Log.e("UserProfile", userProfile.toString());
                    String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                    String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                    Log.e("KakaoId", kakaoID);

                    Intent intent = new Intent(MainActivity.this,  Basicinfo_Edit.class);
                    intent.putExtra("ID",kakaoID);
                    intent.putExtra("setid",'K');
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
}


