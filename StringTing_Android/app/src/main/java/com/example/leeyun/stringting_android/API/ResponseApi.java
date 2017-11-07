package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leeyun on 2017. 11. 7..
 */

public class ResponseApi {
    @SerializedName("email")
    private String email;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
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
}
