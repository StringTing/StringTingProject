package com.string.leeyun.stringting_android.API;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class post_qna_list {

    private ArrayList<post_qna>PostQna;
    private String result;
    public void setPostQna(ArrayList<post_qna> postQna) {
        PostQna = postQna;
    }

    public ArrayList<post_qna> getPostQna() {

        return PostQna;
    }

    public String getResult() {
        return result;

    }

    public void setResult(String result) {
        this.result = result;
    }
}
