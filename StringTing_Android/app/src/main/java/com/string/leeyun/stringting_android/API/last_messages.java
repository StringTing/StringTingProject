package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by leeyun on 2017. 12. 5..
 */

public class last_messages {
    @SerializedName("sex")
    private String sex;
    @SerializedName("contents")
    private String contents;
    @SerializedName("create_time")
    private String create_time;

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void setContents(String contents) {
        this.contents = contents;
    }


    public String getSex() {

        return sex;
    }


    public String getContents() {
        return contents;
    }


    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
