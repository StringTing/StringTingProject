package com.string.leeyun.stringting_android.API;

import java.util.ArrayList;

/**
 * Created by leeyun on 2017. 12. 5..
 */

public class get_matched_accountList extends ArrayList<get_matched_account> {


    private ArrayList<message>last_messages;
    private ArrayList<accounts>account;

    public ArrayList<message> getLast_messages() {
        return last_messages;
    }

    public ArrayList<accounts> getAccount() {
        return account;
    }

    public void setLast_messages(ArrayList<message> last_messages) {
        this.last_messages = last_messages;
    }

    public void setAccount(ArrayList<accounts> account) {
        this.account = account;
    }
}
