package com.example.leeyun.stringting_android.API;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 11. 20..
 */

public class Ger_last_5day_matched_account {
    ArrayList<JsonObject> account=new ArrayList<>();


    public ArrayList<JsonObject> getAccount() {
        return account;
    }

    public void setAccount(ArrayList<JsonObject> account) {
        this.account = account;
    }
}
