package com.string.leeyun.stringting_android;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.string.leeyun.stringting_android.API.MyFirebaseInstanceIDService;
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
import com.string.leeyun.stringting_android.API.Rest_ApiService;
import com.string.leeyun.stringting_android.API.join;
import com.string.leeyun.stringting_android.API.register_message;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;

    private Button CustomloginButton;
    private CallbackManager callbackManager;
    Rest_ApiService apiService;
    Retrofit retrofit;


    SessionCallback callback;

    static String Email;
    static String sex;




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

        retrofit = new Retrofit.Builder().baseUrl(Rest_ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService= retrofit.create(Rest_ApiService.class);


        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);


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
        MyFirebaseInstanceIDService myFirebaseInstanceIDService =new MyFirebaseInstanceIDService();
        myFirebaseInstanceIDService.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
  //      Log.e("refreshedToken",refreshedToken);

        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("fcm_token",refreshedToken);
        Log.e("fcm_token",refreshedToken);
        editor.commit();

        //LocalDb

        SharedPreferences local_id = getSharedPreferences("Local_DB", MODE_PRIVATE);
        try {
            String getTEST=local_id.getString("ID","0");
            String get_local_token= local_id.getString("token","0");
            String get_local_account_id=local_id.getString("account_id","0");
            Log.e("Localdbid",getTEST);
            Log.e("local_token",get_local_token);
            Log.e("local_account_id",get_local_account_id);
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
                                save_sex(sex);
                            Intent intent = new Intent(MainActivity.this,  Basicinfo_Edit.class);
                                Log.e("facebook_token", String.valueOf(AccessToken.getCurrentAccessToken()));
                            intent.putExtra("ID",Email);
                                intent.putExtra("PW","-");
                            intent.putExtra("setformat","FACEBOOK");

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
        Intent exintent = new Intent(getApplicationContext(),Preexistence_Login.class);

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
                    Log.e("UserProfile", userProfile.toString());
                    String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                    String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                    Log.e("KakaoId", kakaoID);

                    Intent intent = new Intent(MainActivity.this,  Basicinfo_Edit.class);
                    intent.putExtra("ID",kakaoID);
                    intent.putExtra("PW","-");
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
    public void save_sex(String data){
        SharedPreferences pref = getSharedPreferences("Local_DB", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sex",data);
        editor.clear();
        editor.commit();
    }
}


