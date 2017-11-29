package com.string.leeyun.stringting_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.string.leeyun.stringting_android.API.get_matched_account;
import com.string.leeyun.stringting_android.API.userinfo;

public class Coin extends AppCompatActivity {
get_matched_account account1;
    userinfo Userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
    }
}
