package com.string.leeyun.stringting_android.API;

import android.accounts.Account;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class get_matched_account {

    @SerializedName("last_message")
    private last_messages last_message;
    @SerializedName("account")
    private accounts account;
    private int group_id;
    private int unread_messages;
    private boolean opened;


    public com.string.leeyun.stringting_android.API.last_messages getLast_messages() {
        return last_message;
    }

    public accounts getAccount() {
        return account;
    }

    public int getGroup_id() {
        return group_id;
    }

    public int getUnread_messages() {
        return unread_messages;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public void setUnread_messages(int unread_messages) {
        this.unread_messages = unread_messages;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }
}

