package com.example.leeyun.stringting_android;

import android.app.Application;

import android.app.Activity;

import com.example.leeyun.stringting_android.API.kakakSDKAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;
    private String Test_Global_Email;


    private String Id;
    private String password;
    private char login_format;
    private int birthday;
    private char military_service_status;
    private String education;
    private String department;
    private String location;
    private  int hegiht;
    private char body_form;
    private boolean smoke;
    private boolean drink;
    private char religion;
    private char blood_type;
    private char authenticated;
    private String id_image;


    public void setId(String Id){
        this.Id=Id;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public void setLogin_format(char login_format) {
        this.login_format=login_format;
    }
    public void setBirthday(int birthday){
        this.birthday=birthday;
    }
    public void setMilitary_service_status(char military_service_status){
        this.military_service_status=military_service_status;
    }

    public void setEducation(String education){
        this.education=education;
    }
    public void setDepartment(String department){
        this.department=department;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setHegiht(int hegiht){
        this.hegiht=hegiht;
    }
    public void setBody_form(char body_form){
        this.body_form=body_form;
    }
    public void setSmoke(boolean smoke){
        this.smoke=smoke;
    }
    public void setDrink(boolean drink){
        this.drink=drink;
    }
    public void setReligion(char religion){
        this.blood_type=blood_type;
    }
    public  void setBlood_type(char blood_type)
    {
        this.blood_type=blood_type;
    }
    public  void setAuthenticated(char authenticated){
        this.authenticated=authenticated;
    }
    public void setId_image(String id_image){
        this.id_image=id_image;
    }


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
