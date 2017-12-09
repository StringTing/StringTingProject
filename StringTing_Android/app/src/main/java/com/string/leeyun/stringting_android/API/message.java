package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leeyun on 2017. 11. 29..
 */

public class message implements Serializable {

    @SerializedName("sex")
    private String sex;
    @SerializedName("group_id")
    private int group_id;
    @SerializedName("contents")
    private String contents;
    @SerializedName("create_time")
    private String create_time;


    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGrounp_id(int group_id) {
        this.group_id = group_id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {

        return create_time;
    }

    public String getSex() {

        return sex;
    }

    public int getGrounp_id() {
        return group_id;
    }

    public String getContents() {
        return contents;
    }


}
