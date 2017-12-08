package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leeyun on 2017. 11. 27..
 */

public class join {


    private int account_id;
    private String token;



    private String result;

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAccount_id() {

        return account_id;
    }
    public String getToken() {

        return token;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    public void setToken(String token) {
        this.token = token;
    }


}
