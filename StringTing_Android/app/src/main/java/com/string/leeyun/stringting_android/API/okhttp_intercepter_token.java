package com.string.leeyun.stringting_android.API;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by leeyun on 2017. 12. 4..
 */

public class okhttp_intercepter_token implements Interceptor {

    private int account_id;
    private String token;
    private String sex;

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public String getSex() {

        return sex;
    }

    public void setAccount_id(int account_id) {

        this.account_id = account_id;
    }

    public String getToken() {

        return token;
    }

    public int getAccount_id() {

        return account_id;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request builder = chain.request();
        Request newRequest;


        newRequest = builder.newBuilder()
                .addHeader("Context-Type","application/json")
                .addHeader("access-token",token)
                .addHeader("account-id", String.valueOf(account_id))
                .addHeader("account-sex",sex)
                .build();


        return chain.proceed(newRequest);

    }


}
