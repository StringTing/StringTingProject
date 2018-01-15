package com.string.leeyun.stringting_android.API;

import java.util.List;

/**
 * Created by leeyun on 2018. 1. 15..
 */

public class qna_list {

    private List<approved> approved;

    public class approved{
        String question;
        int id;
        int question_id;
        String answer;

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

        public String getQuestion() {
            return question;
        }

        public int getId() {
            return id;
        }
    }

    public List<qna_list.approved> getApproved() {
        return approved;
    }

    public void setApproved(List<qna_list.approved> approved) {
        this.approved = approved;
    }
}
