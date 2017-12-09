package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_qnalist {

    @SerializedName("qna_list")
    ArrayList<get_introduction_qna>qna_list;



    public ArrayList<get_introduction_qna> getQna_list() {
        return qna_list;
    }

    public void setQna_list(ArrayList<get_introduction_qna> qna_list) {
        this.qna_list = qna_list;
    }
}
