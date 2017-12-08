package com.string.leeyun.stringting_android.API;

/**
 * Created by leeyun on 2017. 12. 6..
 */

public class check_login {

    private String result;
    private String status;
    private String id;
    private String token;
    private String sex;
    private String email;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword(String s) {

        return password;
    }

    public String getEmail() {

        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {

        return id;
    }

    public String getToken() {
        return token;
    }

    public String getSex() {
        return sex;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
