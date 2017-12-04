package com.string.leeyun.stringting_android;

/**
 * Created by leeyun on 2017. 12. 4..
 */

public class local_db_set {

    private int account_id;
    private String token;
    private String sex;

    public String getSex() {

        return sex;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public void setAccount_id(int account_id) {

        this.account_id = account_id;
    }

    public int getAccount_id() {

        return account_id;
    }
}
