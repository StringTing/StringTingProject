package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeyun on 2017. 12. 4..
 */

public class Im_get_today_introduction {


    @SerializedName("accounts")
    private ArrayList<Get_today_introduction> accounts;

    public void setGet_today_introductions(ArrayList<Get_today_introduction> get_today_introductions) {
        this.accounts = get_today_introductions;
    }

    public ArrayList<Get_today_introduction> getGet_today_introductions() {

        return accounts;
    }


}
