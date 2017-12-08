package com.string.leeyun.stringting_android.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 5..
 */

public class get_message_list {
    @SerializedName("messages")
    ArrayList<message>messages;

    public ArrayList<message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<message> messages) {
        this.messages = messages;
    }
}
