package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by leeyun on 2017. 12. 5..
 */

public class last_messages {
    @SerializedName("sex")
    private String sex;
    @SerializedName("group_id")
    private int grounp_id;
    @SerializedName("contents")
    private String contents;
    @SerializedName("create_time")
    private Date create_time;

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGrounp_id(int groun_id) {
        this.grounp_id = groun_id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatetime(Date createtime) {
        this.create_time = createtime;
    }

    public String getSex() {

        return sex;
    }

    public int getGrounp_id() {
        return grounp_id;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatetime() {
        return create_time;
    }

}
