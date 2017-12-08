package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_questionlist {

    @SerializedName("questions")
    ArrayList<get_introduction_question>questions;

    public ArrayList<get_introduction_question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<get_introduction_question> questions) {
        this.questions = questions;
    }
}
