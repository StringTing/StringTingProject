package com.string.leeyun.stringting_android.API;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class post_qna {
    private int question_id;
    private String answer;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {

        this.result = result;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
