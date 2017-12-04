package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 4..
 */

public class get_last_5days_matched_accountList {


    @SerializedName("accounts")
    private ArrayList<Ger_last_5day_matched_account>accounts;

    public ArrayList<Ger_last_5day_matched_account> getGet_last_5day_matched_account() {
        return accounts;
    }

    public void setGet_last_5day_matched_account(ArrayList<Ger_last_5day_matched_account> get_last_5day_matched_account) {
        this.accounts = get_last_5day_matched_account;
    }
}
