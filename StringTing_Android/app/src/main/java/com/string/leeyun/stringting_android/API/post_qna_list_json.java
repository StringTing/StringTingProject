package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leeyun on 2017. 12. 10..
 */

public class post_qna_list_json {
    @SerializedName("qna_list")
    post_qna_list postQnaList;


    public post_qna_list getPostQnaList() {
        return postQnaList;
    }
}
