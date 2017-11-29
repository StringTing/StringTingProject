package com.string.leeyun.stringting_android.API;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leeyun on 2017. 11. 29..
 */

public class message implements Serializable {

    private String sex;
    private int groun_id;
    private String contents;
    private Date createtime;

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGroun_id(int groun_id) {
        this.groun_id = groun_id;
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

    public int getGroun_id() {
        return groun_id;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatetime() {
        return createtime;
    }
}
