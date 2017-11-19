package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leeyun on 2017. 11. 19..
 */

public class register_like implements Serializable {
    @SerializedName("from_id")
    private int from_id;
    @SerializedName("to_id")
    private int to_id;
    @SerializedName("result")
    private String result;


    public int getFrom_id() {
        return from_id;
    }

    public String getResult() {
        return result;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }
    public void setResult(String result) {
        this.result = result;
    }

}
