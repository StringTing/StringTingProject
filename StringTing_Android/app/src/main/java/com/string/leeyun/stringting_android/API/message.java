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
    private int group_id;
    private String contents;
    private Date createtime;

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGrounp_id(int group_id) {
        this.group_id = group_id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public Date getCreatetime() {
        return createtime;
    }
}
