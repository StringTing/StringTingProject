package com.string.leeyun.stringting_android.API;

/**
 * Created by leeyun on 2017. 11. 27..
 */

public class join {
    private int account_id;
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {

        return token;
    }

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

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    private String message;
}
