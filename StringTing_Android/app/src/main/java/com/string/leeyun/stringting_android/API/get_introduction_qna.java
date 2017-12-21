package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_qna {

    @SerializedName("question")
    private String question;
    @SerializedName("answer")
    private String answer;

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
