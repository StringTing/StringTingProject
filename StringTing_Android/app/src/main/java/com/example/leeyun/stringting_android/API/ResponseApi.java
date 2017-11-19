package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

/**
 * Created by leeyun on 2017. 11. 7..
 */

public class ResponseApi {
    @SerializedName("account_id")
    private String account_id;
    @SerializedName("sex")
    private  String sex;
    @SerializedName("email")
    private String email;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    @PrimaryKey
    private String result;




    public String getEmail(){
        return email;
    }
    public  String getMessage(){
        return message;
    }
    public  String getResult(){
        return  result;
    }
    public String getSex() {
        return sex;
    }
    public String getAccount_id(){
        return account_id;
    }

    public String setEmail(String email){
        this.email=email;
        return email;
    }
    public  void setMessage(String message){
        this.message=message;
    }
    public  void setResult(String result){
        this.result=result;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setAccount_id(String account_id){this.account_id=account_id;}
}
