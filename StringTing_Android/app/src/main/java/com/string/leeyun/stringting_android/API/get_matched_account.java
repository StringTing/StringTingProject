package com.string.leeyun.stringting_android.API;

import android.accounts.Account;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;



public class get_matched_account {

    @SerializedName("accounts")
    private accounts account;
    @SerializedName("last_messages")
    private last_messages last_message;

    public void setAccount(accounts account) {
        this.account = account;
    }

    public accounts getAccount() {

        return account;
    }

    public last_messages getLast_message() {
        return last_message;
    }

    public void setLast_message(last_messages last_message) {
        this.last_message = last_message;
    }
}

