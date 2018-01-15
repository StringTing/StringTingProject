package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by leeyun on 2017. 12. 7..
 */

public class get_introduction_qna {

    private List<approved> approved;

    public class approved{
        String question;
        String answer;

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }

    public List<get_introduction_qna.approved> getApproved() {
        return approved;
    }

    public void setApproved(List<get_introduction_qna.approved> approved) {
        this.approved = approved;
    }
}
