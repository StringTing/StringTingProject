package com.example.leeyun.stringting_android.API;

/**
 * Created by leeyun on 2017. 9. 16..
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.leeyun.stringting_android.RealmDB;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.kakao.util.helper.Utility.getPackageInfo;

import static com.kakao.util.helper.Utility.getKeyHash;

public class App extends Application {


    private Realm mRealm;
    userinfo RealDB_userinfo;
    RealmDB db;





    private RealmResults<RealmDB> getUserList(){
        return mRealm.where(RealmDB.class).findAll();
    }




    public void insertuserData(String data){
        mRealm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                db = realm.createObject(RealmDB.class);
            }
        });
    }



    private void deleteuserData(){

        mRealm.beginTransaction();

        RealmResults<RealmDB> userList = mRealm.where(RealmDB.class).findAll();
        userList.remove(0);
        mRealm.commitTransaction();
    }




    private class KakaoSDKAdapter extends KakaoAdapter {

        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return App.this.getApplicationContext();
                }
            };
        }
    }

        @Override
        public void onCreate() {
            super.onCreate();
            KakaoSDK.init(new KakaoSDKAdapter());
            Log.e("Key Hash : ", getKeyHash(this));
        }
    //Application이 가지고있는 정보를 얻기위한 interface.

}


