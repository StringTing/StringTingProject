package com.example.leeyun.stringting_android;

/**
 * Created by leeyun on 2017. 9. 29..
 */

public class Contributor {
    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
