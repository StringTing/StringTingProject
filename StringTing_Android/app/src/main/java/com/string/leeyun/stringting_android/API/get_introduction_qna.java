package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_qna {

    @SerializedName("id")
    private int id;
    @SerializedName("contents")
    private String contents;

    private String question_id;
    private String answer;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

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
