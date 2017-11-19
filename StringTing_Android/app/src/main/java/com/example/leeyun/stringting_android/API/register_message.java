package com.example.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leeyun on 2017. 11. 19..
 */

public class register_message implements Serializable {
    @SerializedName("group_id")
    private int grounp_id;
    @SerializedName("contents")
    private String contents;
    @SerializedName("result")
    private String result;

    public int getGrounp_id() {
        return grounp_id;
    }

    public String getContents() {
        return contents;
    }

    public String getResult() {
        return result;
    }

    public void setGrounp_id(int grounp_id) {
        this.grounp_id = grounp_id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
