package com.string.leeyun.stringting_android.API;

import java.io.Serializable;

/**
 * Created by leeyun on 2017. 11. 1..
 */

public class userinfo implements Serializable{



    private String login_format;
    private String birthday;
    private String military_service_status;
    private String education;
    private String department;
    private String location;
    private String height;
    private boolean smoke;
    private String drink;            //논의 필요
    private String religion;
    private String blood_type;
    private boolean authenticated;
    private String id_image;
    private String password;
    private String body_form;
    private String fcm_token;
    private String token;
    private String sex;

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getFcm_token() {

        return fcm_token;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {

        return result;
    }

    private String result;


    private String email;



    public userinfo() {

    }



    public String getPassword() {
        return password;
    }

    public String getLogin_format() {
        return login_format;
    }


    public String getBirthday() {
        return birthday;
    }

    public String getMilitary_service_status() {
        return military_service_status;
    }

    public String getEducation() {
        return education;
    }

    public String getEmail() {
        return email;
    }



    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public void setAuthenticated(boolean authenticated) {

        this.authenticated = authenticated;
    }

    public void setBlood_type(String blood_type) {

        this.blood_type = blood_type;
    }

    public void setReligion(String religion) {

        this.religion = religion;
    }

    public void setDrink(String drink) {

        this.drink = drink;
    }

    public void setSmoke(boolean smoke) {

        this.smoke = smoke;
    }

    public void setBody_form(String body_form) {

        this.body_form = body_form;
    }

    public void setheight(String height) {

        this.height = height;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void setDepartment(String department) {

        this.department = department;
    }

    public void setEducation(String education) {

        this.education = education;
    }

    public void setMilitary_service_status(String military_service_status) {

        this.military_service_status = military_service_status;
    }

    public void setBirthday(String birthday) {

        this.birthday = birthday;
    }

    public void setLogin_format(String login_format) {

        this.login_format = login_format;
    }

    public void setPassword(String password) {

        this.password = password;
    }




    public String getId_image() {

        return id_image;
    }

    public boolean getAuthenticated() {

        return authenticated;
    }

    public String getBlood_type() {

        return blood_type;
    }

    public String getReligion() {

        return religion;
    }

    public String getDrink() {

        return drink;
    }

    public boolean isSmoke() {

        return smoke;
    }

    public String getBody_form() {

        return body_form;
    }

    public String getheight() {

        return height;
    }

    public String getLocation() {

        return location;
    }

    public String getDepartment() {

        return department;
    }



    public void setEmail(String email) {
        this.email = email;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public String getHeight() {

        return height;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {

        return sex;
    }
}
