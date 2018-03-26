package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sg970 on 2018-03-25.
 */

public class get_Coin {
    @SerializedName("accounts")
    ArrayList<get_Coin> accounts;

    public ArrayList<get_Coin> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<get_Coin> accounts) {
        this.accounts = accounts;
    }

    int id;
    int coin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
