package com.example.leeyun.stringting_android;

import android.app.Application;

import android.app.Activity;

import com.example.leeyun.stringting_android.API.kakakSDKAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;
    private String Test_Global_Email;

    public String getGlobalString()
    {
        return Test_Global_Email;
    }

    public void setGlobalString(String globalString)
    {
        this.Test_Global_Email = globalString;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        KakaoSDK.init(new kakakSDKAdapter());
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }
}
