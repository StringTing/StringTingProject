package com.example.leeyun.stringting_android.API;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 11. 20..
 */

public class Get_my_pick_list {
    ArrayList<JsonObject> give_high_score_list=new ArrayList<>();
    ArrayList<JsonObject>receive_high_score_list=new ArrayList<>();
    ArrayList<JsonObject>send_like_list=new ArrayList<>();
    public ArrayList<JsonObject> getAccount() {
        return give_high_score_list;
    }

    public void setAccount(ArrayList<JsonObject> account) {
        this.give_high_score_list = account;
    }
}
