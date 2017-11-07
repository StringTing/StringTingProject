package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leeyun on 2017. 11. 1..
 */

public class userinfo implements Serializable{

    @SerializedName("Id")
    public String Id;
    public String email;
    public int age;
    public String password;
    public char login_format;
    public int birthday;
    public String military_service_status;
    public String education;
    public String department;
    public String location;
    public int hegiht;
    public char body_form;
    public boolean smoke;
    public String drink;            //논의 필요
    public char religion;
    public String blood_type;
    public char authenticated;
    public String id_image;



}
