package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_question {


    @SerializedName("id")
    private int id;
    @SerializedName("contents")
    private String contents;

    public int getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
