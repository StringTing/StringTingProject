package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leeyun on 2017. 11. 27..
 */

public class register_image {
    @SerializedName("result")
    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {

        return result;
    }
}
