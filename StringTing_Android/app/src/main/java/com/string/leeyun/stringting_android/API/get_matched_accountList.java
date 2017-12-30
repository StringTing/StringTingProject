package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeyun on 2017. 12. 5..
 */

public class get_matched_accountList  {

        @SerializedName("accounts")
        private ArrayList<get_matched_account>accounts;


    public ArrayList<get_matched_account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<get_matched_account> accounts) {
        this.accounts = accounts;
    }

}
